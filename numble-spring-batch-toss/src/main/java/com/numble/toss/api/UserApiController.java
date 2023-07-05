package com.numble.toss.api;


import com.numble.toss.common.ApiResponse;
import com.numble.toss.dto.user.JoinRequestDto;
import com.numble.toss.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("")
    public ResponseEntity<ApiResponse> join(@RequestBody JoinRequestDto joinRequestDto){
        ApiResponse apiResponse = new ApiResponse();
        log.info("UserApiController join: {}", joinRequestDto);

        apiResponse = userService.join(joinRequestDto);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}

