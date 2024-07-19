package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import ru.netology.dto.CardInfo;
import ru.netology.utils.DataHelper;
import ru.netology.page.LoginPage;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTransferTest {
    private static final List<CardInfo> CARDS_INFO = DataHelper.getCards();
    private static final CardInfo FIRST_CARD = CARDS_INFO.get(0);
    private static final CardInfo SECOND_CARD = CARDS_INFO.get(1);

    @Test
    public void transferMoneyBetweenOwnCards() {
        var transferSum = 5000;

        open("http://localhost:9999");

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthinfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var cardsBalance = dashboardPage.getCardsBalance();
        FIRST_CARD.setBalance(cardsBalance.get("1"));
        SECOND_CARD.setBalance(cardsBalance.get("2"));

        var replenishCard = dashboardPage.clickOnButton();
        dashboardPage = replenishCard.replenishCard(SECOND_CARD.getNumber(), transferSum);
        SECOND_CARD.setBalance(SECOND_CARD.getBalance() - transferSum);
        FIRST_CARD.setBalance(FIRST_CARD.getBalance() + transferSum);

        cardsBalance = dashboardPage.getCardsBalance();
        var actualFirstCardBalance = cardsBalance.get("1");
        var actualSecondCardBalance = cardsBalance.get("2");
        assertEquals(FIRST_CARD.getBalance(), actualFirstCardBalance);
        assertEquals(SECOND_CARD.getBalance(), actualSecondCardBalance);
    }

    @Test
    public void shouldNotAllowTransferMoreThenBalance() {
        var transferSum = SECOND_CARD.getBalance() + 1;
        open("http://localhost:9999");

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthinfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var replenishCard = dashboardPage.clickOnButton();
        assertThrows(NoSuchElementException.class, ()-> replenishCard.replenishCard(SECOND_CARD.getNumber(), transferSum), "Should not be redirected to dashboard page");
    }

    @Test
    public void shouldNotAcceptWrongVerificationCode() {
        open("http://localhost:9999");

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthinfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerify(DataHelper.getInvalidVerificationCode());
        $("[data-test-id=error-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=error-notification] .notification__title").shouldHave(Condition.exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }
}
