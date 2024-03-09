package com.sparta.shopapi.domain.member.dto;

import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.member.entity.enums.Gender;
import com.sparta.shopapi.domain.member.entity.enums.MemberRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "이메일 형식을 지켜주세요.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Schema(description = "사용자 이메일", example = "member@example.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Schema(description = "사용자 비밀번호", example = "Password123!")
    private String password;

    @Schema(description = "사용자 성별", example = "female")
    private String gender;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰 번호 형식을 지켜주세요.")
    @Schema(description = "사용자 휴대폰 번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "사용자 주소", example = "Seoul, South Korea")
    private String address;

    @Schema(description = "사용자 권한", example = "member")
    private String auth;

    @Schema(description = "관리자 여부", example = "false")
    private boolean admin = false;

    @Schema(description = "관리자 토큰", example = "adminToken123")
    private String adminToken = "";

    public Member toEntity(String encodedPassword, MemberRoleEnum auth) {
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .gender(Gender.valueOf(gender))
                .phoneNumber(phoneNumber)
                .address(address)
                .auth(auth)
                .build();
    }

}