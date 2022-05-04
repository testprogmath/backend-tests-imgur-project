package ru.geekbrains.tests;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.dao.AccountResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static ru.geekbrains.base.Endpoints.GET_ACCOUNT;

public class AccountTests extends BaseTest {
    ResponseSpecification accountResponseSpec;

    @Test
    void getAccountPositiveTest() {
        accountResponseSpec = positiveResponseSpecification
                .expect()
                .body("data.url", equalTo(username));

        AccountResponse response = given(requestSpecification, accountResponseSpec)
                .get(GET_ACCOUNT, username)
                .prettyPeek()
                .then()
                .extract()
                .as(AccountResponse.class);
        assertThat(response.getData().getId(), equalTo(userId));
    }

    //todo: refactor at home
    @Test
    void getAccountSettingsTest() {
        Response response = given()
                .header("Authorization", token)
                .expect()
                .body("success", is(true))
                .body("data.account_url", equalTo(username))
                .statusCode(200)
                .when()
                .get("https://api.imgur.com/3/account/testprogmath/settings")
                .prettyPeek();
        assertThat(response.jsonPath().get("data.active_emails[0]"), equalTo("anna.chemic@gmail.com"));
    }
}
