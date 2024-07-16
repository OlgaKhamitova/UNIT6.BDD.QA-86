package ru.netology.data;

import lombok.Getter;
import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Getter
    @Value
    public static class AuthInfo {
        public String login;
        public String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }

    public static AuthInfo getAuthinfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }
    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo){
        return new VerificationCode("12345");
    }
}
