
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage1;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @Test
    public void TransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");

        var loginPage = new LoginPage1();
        var authInfo = DataHelper.getAuthinfo();
        loginPage.validLogin(authInfo);

        $("[data-test-id=code] input").shouldBe(visible);
    }
}
