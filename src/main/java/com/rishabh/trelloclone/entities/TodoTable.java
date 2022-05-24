package com.rishabh.trelloclone.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "todotable")
public class TodoTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid")
    private int tID;
    @Column(name = "status")
    private String status;
    @Column(name = "heading")
    private String heading;
    @Column(name = "description")
    private String description;
    @Column(name ="todocreatetime")
    private Date todoCreateTime;
    @Column(name = "todocompletetime")
    private Date todoCompleteTime;
    @Column(name = "tododuedate")
    private Date todoDueDate;


    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTodoCreateTime() {
        return todoCreateTime;
    }

    public void setTodoCreateTime(Date todoCreateTime) {
        this.todoCreateTime = todoCreateTime;
    }

    public Date getTodoCompleteTime() {
        return todoCompleteTime;
    }

    public void setTodoCompleteTime(Date todoCompleteTime) {
        this.todoCompleteTime = todoCompleteTime;
    }


    public Date getTodoDueDate() {
        return todoDueDate;
    }

    public void setTodoDueDate(Date todoDueDate) {
        this.todoDueDate = todoDueDate;
    }

    @Override
    public String toString() {
        return "TodoTable{" +
                "tID=" + tID +
                ", status='" + status + '\'' +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                ", todoCreateTime=" + todoCreateTime +
                ", todoCompleteTime=" + todoCompleteTime +
                ", todoDueDate=" + todoDueDate +
                '}';
    }
}
