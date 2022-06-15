package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.user.RegistrationInfo;
import ru.netology.utils.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PersonalAreaTest {
    @BeforeEach
    void shouldOpenWebApp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;

    }

    @Test
    void shouldValidUser() {
        RegistrationInfo user = DataGenerator.Registration.generateByUser("active");

        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login] .button__text").click();
        $("[id=root]").should(exactText("  Личный кабинет"), Duration.ofSeconds(15));
    }

    @Test
    void shouldBlockedUser() {
        RegistrationInfo user = DataGenerator.Registration.generateByUser("blocked");

        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login] .button__text").click();
        $("[data-test-id=error-notification] .notification__content")
                .should(exactText("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    void shouldInvalidLoginUser() {
        RegistrationInfo user = DataGenerator.Registration.generateByUser("active");

        $("[data-test-id=login] .input__control").setValue(DataGenerator.invalidLoginUser());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login] .button__text").click();
        $("[data-test-id=error-notification] .notification__content")
                .should(exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    void shouldInvalidPasswordUser() {
        RegistrationInfo user = DataGenerator.Registration.generateByUser("active");

        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(DataGenerator.invalidPasswordUser());
        $("[data-test-id=action-login] .button__text").click();
        $("[data-test-id=error-notification] .notification__content")
                .should(exactText("Ошибка! Неверно указан логин или парол"), Duration.ofSeconds(15));
    }

}
