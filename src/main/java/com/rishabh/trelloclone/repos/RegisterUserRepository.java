package com.rishabh.trelloclone.repos;

import com.rishabh.trelloclone.entities.LoginUser;
import com.rishabh.trelloclone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterUserRepository extends JpaRepository<User, Integer> {


    User findByEmail(String email);

    @Query("Select u from User u where u.email=:email")
    User findPasswordByEmail(@Param("email") String email);

    @Query("Select u from User u where u.userId=:userId")
    User findByUserId(@Param("userId") int userId);
}