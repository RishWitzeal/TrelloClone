package com.rishabh.trelloclone.service;

import com.rishabh.trelloclone.entities.LoginUser;
import com.rishabh.trelloclone.entities.User;
import com.rishabh.trelloclone.payloads.LoginUserPayload;
import com.rishabh.trelloclone.repos.LoginUserRepository;
import com.rishabh.trelloclone.repos.RegisterUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Service
public class LoginUserService {

    private static Logger logger = LoggerFactory.getLogger(LoginUserService.class);

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private RegisterUserRepository registerUserRepository;

    public ResponseEntity<String> checkTheLoginValidityAndGenerateTheSecurityKey(LoginUserPayload loginUserPayLoad) {
        logger.info("Checking the login activity and key generation");
        String secretKey ="";
        User thePasswordCheckerObj = registerUserRepository.findPasswordByEmail(loginUserPayLoad.getEmail());

        //Check id the user exist or Not
        if (thePasswordCheckerObj == null){
            secretKey = "The user Doesnt Exist with Email " + loginUserPayLoad.getEmail() ;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(secretKey);
        }

        //If the user exist and password is correct
        else if(loginUserPayLoad.getPassword().equals(thePasswordCheckerObj.getPassword())){
            LoginUser theObjToStoredInDB = new LoginUser();
            theObjToStoredInDB.setUserID(thePasswordCheckerObj);
            theObjToStoredInDB.setSecretKey(secretKeyGenerator());
            theObjToStoredInDB.setKeyCreateTime(new Date());
            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();
            theObjToStoredInDB.setKeyExpireTime(new Date(timeInSecs + (30 * 60 * 1000)));
            loginUserRepository.save(theObjToStoredInDB);
            logger.info("The Data for email " + loginUserPayLoad.getEmail() +" is saved");
            secretKey=theObjToStoredInDB.getSecretKey();
        }
        else{
            secretKey = "the Password Typed is Wrong for Email " + loginUserPayLoad.getEmail();
            ResponseEntity.status(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(secretKey);
    }

    public String secretKeyGenerator(){
        logger.info("Generating the secret Key");
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }

}
