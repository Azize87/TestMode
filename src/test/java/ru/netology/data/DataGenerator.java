package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public DataGenerator() {

    }

    private static void createUser(RegistrationDto newUser) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(newUser) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static class Registration {

        public static RegistrationDto getUser(String userStatus) {
            Faker faker = new Faker(new Locale("ru"));
            return new RegistrationDto(
                    faker.name().username(),
                    faker.lorem().characters(8, 16),
                    userStatus);
        }

        public static RegistrationDto getRegisteredUser(String status) {
            var user = getUser(status);
            createUser(user);
            return user;
        }

        public static String getLogin() {
            Faker faker = new Faker(new Locale("ru"));
            return faker.name().username();
        }

        public static String getPassword() {
            Faker faker = new Faker(new Locale("ru"));
            return faker.lorem().characters(8, 16);
        }

    }
}
