package com.example.jitendrakumar.myapplication.models;

public class UserData {
    String userEmail;
    String userPassword;
    String userId;

   public UserData(String userName, String pass, String id){

        this.userEmail=userName;
        this.userPassword=pass;
        this.userId=id;
    }

    public String getUserName(){
        return  userEmail;
    }
    public String getPass(){
        return userPassword;
    }
    public String getId(){return userId ;}

}
