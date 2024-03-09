package com.sparta.shopapi.domain.member.entity.enums;

import lombok.Getter;

@Getter
public enum MemberRoleEnum {
    member(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한

    private final String authority;

    MemberRoleEnum(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_member";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}