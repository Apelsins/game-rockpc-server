package com.game.rockpc.server;

import com.esotericsoftware.kryonet.Server;
import com.game.rockpc.config.ConnectionProperties;
import com.game.rockpc.dto.CommandType;
import com.game.rockpc.dto.GameCommandDto;
import com.game.rockpc.dto.InitSessionCommandDto;
import com.game.rockpc.dto.RegistrationCommandDto;
import com.game.rockpc.dto.RegistrationDto;
import com.game.rockpc.dto.ResultDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ServerRunnerInstance implements ApplicationRunner {

    private final ConnectionProperties connectionProperties;
    private final ServerListener serverListener;

    private final Server server = new Server();

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        log.info("Создаем сервер");

        final Server server = new Server();
        server.bind(connectionProperties.getPort());

        server.getKryo().register(CommandType.class);
        server.getKryo().register(RegistrationDto.class);
        server.getKryo().register(RegistrationCommandDto.class);
        server.getKryo().register(GameCommandDto.class);
        server.getKryo().register(InitSessionCommandDto.class);
        server.getKryo().register(ResultDto.class);


        server.start();
        server.addListener(serverListener);
    }

    public void sendTCP(@NonNull final Object msg) {
        server.sendToAllTCP(msg);
    }
}
