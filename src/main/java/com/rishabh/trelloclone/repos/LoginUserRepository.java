package com.rishabh.trelloclone.repos;

import com.rishabh.trelloclone.entities.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginUserRepository extends JpaRepository<LoginUser, Integer> {

    @Query(value = "Select * from loginuser where user_userid=:userId order by createkeytime desc limit 0,1;",nativeQuery = true)
    LoginUser findSecretKeyByUserId(@Param("userId") int userId);
}