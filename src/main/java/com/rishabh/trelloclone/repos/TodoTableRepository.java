package com.rishabh.trelloclone.repos;

import com.rishabh.trelloclone.entities.TodoTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoTableRepository extends JpaRepository<TodoTable, Integer> {
    @Query(value = "select * from todotable;",nativeQuery = true)
    List<TodoTable> findAllTodos();

    @Query("Select t from TodoTable t where t.tID=:tID")
    TodoTable findByTId(@Param("tID") int tID);


}