package com.github.katemerek.server.controllers;


import com.github.katemerek.dto.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("localhost:9001/auth")
//public class ServerController {
//    @PostMapping
//    public ResponseEntity<HttpStatus> addPerson(@RequestBody Person personAdd) {
//        System.out.println(personAdd);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//}
