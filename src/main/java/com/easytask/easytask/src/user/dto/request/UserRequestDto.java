package com.easytask.easytask.src.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    @NotNull(message = "비밀번호를 입력해 주세요")
    private String password;
    private String name;


}
