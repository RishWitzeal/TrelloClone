package com.rishabh.trelloclone.controller;
import com.rishabh.trelloclone.payloads.LoginUserPayload;
import com.rishabh.trelloclone.payloads.RegisterUserPayload;
import com.rishabh.trelloclone.service.LoginUserService;
import com.rishabh.trelloclone.service.RegisterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
public class RegisterAndLoginUserConttroller {

    private static final Logger logger = LoggerFactory.getLogger(RegisterAndLoginUserConttroller.class);

    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private LoginUserService loginUserService;

    @Operation(summary = "Registering the User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterUserPayload.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Info Or User Already Exist",
                    content = @Content) })
    @PostMapping("/registerUser")
   // @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserPayload registerUserPayLoad){
        logger.info("Checking and creating the user with email" + registerUserPayLoad.getEmail());
        ResponseEntity<String> userIDFormedAfterServiceCall = registerUserService.registerUser(registerUserPayLoad);
        return userIDFormedAfterServiceCall;

    }
    @Operation(summary = "User Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginUserPayload.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid PassWord or User doesn't exist",
                    content = @Content) })
    @PostMapping("/loginUser")
    //@ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> loginTheUserAndCreatingTheSecretKey(@RequestBody LoginUserPayload loginUserPayLoad){
        logger.info("Login the user and checking the further Condition");
        ResponseEntity<String> secretKey = loginUserService.checkTheLoginValidityAndGenerateTheSecurityKey(loginUserPayLoad);
        return secretKey;
    }



}
