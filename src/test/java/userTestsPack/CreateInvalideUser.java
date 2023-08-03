package userTestsPack;

import base.UserStepsApi;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.User;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;

@RunWith(Parameterized.class)
public class CreateInvalideUser {
    private UserStepsApi userStepsApi;
    private User user;

    public CreateInvalideUser(User user){
        this.user = user;
    }
    @Parameterized.Parameters
    public static Object[][]getData(){
        return new Object[][]{
                {new User(null,"123456","Invalid")},
                {new User(RandomStringUtils.random(5)+"@mail.ru",null,"Invalid")},
                {new User(RandomStringUtils.random(5)+"@mail.ru","123456",null)}
        };
    }

    @Before
    public void setUp(){
        userStepsApi =new UserStepsApi();
    }

    @Test
    @DisplayName("Создание пользователя без одного из парамметров")
    public void checkCreateCourierWithoutOneParameter(){
        userStepsApi.userRegister(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message",equalTo("Email, password and name are required fields"));
    }
}
