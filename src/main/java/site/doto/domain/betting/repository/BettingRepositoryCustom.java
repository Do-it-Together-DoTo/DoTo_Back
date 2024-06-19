package site.doto.domain.betting.repository;

import io.lettuce.core.dynamic.annotation.Param;
import site.doto.domain.betting.entity.Betting;

import java.util.List;

public interface BettingRepositoryCustom {
    List<Betting> findOpenBetting(@Param("memberId") Long memberId);
}
