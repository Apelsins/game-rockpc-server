package com.game.rockpc.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.rockpc.domain.service.UserService;
import com.game.rockpc.dto.CommandType;
import com.game.rockpc.dto.RegistrationCommandDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@AllArgsConstructor
public class ServerListener extends Listener {

    private final UserService userService;

    @Override
    public void connected(final Connection c) {
        log.info("На сервер подключился '{}'", c.getRemoteAddressTCP().getHostString());
    }

    //Используется когда клиент отправляет пакет серверу
    @Override
    public void received(final Connection c, final Object incomeObj) {
        if (incomeObj instanceof final RegistrationCommandDto regCommand) {
            if (regCommand.getCommandType() == CommandType.SIGN_UP) {
                userService.signUpNewUser(regCommand);
            } else if (regCommand.getCommandType() == CommandType.SIGN_IN) {
                userService.signInNewUser(regCommand);
            }
        }
    }

    //Используется когда клиент покидает сервер.
    @Override
    public void disconnected(final Connection c) {
        log.info("Клиент покинул сервер!");
    }
}
