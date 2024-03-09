package com.sparta.shopapi.domain.member.controller;

import com.sparta.shopapi.domain.member.dto.SignupRequestDto;
import com.sparta.shopapi.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "member", description = "회원 관리 API")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(
            summary = "회원가입",
            description = "새로운 사용자를 회원가입합니다."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public String signup(
            @RequestBody @Valid SignupRequestDto requestDto,
            BindingResult bindingResult) {
        memberService.signup(requestDto);
        return "회원가입에 성공하였습니다.";
    }
}

