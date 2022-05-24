package com.rishabh.trelloclone.controller;

import com.rishabh.trelloclone.entities.TodoTable;
import com.rishabh.trelloclone.payloads.AssignTodoPayLoad;
import com.rishabh.trelloclone.payloads.CreateTodoPayload;
import com.rishabh.trelloclone.payloads.UpdateStatusPayLoad;
import com.rishabh.trelloclone.service.TodoUserService;
import com.rishabh.trelloclone.service.Todo_UserModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/app/todo")
public class TodoController {

    private static Logger logger = LoggerFactory.getLogger(TodoController.class);
    @Autowired
    private TodoUserService todoUserService;

    @Autowired
    private Todo_UserModelService todo_userModelService;

    @Operation(summary = "Creating Todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateTodoPayload.class)) }),
            @ApiResponse(responseCode = "400", description = "Secret Key is Wrong",
                    content = @Content) })
    @PostMapping("/createTodo")
    public ResponseEntity<Map<String,String>> createTodo(@RequestHeader("userId") String userId, @RequestHeader("secretKey") String secretKey, @RequestBody CreateTodoPayload todoJsonPayload) throws ParseException {
        logger.info("Checking the header validations");
        String responseToBeReturned = "";
        Map<String,String> map= new HashMap<>();
        if (todoUserService.theRequestHeaderIsValid(userId,secretKey)) {
            TodoTable returnedTodoObj = todoUserService.createTodo(todoJsonPayload);
            map.put("tid",String.valueOf(returnedTodoObj.gettID()));
            map.put("heading",returnedTodoObj.getHeading());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(returnedTodoObj.getTodoCreateTime());
            System.out.println("Converted String: " + strDate);
            map.put("TodoCreateTime",strDate);
            return  ResponseEntity.status(HttpStatus.OK).body(map);
        }
        else{
            logger.error("Either secret key is wrong or time is up");
            map.put("ISSUE" , "PROBLEM IN THE HEADER INSERTION");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);

        }
    }
    @Operation(summary = "Getting all todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoTable.class)) }),
            @ApiResponse(responseCode = "400", description = "Wrong Secret Key Or UserId",
                    content = @Content) })
    @GetMapping("/getAllTodos")
    public List<String> getAllTodos(@RequestHeader("userId") String userId, @RequestHeader("secretKey") String secretKey){
        logger.info("Calling Controller get all todo");
        List<String> listOfTodo = new ArrayList<>();
        if (todoUserService.theRequestHeaderIsValid(userId,secretKey)){
            listOfTodo = todoUserService.fetchAllTodos(listOfTodo);
        }
        else{
            logger.error("Wrong Secret Key Or UserId");
        }
        return listOfTodo;
    }
    @Operation(summary = "Assigning todos to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AssignTodoPayLoad.class)) }),
            @ApiResponse(responseCode = "400", description = "Wrong Secret Key Or UserId",
                    content = @Content) })
    @PostMapping("/assignTodo")
    public ResponseEntity<String> assignTodoToUser(@RequestHeader("userId") String userId, @RequestHeader("secretKey") String secretKey, @RequestBody AssignTodoPayLoad todoUserModelPayLoad){
        logger.info("Assigning Todo controller called");
        if(todoUserService.theRequestHeaderIsValid(userId, secretKey)){
            String ans = todoUserService.assignTodoToUser(todoUserModelPayLoad);
            return ResponseEntity.status(HttpStatus.CREATED).body(ans);
        }
        else{
            logger.error("Problem in the Input Header");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Problem in Header Insertion");
        }

    }
    @Operation(summary = "Getting all todos with status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoTable.class)) }),
            @ApiResponse(responseCode = "400", description = "Wrong Secret Key Or UserId",
                    content = @Content) })
    @GetMapping("/getAllTodoWithStatus")
    public ResponseEntity<HashMap<String, List<HashMap<String, String>>>> getALLTodoWithStatus(@RequestHeader("userId") String userId, @RequestHeader("secretKey") String secretKey){
        logger.info("Getting the status of All Todos");
//        List<HashMap<String,String>> mapList = new Arraylist<>();
        HashMap<String,List<HashMap<String,String>>> mapList = new HashMap<>();
        if(todoUserService.theRequestHeaderIsValid(userId, secretKey)){
            mapList = todoUserService.allTodoWithStatus();
            return ResponseEntity.status(HttpStatus.FOUND).body(mapList);
        }
//        else {
//            logger.error("Problem in the Input Header");
//            Map<String,String> map = new HashMap<>();
//            map.put("Status","Problem in the Input Header");
//            mapList.add(map);
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(mapList);
//        }
        return ResponseEntity.status(HttpStatus.OK).body(mapList);
    }
    @Operation(summary = "updating todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateStatusPayLoad.class)) }),
            @ApiResponse(responseCode = "400", description = "Wrong Secret Key Or UserId",
                    content = @Content) })
    @PutMapping("/updateStatus")
    public ResponseEntity<Map<String,String>> updateStatus(@RequestHeader("userId") String userId, @RequestHeader("secretKey") String secretKey, @RequestBody UpdateStatusPayLoad statusPayLoad){
        logger.info("The Update Info Controller Starts");
        Map<String,String> map = new HashMap<>();
        if(todoUserService.theRequestHeaderIsValid(userId,secretKey)){
            map = todoUserService.updateStatus(statusPayLoad);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        }else {
            logger.error("Invalid Headers");
            map.put("Error","Problem in headers");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }

    @GetMapping("/getUserCompleteInfo/{UserID}")
    public ResponseEntity<List<Map<String,String>>> getWholeTrelloOfUser(@RequestHeader("userId") String userId, @RequestHeader("secretKey") String secretKey,@PathVariable("UserID") String UserID){
        logger.info("The Update Info Controller Starts");
        Map<String,String> map = new HashMap<>();
        if(todoUserService.theRequestHeaderIsValid(userId,secretKey)){
            List<Map<String, String>> maps =  todo_userModelService.getUserCompleteInfo(UserID);
            return ResponseEntity.status(HttpStatus.OK).body(maps);
        }
        else{
            logger.error("Header Issue");
            map.put("Issue","Problem With Header");
            List<Map<String,String>> mapList = new ArrayList<>();
            mapList.add(map);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapList);
        }

    }
}
