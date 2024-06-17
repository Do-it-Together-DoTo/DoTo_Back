package site.doto.domain.betting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.betting.dto.BettingAddReq;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.betting.repository.BettingRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.domain.member_betting.repository.MemberBettingRepository;
import site.doto.domain.todo.entity.Todo;
import site.doto.domain.todo.repository.TodoRepository;
import site.doto.global.exception.CustomException;
import site.doto.global.redis.RedisUtils;

import java.time.LocalDate;

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
            throw new CustomException(TODO_NOT_MINE);
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

    public void removeBetting(Long bettingId, Long memberId) {
        Betting betting = bettingRepository.findById(bettingId)
                .orElseThrow(() -> new CustomException(BETTING_NOT_FOUND));

        Member member = betting.getMember();

        if (!memberId.equals(member.getId())) {
            throw new CustomException(BETTING_NOT_MINE);
        }

        if (memberBettingRepository.existsByBettingId(bettingId)) {
            throw new CustomException(BETTING_CANCEL_FAILED);
        }

        bettingRepository.delete(betting);

        redisUtils.updateRecordToRedis(memberId, betting.getDate().getYear(), betting.getDate().getMonthValue(), "myBetOpen", -1);
    }
}
