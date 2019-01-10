package com.nerdblistersteam.concierge.controller;

import com.nerdblistersteam.concierge.domain.User;
import com.nerdblistersteam.concierge.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Controller
public class ConciergeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String registrering() {
        return "/auth/registrering";
    }

    @PostMapping("/register")
    public String postRegisterData(@RequestParam String username,
                                   @RequestParam String email,
                                   @RequestParam String password) {
        System.out.println("Called postRegisterData with: " + username + email + password);
        return "/auth/registrering";
    }

    @GetMapping("/log")
    public String login() {
        return "/auth/login";
    }

    @PostMapping("/log")
    public String postLogin(@RequestParam String username,
                              @RequestParam String password) throws  Exception {

        //Nedan är endast test för att kunna se att koppling finns till databasen
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:concierge", "sa", "");
        System.out.println("Connected to test db");
        Statement statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO user VALUES (2, null, 'woop@woop.se', TRUE, '" + username +"', 'woop', '" + password + "')");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USER");

        while(resultSet.next()) {
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("email"));
            System.out.println(resultSet.getString("password"));

        }
        conn.close();

        System.out.println("Du har loggat in med: " + username + password);
        return "/auth/login";
    }
}
