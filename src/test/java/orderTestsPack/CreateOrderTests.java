package orderTestsPack;

import base.OrderStepsApi;
import base.UserStepsApi;
import base.util.GeneratorData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTests {

    private OrderStepsApi orderStepsApi;
    private UserStepsApi userStepsApi;
    private User testUser;
    private String accessToken;

    @Before
    public void setUp(){
        userStepsApi = new UserStepsApi();
        orderStepsApi = new OrderStepsApi();
       testUser = GeneratorData.createUser();
//        userStepsApi.userRegister(testUser);
       // testOrder.setIngredientsList(orderStepsApi.getIngredientsList().extract().path("data._id"));

    }
    @After
    public void teardown(){
        ValidatableResponse response = userStepsApi.userBasicAuth(testUser);
        accessToken = response.extract().path("accessToken");
        if (accessToken == null)
            return;
        else userStepsApi.delite(accessToken);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void creatingOrderUnauth() {
        orderStepsApi.setIngredientsList();
        orderStepsApi.createOrderUnauth();
        orderStepsApi.getOrderResponse()
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .and()
                .body("order.number",notNullValue());
    }
    @Test
    @DisplayName("Cоздание заказа без ингридиентов")
    public void creatinfOrderNoIngredients(){
        orderStepsApi.createOrderNoIngredients();
        orderStepsApi.getOrderResponse()
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message",equalTo("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Создание заказа без ингридиентов")
    public void creatingfOrderWithIndalidHash(){
        orderStepsApi.createOrderWithInvalidHash();
        orderStepsApi.getOrderResponse()
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);

    }
    @Test
    @DisplayName("Создание заказа зарегестритованным пользователем")
    public void creatingOrderAuth(){
        ValidatableResponse response = userStepsApi.userRegister(testUser);
        accessToken = response.extract().path("accessToken");
        orderStepsApi.setIngredientsList();
        orderStepsApi.createOrderAuth(accessToken);
        orderStepsApi.getOrderResponse()
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("order.number",notNullValue());
    }

    @Test
    public void checkUserOrders(){
        ValidatableResponse response = userStepsApi.userRegister(testUser);
        accessToken = response.extract().path("accessToken");
        orderStepsApi.getUserOrders(accessToken);
        orderStepsApi.getOrderResponse()
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success",equalTo(true))
                .and()
                .body("total",notNullValue());

    }
    @Test
    public void checkUserOrdersNoAuth(){
        orderStepsApi.getUserOrders("");
        orderStepsApi.getOrderResponse()
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message",equalTo("You should be authorised"));

    }
}
