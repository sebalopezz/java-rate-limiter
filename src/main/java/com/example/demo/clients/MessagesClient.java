package com.example.demo.clients;

import com.example.demo.clients.responses.MessagesResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MessagesClient {

    private static final RestTemplate restClient = new RestTemplate();
    private static final String URL_GET_MESSAGE = "https://www.foaas.com/awesome/lemon";

    public MessagesResponse get() {
        return restClient.getForObject(URL_GET_MESSAGE, MessagesResponse.class);
    }
}
