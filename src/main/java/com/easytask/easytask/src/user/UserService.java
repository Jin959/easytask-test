package com.easytask.easytask.src.user;


import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.jwt.TokenProvider;
import com.easytask.easytask.src.user.dto.request.UserLoginDto;
import com.easytask.easytask.src.user.dto.request.UserRequestDto;
import com.easytask.easytask.src.user.dto.request.AbilitySettingRequestDto;
import com.easytask.easytask.src.user.dto.response.UserResponseDto;
import com.easytask.easytask.src.user.entity.PossibleTask;
import com.easytask.easytask.src.user.entity.Role;
import com.easytask.easytask.src.user.entity.TaskAbility;
import com.easytask.easytask.src.user.entity.User;
import com.easytask.easytask.src.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.easytask.easytask.common.jwt.JwtFilter.AUTHORIZATION_HEADER;
import static com.easytask.easytask.common.response.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final PasswordEncoder passwordEncoder;




    public String login(UserLoginDto loginDto) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + jwt);


            System.out.println("jwt = " + jwt);
            return jwt;
        } catch (Exception e){
            throw new BaseException(NOT_FIND_USER);
        }
    }


    public UserResponseDto registerUser(UserRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new BaseException(REGISTERED_USER);
        }
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return new UserResponseDto(user);

    }

    public UserResponseDto SkillSetting(AbilitySettingRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BaseException(NOT_FIND_USER));
        for(Map<String,String> a : requestDto.getPossibleTask()){
            PossibleTask possibleTask = PossibleTask.builder()
                    .user(user)
                    .categoryBig(a.keySet().iterator().next())
                    .categorySmall(a.get(a.keySet().iterator().next()))
                    .build();
            user.addPossibleTask(possibleTask);
        }
        for(Map<String,String> a : requestDto.getTaskAbility()){
            TaskAbility taskAbility = TaskAbility.builder()
                    .user(user)
                    .categoryBig(a.keySet().iterator().next())
                    .categorySmall(a.get(a.keySet().iterator().next()))
                    .build();
            user.addTaskAbility(taskAbility);
        }

        return new UserResponseDto(user);
    }

    public void updateUser(UserRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BaseException(NOT_FIND_USER));
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.updateUser(requestDto);

    }


    public UserResponseDto getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        return new UserResponseDto(user);
    }

    public List<UserResponseDto> getAllUser(Pageable pageable) {
        Page<User> userList = userRepository.findAll(pageable);
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for(User a : userList){
            userResponseDtoList.add(new UserResponseDto(a));
        }
        return userResponseDtoList;
    }


}

