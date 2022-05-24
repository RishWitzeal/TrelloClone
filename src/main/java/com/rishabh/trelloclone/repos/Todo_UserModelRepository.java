package com.rishabh.trelloclone.repos;

import com.rishabh.trelloclone.entities.Todo_UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Todo_UserModelRepository extends JpaRepository<Todo_UserModel, Integer> {
    @Query("select tu from Todo_UserModel tu where tu.user_userID=:user_userID AND tu.todoTable_tId=:todoTable_tId")
    Todo_UserModel findByUIDandTID(@Param("user_userID") int user_userID,@Param("todoTable_tId") int todoTable_tId);
}