package com.game.rockpc.domain.service;

import com.game.rockpc.client.ClientSession;
import com.game.rockpc.domain.entity.GameHistory;
import com.game.rockpc.dto.CommandType;
import com.game.rockpc.dto.GameCommandDto;
import com.game.rockpc.dto.ResultDto;
import com.game.rockpc.repository.GameHistoryRepository;
import com.game.rockpc.server.ServerRunnerInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
//@AllArgsConstructor
public class GameService {

    private final SessionService sessionService;

    private final GameHistoryRepository gameHistoryRepository;

    private final ServerRunnerInstance server;

    public GameService(
            final SessionService sessionService,
            final GameHistoryRepository gameHistoryRepository,
            @Lazy final ServerRunnerInstance server
    ) {
        this.sessionService = sessionService;
        this.gameHistoryRepository = gameHistoryRepository;
        this.server = server;
    }

    public String play(final GameCommandDto gameCommandDto) {
        final ClientSession currentSession = sessionService.getSessionFromPoll(UUID.fromString(gameCommandDto.getSessionId()));
        if (!currentSession.isSessionInit()) {
            throw new RuntimeException("Need Init!");
        }

        final CommandType userChoice = gameCommandDto.getCommandType();
        final CommandType serverChoice = getRandomChoice();

        String result = null;
        if (userChoice == CommandType.PAPER && serverChoice == CommandType.PAPER) {
            result = "Draw";
        } else if (userChoice == CommandType.PAPER && serverChoice == CommandType.ROCK) {
            result = "Win";
        } else if (userChoice == CommandType.PAPER && serverChoice == CommandType.SCISSORS) {
            result = "Lose";
        } else if (userChoice == CommandType.ROCK && serverChoice == CommandType.PAPER) {
            result = "Lose";
        } else if (userChoice == CommandType.ROCK && serverChoice == CommandType.ROCK) {
            result = "Draw";
        } else if (userChoice == CommandType.ROCK && serverChoice == CommandType.SCISSORS) {
            result = "Win";
        } else if (userChoice == CommandType.SCISSORS && serverChoice == CommandType.PAPER) {
            result = "Win";
        } else if (userChoice == CommandType.SCISSORS && serverChoice == CommandType.ROCK) {
            result = "Lose";
        } else if (userChoice == CommandType.SCISSORS && serverChoice == CommandType.SCISSORS) {
            result = "Draw";
        }

        log.info("YOU '{}'! YOU CHOICE:'{}', ENEMY CHOICE '{}'", result, userChoice, serverChoice);


        final GameHistory gameHistory = new GameHistory(currentSession.getClientId(), 1, userChoice.toString(), result);
        gameHistoryRepository.save(gameHistory);

        server.sendTCP(new ResultDto(result));

        return result;
    }

    private CommandType getRandomChoice() {
        final CommandType[] commandTypes = {CommandType.ROCK, CommandType.SCISSORS, CommandType.PAPER};
        final Random random = new Random();
        final int randomIndex = random.nextInt(commandTypes.length);
        return commandTypes[randomIndex];
    }
}
