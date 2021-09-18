package com.ticoyk.sqstudent.api.auth.user.attributes;

import java.util.Arrays;

public enum Authority {

    ADMIN("admin"), MANAGER("manager"), APPUSER("app_user"), COMPANYUSER("company_user");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return this.authority;
    }

    public static boolean contains(String name) {
        return Arrays.stream(Authority.values()).anyMatch( value -> value.toString().equals(name.toLowerCase()));
    }

}
