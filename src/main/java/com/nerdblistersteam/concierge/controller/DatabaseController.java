//package com.nerdblistersteam.concierge.controller;
//
//import org.springframework.stereotype.Component;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//@Component
//public class DatabaseController {
//    private Connection connection = connect("jdbc:mysql://localhost:3306/concierge?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//
//    private Connection connect(String url) {
//        System.out.println("Skapar anslutning...");
//        try {
//            System.out.println("Uppkopplad");
//            return DriverManager.getConnection(url, "root", "root");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public boolean createUser(String name,String email, String password, String confirmPassword){
//        if (!password.equals(confirmPassword)){
//            return false;
//        }
//        try {
//            String query = "Insert into user (name, password, email) values(?,?,?)";
//            PreparedStatement stmt = connection.prepareStatement(query);
//            stmt.setString(1,name);
//            stmt.setString(2,password);
//            stmt.setString(3,email);
//            if (stmt.executeUpdate()!=0){
//                System.out.println("Användare sparad");
//                return true;
//            }else {
//                return false;
//            }
//
//
//        }catch (SQLException e){
//            System.out.println("Något gick fel: " + e);
//        }
//        return false;
//    }
//}
