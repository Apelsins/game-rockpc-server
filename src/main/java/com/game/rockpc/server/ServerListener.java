package com.game.rockpc.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.rockpc.domain.service.AuthService;
import com.game.rockpc.domain.service.GameService;
import com.game.rockpc.domain.service.SessionService;
import com.game.rockpc.dto.CommandType;
import com.game.rockpc.dto.GameCommandDto;
import com.game.rockpc.dto.InitSessionCommandDto;
import com.game.rockpc.dto.RegistrationCommandDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
@AllArgsConstructor
public class ServerListener extends Listener {

    private final AuthService authService;
    private final SessionService sessionService;
    private final GameService gameService;

    @Override
    public void connected(final Connection c) {
        log.info("На сервер подключился '{}'", c.getRemoteAddressTCP().getHostString());
    }

    @Override
    public void received(final Connection c, final Object incomeObj) {
        if (incomeObj instanceof final RegistrationCommandDto regCommand) {
            if (regCommand.getCommandType() == CommandType.SIGN_UP) {
                authService.signUpNewUser(regCommand);
            } else if (regCommand.getCommandType() == CommandType.SIGN_IN) {
                authService.signInNewUser(regCommand);
            }
        }

        if (incomeObj instanceof final InitSessionCommandDto initCommand) {
            sessionService.initSession(UUID.fromString(initCommand.getSessionId()));
        }

        if (incomeObj instanceof final GameCommandDto gameCommandDto) {
            gameService.play(gameCommandDto);
        }
    }

    @Override
    public void disconnected(final Connection c) {
        log.info("Клиент покинул сервер!");
    }
}
