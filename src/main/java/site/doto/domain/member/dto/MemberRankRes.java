package site.doto.domain.member.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberRankRes {
    List<RankDto> ranks;
}
