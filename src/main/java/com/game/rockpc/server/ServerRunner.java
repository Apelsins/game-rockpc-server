package com.game.rockpc.server;

import com.esotericsoftware.kryonet.Server;
import com.game.rockpc.config.ConnectionProperties;
import com.game.rockpc.dto.CommandType;
import com.game.rockpc.dto.GameCommandDto;
import com.game.rockpc.dto.RegistrationCommandDto;
import com.game.rockpc.dto.RegistrationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ServerRunner implements ApplicationRunner {

    private final ConnectionProperties connectionProperties;
    private final ServerListener serverListener;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        log.info("Создаем сервер");

        final Server server = new Server();
        server.bind(connectionProperties.getPort());

        server.getKryo().register(CommandType.class);
        server.getKryo().register(RegistrationDto.class);
        server.getKryo().register(RegistrationCommandDto.class);
        server.getKryo().register(GameCommandDto.class);


        server.start();
        server.addListener(serverListener);
    }
}
