import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {

        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[name='login']").setValue(registeredUser.getLogin());
        $("[name='password']").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[id='root']").shouldBe(exactText("Личный кабинет"));
    }

    @Test
    void shouldErrorIfNotRegisteredUser() {

        var newUser = DataGenerator.Registration.getUser("active");

        $("[name='login']").setValue(newUser.getLogin());
        $("[name='password']").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']")
                .shouldBe(visible).shouldHave(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldErrorLoginIfRegisteredBlockedUser() {

        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");

        $("[name='login']").setValue(blockedUser.getLogin());
        $("[name='password']").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']")
                .shouldBe(visible).shouldHave(exactText("Ошибка Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldErrorLoginIfInvalidUserName() {

        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[name='login']").setValue(DataGenerator.Registration.getLogin());
        $("[name='password']").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']")
                .shouldBe(visible).shouldHave(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldErrorLoginIfInvalidUserPassword() {

        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[name='login']").setValue(registeredUser.getLogin());
        $("[name='password']").setValue(DataGenerator.Registration.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']")
                .shouldBe(visible).shouldHave(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }


}
