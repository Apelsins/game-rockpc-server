package com.game.rockpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameCommandDto {

    private String sessionId;
    private CommandType commandType;

}
