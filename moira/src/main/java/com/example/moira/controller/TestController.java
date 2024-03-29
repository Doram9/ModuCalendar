package com.example.moira.controller;

import com.example.moira.entity.User;
import com.example.moira.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    @RequestMapping(value = "/main")
    @ResponseBody
    public String test() {
        return "HelloWorld";
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getAllUser() {

        ResponseEntity<User> user = null;
        return user;
    }

    @PostMapping(value = "/user")
    public boolean insertUser(@RequestBody User user) {

        User newUser = User.builder()
                .id(user.getId())
                .pw(user.getPw())
                .name(user.getName())
                .age(user.getAge()).build();

        userRepository.save(newUser);

        return true;
    }



    @GetMapping(value = "/user")
    public ResponseEntity<User> getAllUser() {

        ResponseEntity<User> user = null;
        return user;
    }

    @PostMapping(value = "/user")
    public boolean insertUser(@RequestBody User user) {

        User newUser = User.builder()
                .id(user.getId())
                .pw(user.getPw())
                .name(user.getName())
                .role(0).build();

        userRepository.save(newUser);

        return true;
    }

    @PostMapping(value = "/test")
    public boolean insertTest() {
        User newUser = User.builder()
                .id("test")
                .pw("1111")
                .name("foobar")
                .role(0).build();

        userRepository.save(newUser);

        return true;
    }

    @PatchMapping(value = "/test")
    public ResponseEntity<User> updateTest() {
        User newUser = userRepository.findById("test").get();

        return ResponseEntity.ok().body(newUser);
    }

    @DeleteMapping(value = "/test")
    public boolean deleteTest() {
        User newUser = userRepository.findById("test").get();
        userRepository.delete(newUser);

        return true;
    }


}
