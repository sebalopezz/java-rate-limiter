package com.example.demo.controllers;

import com.example.demo.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/message")
    public ResponseEntity getMessage() {
        var response = messagesService.get();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
