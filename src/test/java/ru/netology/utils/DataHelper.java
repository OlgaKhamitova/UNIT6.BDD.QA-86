package ru.netology.utils;

import ru.netology.constants.TestConstants;
import ru.netology.dto.AuthInfo;
import ru.netology.dto.CardInfo;
import ru.netology.dto.VerificationCode;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    public static AuthInfo getAuthinfo() {
        return new AuthInfo(TestConstants.TRANSFER_TEST_USERNAME, TestConstants.TRANSFER_TEST_PASSWORD);
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode(TestConstants.TRANSFER_TEST_VERIFICATION_CODE);
    }

    public static VerificationCode getInvalidVerificationCode() {
        var validVerificationCode = TestConstants.TRANSFER_TEST_VERIFICATION_CODE;
        var invalidVerificationCode = validVerificationCode.substring(0, validVerificationCode.length() - 1);
        return new VerificationCode(invalidVerificationCode);
    }

    public static List<CardInfo> getCards() {
        return new ArrayList<>() {{
            add(new CardInfo(TestConstants.FIRST_CARD_NUMBER));
            add(new CardInfo(TestConstants.SECOND_CARD_NUMBER));
        }};
    }
}
