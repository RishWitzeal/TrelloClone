package com.rishabh.trelloclone.payloads;

import com.rishabh.trelloclone.Validators.ValidPassword;

import javax.validation.constraints.Email;

public class RegisterUserPayload {

    @Email
    private String email;
//    @ValidPassword
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUserPayload{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
