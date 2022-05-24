package com.rishabh.trelloclone.service;

import com.rishabh.trelloclone.entities.TodoTable;
import com.rishabh.trelloclone.entities.Todo_UserModel;
import com.rishabh.trelloclone.entities.User;
import com.rishabh.trelloclone.repos.RegisterUserRepository;
import com.rishabh.trelloclone.repos.TodoTableRepository;
import com.rishabh.trelloclone.repos.Todo_UserModelRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class Todo_UserModelService {

    private static final Logger logger = LoggerFactory.getLogger(Todo_UserModelService.class);

    @Autowired
    private RegisterUserRepository registerUserRepository;

    @Autowired
    private TodoTableRepository todoTableRepository;

    @Autowired
    private Todo_UserModelRepository todo_userModelRepository;

    public String assignTodoToUser(Todo_UserModel todoUserModelPayLoad) {
        logger.info("Checking and adding the assign value to the Db");
        String ans = "";
        int UserIDFromPayLoad = todoUserModelPayLoad.getUser_userID();
        int TidFromPayLoad = todoUserModelPayLoad.getTodoTable_tId();
        //int userIDToBeUsed = todoUserModelPayLoad.getInt("user_userId");
        //int tIDToBeUsed = todoUserModelPayLoad.getInt("todoTable_tId");
        User theUserFetchedFromDB = registerUserRepository.findByUserId(UserIDFromPayLoad);
        TodoTable theTodoFetchedFromDB = todoTableRepository.findByTId(TidFromPayLoad);
        if(theUserFetchedFromDB == null) {
            logger.info("The User is not present");
            return "The User is NOT IN THE DB";
        }
        if(theTodoFetchedFromDB == null){
            logger.info("The TID IS NOT CREATED YET");
            return "The tid is not in the DB";
        }
        else{
            logger.info("The conditions are checked now add the data in the db");
            Todo_UserModel DBEntry = new Todo_UserModel();
            DBEntry.setUser_userID(theUserFetchedFromDB.getUserId());
            DBEntry.setTodoTable_tId(theTodoFetchedFromDB.gettID());
            todo_userModelRepository.save(DBEntry);
            return "the User " + theUserFetchedFromDB.getUserId()+ " is assigned with tid " + theTodoFetchedFromDB.gettID();
        }
    }

    public List<Map<String,String>> getUserCompleteInfo(String userID){
        List<Map<String, String>> listMap = new ArrayList<>();
        String conn_url = "jdbc:mysql://localhost:3307/mydb";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con_obj = DriverManager.getConnection(conn_url, "root", "admin");
            logger.info("Connection Successful");
            //String sql = "Insert into todo_user(user_userid,todotable_tid) values('"+userID+"','" + tID+"'"+");";
            String sql = "Select u.email,u.usercreatedtime,t.tid,t.status,t.heading from todo_user tu Join user u ON tu.user_userid = u.userid join todotable t On tu.todotable_tid = t.tid where tu.user_userid =" + "'" +userID+"'"+";";
            PreparedStatement stmt = con_obj.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()) {
                Map<String,String> map = new HashMap<>();
                map.put(userID,"User has No TODO Assigned");
                listMap.add(map);
                return listMap;
            }
            else{
                while(res.next()){
                    Map<String,String> map = new HashMap<>();
                    String email = res.getString("email");
                    String usercreatedtime = res.getString("usercreatedtime");
                    String tid = res.getString("tid");
                    String status = res.getString("status");
                    String heading = res.getString("heading");

                    map.put("email",email);
                    map.put("userCreatedTime",usercreatedtime);
                    map.put("tid",tid);
                    map.put("status",status);
                    map.put("heading",heading);


                    listMap.add(map);
                }
                return listMap;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listMap;
    }
}
