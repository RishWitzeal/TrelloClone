package com.rishabh.trelloclone.payloads;

public class AssignTodoPayLoad {

    private int user_userID;
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
        return "AssignTodoPayLoad{" +
                "user_userID=" + user_userID +
                ", todoTable_tId=" + todoTable_tId +
                '}';
    }
}
