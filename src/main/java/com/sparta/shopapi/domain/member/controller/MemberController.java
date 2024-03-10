package com.sparta.shopapi.domain.member.controller;

import com.sparta.shopapi.domain.member.dto.SignupRequestDto;
import com.sparta.shopapi.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "member", description = "회원을 관리합니다.")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(
            summary = "회원가입",
            description = "작성 폼에 맞춰 회원가입 합니다."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/signup")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto) {
        memberService.signup(requestDto);
        return "회원가입에 성공하였습니다.";
    }
}

