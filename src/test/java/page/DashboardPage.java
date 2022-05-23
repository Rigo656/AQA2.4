package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    private final SelenideElement cardFirstButton = $$("[data-test-id=action-deposit]").first();
    private final SelenideElement cardSecondButton = $$("[data-test-id=action-deposit]").last();
    private final SelenideElement cardFirst = $$(".list__item").first();
    private final SelenideElement cardSecond = $$(".list__item").last();

    public CardReplenishmentPage firstCardButton() {
        cardFirstButton.click();
        return new CardReplenishmentPage();
    }

    public CardReplenishmentPage secondCardButton() {
        cardSecondButton.click();
        return new CardReplenishmentPage();
    }


    public int getFirstCardBalance() {
        val text = cardFirst.text();
        return extractBalanceCard(text);
    }

    public int getSecondCardBalance() {
        val text = cardSecond.text();
        return extractBalanceCard(text);
    }

    private int extractBalanceCard(String text) {
        String balanceStart = "баланс: ";
        val start = text.indexOf(balanceStart);
        String balanceFinish = " р.";
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
