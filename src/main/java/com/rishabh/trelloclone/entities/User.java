package com.rishabh.trelloclone.entities;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userId;         //PK
    @Column(name = "email")
//    @Email(regexp = ".+@.+\\..+")
    private String email;
    @Column(name = "password")
//    @ValidPassword
    private String password;
    @Column(name = "usercreatedtime")
    private LocalDateTime userCreatedTime;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user_userID",targetEntity = LoginUser.class)
    private Set<LoginUser> loginUserSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user_userID",targetEntity = Todo_UserModel.class)
    private Set<Todo_UserModel> todo_userModelSet;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getUserCreatedTime() {
        return userCreatedTime;
    }

    public void setUserCreatedTime(LocalDateTime userCreatedTime) {
        this.userCreatedTime = userCreatedTime;
    }

    public Set<LoginUser> getLoginUserSet() {
        return loginUserSet;
    }

    public void setLoginUserSet(Set<LoginUser> loginUserSet) {
        this.loginUserSet = loginUserSet;
    }

    public Set<Todo_UserModel> getTodo_userModelSet() {
        return todo_userModelSet;
    }

    public void setTodo_userModelSet(Set<Todo_UserModel> todo_userModelSet) {
        this.todo_userModelSet = todo_userModelSet;
    }
}
