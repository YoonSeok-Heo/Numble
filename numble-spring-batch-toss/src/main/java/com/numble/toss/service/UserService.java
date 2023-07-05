package com.numble.toss.service;

import com.numble.toss.common.ApiResponse;
import com.numble.toss.common.DateUtils;
import com.numble.toss.dto.user.JoinRequestDto;
import com.numble.toss.entity.Authority;
import com.numble.toss.entity.UserEntity;
import com.numble.toss.repository.UserRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passordEncoder;

    public ApiResponse join(JoinRequestDto joinRequestDto) {
        ApiResponse apiResponse = new ApiResponse();

        UserEntity joinUserEntity = UserEntity.builder()
                .username(joinRequestDto.getUsername())
                .password(passordEncoder.encode(joinRequestDto.getPassword()))
                .phone(joinRequestDto.getPhone())
                .email(joinRequestDto.getEmail())
                .name(joinRequestDto.getName())
                .joinDate(DateUtils.nowDate())
                .lastModifiedDate(DateUtils.nowDate())
                .build()
                ;

        joinUserEntity.setRoles(List.of());

        UserEntity savedUserEntity = userRepository.save(joinUserEntity);

        log.info("UserService: {}", savedUserEntity);

        apiResponse.putData("userDate", savedUserEntity);
        return apiResponse;
    }
}
