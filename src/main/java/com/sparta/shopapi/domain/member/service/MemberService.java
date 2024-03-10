package com.sparta.shopapi.domain.member.service;

import com.sparta.shopapi.domain.member.dto.SignupRequestDto;
import com.sparta.shopapi.domain.member.entity.enums.MemberRoleEnum;
import com.sparta.shopapi.domain.member.repository.MemberRepository;
import com.sparta.shopapi.global.handler.exception.BusinessException;
import com.sparta.shopapi.global.handler.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "7870ba8089050930cdd1e2616e842ff76b0880770ad04798dd947226e9ef90234709ff5d64533b36556b31ec8cc001ff17d9be9ac469f03ad10efac8560b40ea";

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        if(memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        log.info("admin? : " + requestDto.isAdmin());

        MemberRoleEnum auth = MemberRoleEnum.member;
        if (requestDto.isAdmin()) {
            log.info(requestDto.getAdminToken());
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new BusinessException(ErrorCode.ACCESS_DENIED_ADMIN);
            }
            auth = MemberRoleEnum.ADMIN;
        }

        // 사용자 등록
        memberRepository.save(requestDto.toEntity(encodedPassword, auth));
    }
}