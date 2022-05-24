package com.rishabh.trelloclone.service;

import com.rishabh.trelloclone.entities.User;
import com.rishabh.trelloclone.payloads.RegisterUserPayload;
import com.rishabh.trelloclone.repos.RegisterUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class RegisterUserService {

    private static Logger logger = LoggerFactory.getLogger(RegisterUserService.class);

    @Autowired
    private RegisterUserRepository registerUserRepository;

    public ResponseEntity<String> registerUser(RegisterUserPayload registerUserPayLoad) {
        logger.info("Register the user and checking if that user already exist");
        String userID = "";
        User objCreatedToExistance = registerUserRepository.findByEmail(registerUserPayLoad.getEmail());
        if(isPayLoadEmailValid(registerUserPayLoad.getEmail())) {
            logger.info("The Email is in Right Constraints");
            if (objCreatedToExistance == null) {
                if (isPasswordValid(registerUserPayLoad.getPassword())) {
                    User newRegisterUser = new User();
                    newRegisterUser.setEmail(registerUserPayLoad.getEmail());
                    newRegisterUser.setPassword(registerUserPayLoad.getPassword());
                    LocalDateTime now = LocalDateTime.now();
                    newRegisterUser.setUserCreatedTime(now);
                    registerUserRepository.save(newRegisterUser);
                    logger.info("New User registered");
                    userID = String.valueOf(newRegisterUser.getUserId());
                } else {
                    logger.error("The Password given by user is not in the given constraints");
                    userID = "the password is out of constraints";
                }
            }
            else{
                logger.info("The email id already exist");
                userID = "the email already exist";
                return ResponseEntity.status(HttpStatus.CONFLICT).body(userID);
            }
        }
        else{
            logger.error("Wrong Email Id Constraints");
            userID = "the email is not in the valid constraints";
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(userID);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userID);
    }

    private boolean isPayLoadEmailValid(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@"+"(?:[a-zA-Z0-9-]+\\.)+[a-z" +"A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;
            return pat.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password){
        {
            boolean isValid = true;
            if (password.length() > 15 || password.length() < 8)
            {
                logger.warn("Password must be less than 20 and more than 8 characters in length.");
                isValid = false;
            }
            String upperCaseChars = "(.*[A-Z].*)";
            if (!password.matches(upperCaseChars ))
            {
                logger.warn("Password must have atleast one uppercase character");
                isValid = false;
            }
            String lowerCaseChars = "(.*[a-z].*)";
            if (!password.matches(lowerCaseChars ))
            {
                logger.warn("Password must have atleast one lowercase character");
                isValid = false;
            }
            String numbers = "(.*[0-9].*)";
            if (!password.matches(numbers ))
            {
                logger.warn("Password must have atleast one number");
                isValid = false;
            }
            String specialChars = "(.*[@,#,$,%].*$)";
            if (!password.matches(specialChars ))
            {
                logger.warn("Password must have atleast one special character among @#$%");
                isValid = false;
            }
            return isValid;
        }
    }
}
