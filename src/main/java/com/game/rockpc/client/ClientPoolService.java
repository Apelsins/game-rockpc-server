package com.game.rockpc.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ClientPoolService {

    private final ClientFactory clientFactory;

    private final Random random = new Random();
    private final ConcurrentHashMap<String, ClientInstance> clientPool = new ConcurrentHashMap<>();

//    @PostConstruct
//    public void init() throws InterruptedException {
//        Thread.sleep(5000);
//        clientPool.put("client-1", clientFactory.createClientInstance());
//    }

    public void createClient() {
         clientPool.put("client-" + random.nextInt(10000), clientFactory.createClientInstance());
    }
}
