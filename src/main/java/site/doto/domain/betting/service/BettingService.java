package site.doto.domain.betting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.betting.dto.*;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.betting.repository.BettingRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.domain.member_betting.entity.MemberBetting;
import site.doto.domain.member_betting.repository.MemberBettingRepository;
import site.doto.domain.todo.dto.TodoRedisDto;
import site.doto.domain.todo.entity.Todo;
import site.doto.domain.todo.repository.TodoRepository;
import site.doto.global.exception.CustomException;
import site.doto.global.redis.RedisUtils;

import java.time.LocalDate;
import java.util.List;

import static site.doto.global.status_code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BettingService {
    private final BettingRepository bettingRepository;
    private final MemberRepository memberRepository;
    private final MemberBettingRepository memberBettingRepository;
    private final TodoRepository todoRepository;
    private final RedisUtils redisUtils;

    public void addBetting(Long memberId, BettingAddReq bettingAddReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Todo todo = todoRepository.findById(bettingAddReq.getTodoId())
                .orElseThrow(() -> new CustomException(TODO_NOT_FOUND));

        if (!todo.getMember().getId().equals(memberId)) {
            throw new CustomException(FORBIDDEN);
        }

        if (todo.getDate().isBefore(LocalDate.now())) {
            throw new CustomException(TODO_ALREADY_PAST);
        }

        if (todo.getIsDone()) {
            throw new CustomException(TODO_ALREADY_DONE);
        }

        if (bettingAlreadyHolding(memberId)) {
            throw new CustomException(BETTING_ALREADY_HOLDING);
        }

        Betting betting = bettingAddReq.toEntity(member, todo);

        bettingRepository.save(betting);

        redisUtils.updateRecordToRedis(memberId, todo.getYear(), todo.getMonth(), "myBetOpen", 1);
    }

    private boolean bettingAlreadyHolding(Long memberId) {
        return !bettingRepository.findAfterToday(memberId, PageRequest.of(0, 1)).isEmpty();
    }

    @Transactional(readOnly = true)
    public MyBettingListRes findMyBetting(Long memberId) {
        List<Betting> myBetting = bettingRepository.findAllByMember(memberId);
        List<Betting> joiningBetting = bettingRepository.findJoiningBetting(memberId);

        MyBettingListRes myBettingListRes = new MyBettingListRes(myBetting);

        divideJoiningBetting(myBettingListRes, joiningBetting);

        return myBettingListRes;
    }

    private void divideJoiningBetting(MyBettingListRes myBettingListRes, List<Betting> bettingList) {
        List<BettingDto> joiningBetting = myBettingListRes.getJoiningBetting();
        List<BettingDto> closedBetting = myBettingListRes.getClosedBetting();

        bettingList.stream()
                .forEach(betting -> {
                    LocalDate todoDate;

                    if (betting.getTodo() == null) {
                        TodoRedisDto todoRedisDto = getTodoDataFromRedis(betting.getId());
                        todoDate = LocalDate.of(todoRedisDto.getYear(), todoRedisDto.getMonth(), todoRedisDto.getDate());
                    } else {
                        todoDate = betting.getDate();
                    }

                    if (todoDate.isBefore(LocalDate.now())) {
                        closedBetting.add(BettingDto.toDto(betting));
                    } else {
                        joiningBetting.add(BettingDto.toDto(betting));
                    }
                });
    }

    @Transactional(readOnly = true)
    public OpenBettingListRes findOpenBetting(Long memberId) {
        List<Betting> openBetting = bettingRepository.findOpenBetting(memberId);

        return new OpenBettingListRes(openBetting);
    }

    @Transactional(readOnly = true)
    public BettingDetailsRes findBetting(Long memberId, Long bettingId) {
        Betting betting = bettingRepository.findByIdWithChatRoom(bettingId)
                .orElseThrow(() -> new CustomException(BETTING_NOT_FOUND));

        List<MemberBetting> memberBetting = memberBettingRepository.findByBettingId(bettingId);

        BettingDetailsRes bettingDetailsRes = new BettingDetailsRes(betting, memberBetting, memberId);

        addTodoDataToBettingDetails(betting, bettingDetailsRes);

        return bettingDetailsRes;
    }

    private void addTodoDataToBettingDetails(Betting betting, BettingDetailsRes bettingDetailsRes) {
        if (betting.getTodo() == null) {
            TodoRedisDto todoRedisDto = getTodoDataFromRedis(betting.getId());

            LocalDate todoDate = LocalDate.of(todoRedisDto.getYear(), todoRedisDto.getMonth(), todoRedisDto.getDate());

            bettingDetailsRes.setTodoContents(todoRedisDto.getContents());
            bettingDetailsRes.setIsFinished(todoDate.isBefore(LocalDate.now()));
        } else {
            bettingDetailsRes.setTodoContents(betting.getTodo().getContents());
            bettingDetailsRes.setIsFinished(betting.getDate().isBefore(LocalDate.now()));
        }
    }

    public void removeBetting(Long bettingId, Long memberId) {
        Betting betting = bettingRepository.findByIdWithChatRoom(bettingId)
                .orElseThrow(() -> new CustomException(BETTING_NOT_FOUND));

        Member member = betting.getMember();

        if (!memberId.equals(member.getId())) {
            throw new CustomException(FORBIDDEN);
        }

        if (memberBettingRepository.existsByBettingId(bettingId)) {
            throw new CustomException(BETTING_CANCEL_FAILED);
        }

        bettingRepository.delete(betting);

        redisUtils.updateRecordToRedis(memberId, betting.getDate().getYear(), betting.getDate().getMonthValue(), "myBetOpen", -1);
    }

    private TodoRedisDto getTodoDataFromRedis(Long bettingId) {
        return (TodoRedisDto) redisUtils.getData("todo:" + bettingId);
    }
}
