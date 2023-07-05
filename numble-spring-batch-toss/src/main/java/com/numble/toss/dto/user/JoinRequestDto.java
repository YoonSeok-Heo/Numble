package com.numble.toss.dto.user;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JoinRequestDto {

    private String username;

    private String password;

    private String name;

    private String phone;

    private String email;

}
