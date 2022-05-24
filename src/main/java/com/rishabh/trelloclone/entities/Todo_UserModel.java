package com.rishabh.trelloclone.entities;

import javax.persistence.*;

@Entity
@Table(name = "todo_user")
public class Todo_UserModel {

    @Id
    @Column(name = "sno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;
   // @ManyToOne(targetEntity = User.class)
   // @JoinColumn(name = "user_userid",nullable = false)
    @Column(name = "user_userid")
    private int user_userID;
   // @ManyToOne(targetEntity = TodoTable.class)
   // @JoinColumn(name = "todotable_tid",nullable = false)
    @Column(name = "todotable_tid")
    private int todoTable_tId;


    public int getUser_userID() {
        return user_userID;
    }

    public void setUser_userID(int user_userID) {
        this.user_userID = user_userID;
    }

    public int getTodoTable_tId() {
        return todoTable_tId;
    }

    public void setTodoTable_tId(int todoTable_tId) {
        this.todoTable_tId = todoTable_tId;
    }

    @Override
    public String toString() {
        return "Todo_UserModel{" +
                "user_userID=" + user_userID +
                ", todoTable_tId=" + todoTable_tId +
                '}';
    }
}
