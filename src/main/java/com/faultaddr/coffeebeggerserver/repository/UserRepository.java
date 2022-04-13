package com.faultaddr.coffeebeggerserver.repository;

import com.faultaddr.coffeebeggerserver.entity.MUserEntity;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(appliesTo = "user")
@Qualifier("UserRepository")
public interface UserRepository extends CrudRepository<MUserEntity, Long> {
    @Query("select m from MUserEntity  m where m.avatar=:avatar")
    public MUserEntity findMUserEntityByAvatar(String avatar);
}
