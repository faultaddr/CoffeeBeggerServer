package com.faultaddr.coffeebeggerserver.dao;

import com.faultaddr.coffeebeggerserver.entity.MGameEntity;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Table(appliesTo = "game")
@Qualifier("GameRepository")
public interface GameRepository extends JpaRepository<MGameEntity, Long> {
    @SuppressWarnings("unchecked")
    public MGameEntity save(MGameEntity mBeatEntity);

    @Query("select m from MGameEntity  m where m.gameId=:gameId")
    public MGameEntity findMGameEntityByGameId(@Param("gameId") String gameId);
}
