package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {
    String inputDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Test
    void shouldRegisterByDelivery() {
        open("http://localhost:9999");
        //SelenideElement form = $("form");
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Василий Дегин");
        $("[data-test-id=phone] input").setValue("+79110000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно")).waitUntil(Condition.visible, 15000);
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + inputDate));
    }

    @Test
    void shouldNotRegisterIncorrectDate() {
        open("http://localhost:9999");
        //SelenideElement form = $("form");
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(inputDate="05.03.2021");
        $("[data-test-id=name] input").setValue("Василий Дегин");
        $("[data-test-id=phone] input").setValue("+79110000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotRegisterIncorrectName() {
        open("http://localhost:9999");
        //SelenideElement form = $("form");
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Vasya");
        $("[data-test-id=phone] input").setValue("+79110000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotRegisterIncorrectNumber() {
        open("http://localhost:9999");
        //SelenideElement form = $("form");
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Василий Дегин");
        $("[data-test-id=phone] input").setValue("+791100000001");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotRegisterIncorrectCity() {
        open("http://localhost:9999");
        //SelenideElement form = $("form");
        $("[data-test-id=city] input").setValue("Набережные Челны");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Василий Дегин");
        $("[data-test-id=phone] input").setValue("+79110000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна."));
    }
}
