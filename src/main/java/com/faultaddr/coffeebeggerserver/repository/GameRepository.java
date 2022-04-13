package com.faultaddr.coffeebeggerserver.repository;

import com.faultaddr.coffeebeggerserver.entity.MGameEntity;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Table(appliesTo = "game")
@Qualifier("GameRepository")
public interface GameRepository extends CrudRepository<MGameEntity, Long> {
    @SuppressWarnings("unchecked")
    public MGameEntity save(MGameEntity mBeatEntity);

    @Query("select m from MGameEntity  m where m.gameId=:gameId")
    public MGameEntity findMGameEntityByGameId(@Param("gameId") String gameId);

    @Modifying
    @Query("update MGameEntity m set m.result =:result where m.gameId=:gameId")
    public void updateMGameEntityResultByGameId(String result, String gameId);

    @Modifying
    @Query("update MGameEntity m set m.participant =:participant where m.gameId=:gameId")
    public void updateMGameEntityParticipantByGameId(String participant, String gameId);


}
