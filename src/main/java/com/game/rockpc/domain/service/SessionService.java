package com.game.rockpc.domain.service;

import com.game.rockpc.client.ClientFactory;
import com.game.rockpc.client.ClientSession;
import com.game.rockpc.domain.entity.User;
import com.game.rockpc.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class SessionService {

    private final ClientFactory clientFactory;
    private final UserRepository userRepository;

    private final ConcurrentHashMap<UUID, ClientSession> sessionPool = new ConcurrentHashMap<>();

    public void createSession(@NonNull final UUID sessionId, @NonNull final String login) {
        final User user = userRepository.findByLogin(login).orElseThrow(RuntimeException::new);
        final ClientSession newSession = new ClientSession(false, user.getId(), 0);

        sessionPool.put(sessionId, newSession);
    }

    public void initSession(@NonNull final UUID sessionId) {
        final ClientSession clientSession = sessionPool.get(sessionId);
        clientSession.setSessionInit(true);
    }

    public ClientSession getSessionFromPoll(@NonNull final UUID sessionId) {
        return sessionPool.get(sessionId);
    }

}
