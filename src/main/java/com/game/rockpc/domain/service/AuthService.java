package com.game.rockpc.domain.service;

import com.game.rockpc.domain.entity.User;
import com.game.rockpc.dto.RegistrationCommandDto;
import com.game.rockpc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SessionService sessionService;
    private final UserRepository userRepository;

    public void signUpNewUser(final RegistrationCommandDto regCommand) {
        final User user = new User(
                regCommand.getRegistrationDto().getLogin(),
                regCommand.getRegistrationDto().getPassword(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        userRepository.save(user);
    }

    public void signInNewUser(final RegistrationCommandDto regCommand) {
        final User user = userRepository.findByLoginAndPassword(
                regCommand.getRegistrationDto().getLogin(),
                regCommand.getRegistrationDto().getPassword()
        ).orElseThrow(() -> new RuntimeException("Cannot find user"));

        userRepository.updateLastAuthTimestamp(user.getId());

        sessionService.createSession(
                UUID.fromString(regCommand.getRegistrationDto().getSessionId()),
                regCommand.getRegistrationDto().getLogin()
        );
    }
}
