package ru.netology.ibank.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.ibank.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.ibank.data.DataGenerator.Registration.getUser;
import static ru.netology.ibank.data.DataGenerator.getRandomLogin;
import static ru.netology.ibank.data.DataGenerator.getRandomPassword;

public class IBankTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void registeredAndActiveStatus() {
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    public void registeredAndBlockedStatus() {
        var registeredUser = getRegisteredUser("blocked");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка"));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    public void notRegisteredAndActiveStatus() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка"));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    public void incorrectLoginRegistered() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка"));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    public void incorrectPasswordRegistered() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка"));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }
}
