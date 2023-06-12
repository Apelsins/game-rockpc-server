package com.game.rockpc.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientSession {

    private boolean isSessionInit;
    private Long clientId;
    private int gameStep;
}
