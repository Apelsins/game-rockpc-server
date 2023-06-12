package com.game.rockpc.client;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientFactory {

    private final ApplicationContext applicationContext;

    public synchronized ClientSession createSession() {
        return applicationContext.getBean(ClientSession.class);
    }
}
