package com.rishabh.trelloclone.service;

import com.rishabh.trelloclone.entities.*;
import com.rishabh.trelloclone.payloads.AssignTodoPayLoad;
import com.rishabh.trelloclone.payloads.CreateTodoPayload;
import com.rishabh.trelloclone.payloads.UpdateStatusPayLoad;
import com.rishabh.trelloclone.repos.LoginUserRepository;
import com.rishabh.trelloclone.repos.RegisterUserRepository;
import com.rishabh.trelloclone.repos.TodoTableRepository;
import com.rishabh.trelloclone.repos.Todo_UserModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoUserService {

    private static final Logger logger = LoggerFactory.getLogger(TodoUserService.class);

    @Autowired
    private TodoTableRepository todoTableRepository;

    @Autowired
    private RegisterUserRepository registerUserRepository;

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private Todo_UserModelRepository todo_userModelRepository;

    public TodoTable createTodo(CreateTodoPayload todoPayload) {
        logger.info("Create todo method starts in the service");
        TodoTable newTodo = new TodoTable();
        newTodo.setStatus("Created");
        newTodo.setHeading(todoPayload.getHeading());
        newTodo.setDescription(todoPayload.getDescription());
        newTodo.setTodoCreateTime(new Date());
        newTodo.setTodoCompleteTime(null);
        todoTableRepository.save(newTodo);
        return newTodo;
    }

    public boolean theRequestHeaderIsValid(String userId, String secretKey) {
        boolean flag = false;
        User theUserObjForValidation = registerUserRepository.findByUserId(Integer.parseInt(userId));
        if (theUserObjForValidation == null) {
            logger.info("User is not in the database");
        } else {
            LoginUser theLoginUserObjForValidation = loginUserRepository.findSecretKeyByUserId(Integer.parseInt(userId));
            if (theLoginUserObjForValidation.getSecretKey().equals(secretKey)) {
                logger.info("The SECRET KEY is correct ");
                Date theTimeWhentheKeyIsType = new Date();
                if (isTheKeyValidFor30(theTimeWhentheKeyIsType, theLoginUserObjForValidation.getKeyExpireTime())) {
                    logger.info("The login time of the user is before expire time");
                    return flag = true;
                } else {
                    logger.info("The key typed time exceed the expire time");
                    return flag = false;
                }
            } else {
                logger.info("the SECRET KEY is Invalid");
                return flag;
            }
        }
        return flag;
    }

    private boolean isTheKeyValidFor30(Date theTimeWhenTheKeyIsType, Date keyExpireTime) {
        if (theTimeWhenTheKeyIsType.before(keyExpireTime)) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> fetchAllTodos(List<String> listOfTodo) {
        logger.info("List of Todo is being filled");
        List<TodoTable> objListOfAllTodos = todoTableRepository.findAllTodos();
        //Traversing the objList and adding in listOfTodo
        objListOfAllTodos.stream().forEach((todo) -> listOfTodo.add(todo.getHeading()));
        return listOfTodo;
    }


//    public String assignTodoToUser(Todo_UserModel todoUserModelPayLoad) {
//        logger.info("Assigning the User a specific todo");
//        String ans = "";
//        String conn_url = "jdbc:mysql://localhost:3307/mydb";
//        int userID = todoUserModelPayLoad.getUser_userID();
//        int tID = todoUserModelPayLoad.getTodoTable_tId();
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con_obj = DriverManager.getConnection(conn_url, "root", "admin");
//            logger.info("Connection Successful");
//            String sql = "Insert into todo_user(user_userid,todotable_tid) values('"+userID+"','" + tID+"'"+");";
//            PreparedStatement stmt = con_obj.prepareStatement(sql);
//            int res = stmt.executeUpdate();
//            if(res > 0 ) logger.info("the user " + userID + " is assigned with tid " + tID);
//            else logger.info("todo_user table not updated");
//            ans = "the user " + userID + "is assigned with tid" + tID;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return ans;
//    }
    public String assignTodoToUser(AssignTodoPayLoad todoUserModelPayLoad){
         logger.info("Assign todo to the user Service");
         User userAssignedTo = registerUserRepository.findByUserId(todoUserModelPayLoad.getUser_userID());
         TodoTable todoToBeAssigned = todoTableRepository.findByTId(todoUserModelPayLoad.getTodoTable_tId());
         if(userAssignedTo == null){
             logger.info("The User is not in the DB");
             return "The User is not in the DB";
         }
         if(todoToBeAssigned == null){
             logger.info("The TID IS NOT CREATED YET");
             return "The TID IS NOT CREATED YET";
         }
         else {
             logger.info("The User and Tid is present");
             Todo_UserModel CheckingObj = todo_userModelRepository.findByUIDandTID(todoUserModelPayLoad.getUser_userID(), todoUserModelPayLoad.getTodoTable_tId());
             if (CheckingObj != null && CheckingObj.getUser_userID() == todoUserModelPayLoad.getUser_userID() && CheckingObj.getTodoTable_tId() == CheckingObj.getTodoTable_tId()) {
                 return "The User is already assigned to a TODO";
             }
             if (CheckingObj == null){
                 logger.info("Adding the User and Todo to the DB");
                 Todo_UserModel theDataToBeAddedInAssignTable = new Todo_UserModel();
                 theDataToBeAddedInAssignTable.setUser_userID(todoUserModelPayLoad.getUser_userID());
                 theDataToBeAddedInAssignTable.setTodoTable_tId(todoUserModelPayLoad.getTodoTable_tId());
                 todo_userModelRepository.save(theDataToBeAddedInAssignTable);
                 return "The UserID " + theDataToBeAddedInAssignTable.getUser_userID() + " is Assigned with todo " + theDataToBeAddedInAssignTable.getTodoTable_tId();
             }
         }
        return "Something needs to be done!!!";
    }
    public List<Map<String,String>> allTodoWithStatus() {
        logger.info("Returning map of tid and status of todos");
        List<Map<String,String>> mapList = new ArrayList<>();
        List<TodoTable> allTodoObj = todoTableRepository.findAllTodos();
//        allTodoObj.stream().forEach((todo) -> map.put(String.valueOf(todo.getHeading()),todo.getStatus()));
        int indx =0 ;
        while(indx<allTodoObj.size()){
            Map<String,String> map = new HashMap<>();
            map.put("Status",allTodoObj.get(indx).getStatus());
            map.put("Heading",allTodoObj.get(indx).getHeading());
            map.put("tid",String.valueOf(allTodoObj.get(indx).gettID()));
            mapList.add(map);
            indx++;
        }
        return mapList;

    }

    public Map<String, String> updateStatus(UpdateStatusPayLoad statusPayLoad) {
        logger.info("Changing Status");
        TodoTable theObjFetchedFromDB = todoTableRepository.findByTId(statusPayLoad.gettID());
        Map<String,String> map = new HashMap<>();
        if (theObjFetchedFromDB==null){
            logger.info("No Todo found for tid " + statusPayLoad.gettID());
            map.put(String.valueOf(statusPayLoad.gettID()),"No Todo found for tid");
        }
        else{
            if (statusPayLoad.getStatus().equals("Completed")){
                theObjFetchedFromDB.setStatus(statusPayLoad.getStatus());
                theObjFetchedFromDB.setTodoCompleteTime(new Date());
                todoTableRepository.save(theObjFetchedFromDB);
            }
            else{
                theObjFetchedFromDB.setStatus(statusPayLoad.getStatus());
                todoTableRepository.save(theObjFetchedFromDB);
            }
            map.put(String.valueOf(statusPayLoad.gettID()),statusPayLoad.getStatus());
        }

        return map;
    }
}