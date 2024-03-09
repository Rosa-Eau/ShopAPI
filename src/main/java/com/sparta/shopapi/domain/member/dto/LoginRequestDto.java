package com.sparta.shopapi.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "이메일 형식을 지켜주세요.")
    @NotBlank(message = "이메일을 입력하세요.")
    @Schema(description = "사용자 이메일", example = "member@example.com")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Schema(description = "사용자 비밀번호", example = "passworD123!")
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}