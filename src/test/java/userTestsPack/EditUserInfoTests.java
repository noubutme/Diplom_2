package userTestsPack;

import base.UserStepsApi;
import base.util.GeneratorData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pojo.User;

import static org.hamcrest.Matchers.equalTo;


public class EditUserInfoTests {
    private UserStepsApi userStepsApi;
    private User testUser;
    private String accessToken;

    @Before
    public void setUp(){
        userStepsApi =new UserStepsApi();
        testUser = GeneratorData.createUser();
    }
    @After
    public void tearDown(){
        userStepsApi.delite(accessToken);
    }
    @Test
    @DisplayName("Изменение данных пользователя c авторизацией: email")
    public void editEmailAuth(){
        ValidatableResponse response = userStepsApi.userRegister(testUser);
        accessToken = response.extract().path("accessToken");
        testUser.setEmail(GeneratorData.generateEmail());
        userStepsApi.editWithAuth(accessToken,testUser)
                .assertThat()
                .statusCode(200)
                .and()
                .body("user.email",equalTo(testUser.getEmail()));
    }
    @Test
    @DisplayName("Изменение данных пользователя без авторизации: email")
    public void editEmailNoAuth(){
        ValidatableResponse response = userStepsApi.userRegister(testUser);
        testUser.setEmail(GeneratorData.generateEmail());
        userStepsApi.editWithNoAuth(testUser)
                .assertThat()
                .statusCode(401)
                .and()
                .body("message",equalTo("You should be authorised"));
        accessToken = response.extract().path("accessToken");
    }
    @Test
    @DisplayName("Изменение данных пользователя c авторизацией: email")
    public void editNameAuth(){
        ValidatableResponse response = userStepsApi.userRegister(testUser);
        accessToken = response.extract().path("accessToken");
        testUser.setName(GeneratorData.generateName());
        userStepsApi.editWithAuth(accessToken,testUser)
                .assertThat()
                .statusCode(200)
                .and()
                .body("user.email",equalTo(testUser.getEmail()));
    }
    @Test
    @DisplayName("Изменение данных пользователя без авторизации: email")
    public void editNameNoAuth(){
        ValidatableResponse response = userStepsApi.userRegister(testUser);
        testUser.setName(GeneratorData.generateName());
        userStepsApi.editWithNoAuth(testUser)
                .assertThat()
                .statusCode(401)
                .and()
                .body("message",equalTo("You should be authorised"));
        accessToken = response.extract().path("accessToken");
    }
    @Test
    @DisplayName("Изменение данных пользователя c авторизацией: email")
    public void editPasswordAuth(){
        ValidatableResponse response = userStepsApi.userRegister(testUser);
        accessToken = response.extract().path("accessToken");
        testUser.setPassword(GeneratorData.generatePassword());
        userStepsApi.editWithAuth(accessToken,testUser)
                .assertThat()
                .statusCode(200)
                .and()
                .body("user.email",equalTo(testUser.getEmail()));
    }
    @Test
    @DisplayName("Изменение данных пользователя без авторизации: email")
    public void editPasswordNoAuth(){
        ValidatableResponse response = userStepsApi.userRegister(testUser);
        testUser.setPassword(GeneratorData.generatePassword());
        userStepsApi.editWithNoAuth(testUser)
                .assertThat()
                .statusCode(401)
                .and()
                .body("message",equalTo("You should be authorised"));
        accessToken = response.extract().path("accessToken");
    }
}
