package com.game.rockpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationCommandDto {

    private CommandType commandType;
    private RegistrationDto registrationDto;

}
