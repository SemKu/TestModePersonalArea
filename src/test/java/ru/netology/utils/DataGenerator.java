package ru.netology.utils;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import ru.netology.user.RegistrationInfo;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("eg"));
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static void newUser(RegistrationInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @UtilityClass
    public static class Registration {
        public static RegistrationInfo generateByUser(String status) {
            RegistrationInfo user = new RegistrationInfo(
                    faker.name().username(),
                    faker.internet().password(),
                    status
            );
            newUser(user);
            return user;
        }
    }

    public static String invalidLoginUser() {
        return faker.name().username();
    }

    public static String invalidPasswordUser() {
        return faker.internet().password();
    }
}