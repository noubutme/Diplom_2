package base;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

import java.util.*;

public class OrderStepsApi extends RestClient{
    private static final String getIngredientsList = BASE_URI + "ingredients";
    private static final String Order = BASE_URI + "orders";
    private static final String getAllOrders = Order+"/all";
    private Response orderResponse;
    private List<String> testOrder = new ArrayList<>();




    @Step("Извлечение тела ответа")
    public Response getOrderResponse() {
        return orderResponse;
    }
    @Step("Получение списка ингредиентов")
    public void setIngredientsList() {
        testOrder = given()
                .spec(getReqSpec())
                .get("ingredients")
                .then()
                .extract()
                .path("data._id");
    }
    @Step("Создание заказа без токена авторизации")
    public void createOrderUnauth() {
        Random random = new Random();
        String randomIngredientFromList = testOrder.get(random.nextInt(testOrder.size()));
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("ingredients", randomIngredientFromList);
        orderResponse = given()
                .spec(getReqSpec())
                .body(dataMap)
                .when()
                .post("orders");
    }
    @Step("Создание заказа без ингридиентов")
    public void createOrderNoIngredients() {
                Map<String, String> dataMap = new HashMap<>();
        dataMap.put("ingredients", null);
        orderResponse = given()
                .spec(getReqSpec())
                .body(dataMap)
                .when()
                .post("orders");
    }
    @Step("Создание заказа c неверным хэшэм ингредиентов")
    public void createOrderWithInvalidHash() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("ingredients", "null");
        orderResponse = given()
                .spec(getReqSpec())
                .body(dataMap)
                .when()
                .post("orders");
    }
    @Step("Создание заказа без токена авторизации")
    public void createOrderAuth(String accessToken) {
        Random random = new Random();
        String randomIngredientFromList = testOrder.get(random.nextInt(testOrder.size()));
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("ingredients", randomIngredientFromList);
        orderResponse = given()
                .spec(getReqSpec())
                .header("Authorization",accessToken)
                .body(dataMap)
                .when()
                .post("orders");
    }
    @Step("Получение заказов пользователя")
    public void getUserOrders(String accessToken){
        orderResponse = given()
                .spec(getReqSpec())
                .header("Authorization",accessToken)
                .when()
                .get(Order);
    }

}
