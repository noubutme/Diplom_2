package base;

import base.util.GeneratorData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.User;
import static io.restassured.RestAssured.given;
public class UserStepsApi extends RestClient{
    private static final String Register_URL = BASE_URI + "auth/register";
    private static final String Auth_URL = BASE_URI + "auth/login";
    private static final String User_URL = BASE_URI + "auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse userRegister(User user){
        return given()
                .spec(getReqSpec())
                .body(user)
                .when()
                .post(Register_URL)
                .then();
    }
    @Step("Авторизация по логину и паролю")
    public ValidatableResponse userBasicAuth(User user){
        return given()
                .spec(getReqSpec())
                .body(new User(user.getEmail(),user.getPassword()))
                .when()
                .post(Auth_URL)
                .then();


    }

//    @Step("Авторизация по токену")
//    public ValidatableResponse userBasicAuth(String accessToken){
//        return given()
//                .spec(getReqSpec())
//                .auth().oauth2(accessToken)
//                .when()
//                .post(Auth_URL)
//                .then();
//    }

    @Step("Редактирование данных с авторизацией")
     public ValidatableResponse editWithAuth(String accessToken,User user){
        return  given()
                .spec(getReqSpec())
                .header("Authorization",accessToken)
                .body(new User(user.getEmail(),user.getPassword(),user.getName()))
                .when()
                .patch(User_URL)
                .then();
    }
    @Step("Редактирование данных без авторизацией")
    public ValidatableResponse editWithNoAuth(User user){
        return  given()
                .spec(getReqSpec())
                .body(user)
                .when()
                .patch(User_URL)
                .then();
    }

    @Step("Удаление")
    public ValidatableResponse delite(String accessToken){
        return given()
                .spec(getReqSpec())
                .auth().oauth2(accessToken)
                .when()
                .delete(User_URL)
                .then();
    }
}
