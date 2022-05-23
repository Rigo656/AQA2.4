package tests;

import date.DataHelper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    public void shouldTransferMoneyFromSecondCardToFirstCard() {
        val dashboardPage = new DashboardPage();

        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        val infoCard = DataHelper.getSecondCardInfo();
        String sum = "10000";
        transferPage.transferForm(sum, infoCard);
        assertEquals(balanceFirstCard + Integer.parseInt(sum), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard - Integer.parseInt(sum), dashboardPage.getSecondCardBalance());
    }

    @Test
    public void shouldTransferMoneyFromFirstCardToSecond() {
        val dashboardPage = new DashboardPage();
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondCardButton();
        val infoCard = DataHelper.getFirstCardInfo();
        String sum = "10000";
        transferPage.transferForm(sum, infoCard);
        assertEquals(balanceFirstCard - Integer.parseInt(sum), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard + Integer.parseInt(sum), dashboardPage.getSecondCardBalance());
    }

    @Test
    public void shouldTransferMoneyUpperLimitAndGetError() {
        val dashboardPage = new DashboardPage();
        val transferPage = dashboardPage.secondCardButton();
        val infoCard = DataHelper.getFirstCardInfo();
        String sum = "20000";
        transferPage.transferForm(sum, infoCard);
        transferPage.getError();
    }
}

