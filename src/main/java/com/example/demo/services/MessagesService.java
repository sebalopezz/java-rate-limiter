package com.example.demo.services;

import com.example.demo.clients.MessagesClient;
import com.example.demo.clients.responses.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {

    @Autowired
    private MessagesClient client;

    public MessagesResponse get() {
        return client.get();
    }
}
