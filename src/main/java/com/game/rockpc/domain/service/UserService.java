package com.game.rockpc.domain.service;

import com.game.rockpc.domain.entity.User;
import com.game.rockpc.dto.RegistrationCommandDto;
import com.game.rockpc.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signUpNewUser(final RegistrationCommandDto regCommand) {
        final User user = new User(
                regCommand.getRegistrationDto().getLogin(),
                regCommand.getRegistrationDto().getPassword(),
                LocalDateTime.now(),
                LocalDateTime.now());
        userRepository.save(user);
    }

    public void signInNewUser(final RegistrationCommandDto regCommand) {
        final User user = userRepository.findByLoginAndPassword(
                regCommand.getRegistrationDto().getLogin(),
                regCommand.getRegistrationDto().getPassword()
        ).orElseThrow(() -> new RuntimeException("Cannot find user"));

        userRepository.updateLastAuthTimestamp(user.getId(), LocalDateTime.now());
    }
}
