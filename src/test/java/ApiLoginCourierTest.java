import io.qameta.allure.Step;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class ApiLoginCourierTest extends BaseApiTest {

    @Test
    public void loginCourierTest() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske1",
                "12345"
        );

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login") // отправь запрос на ручку
                .then()
                .statusCode(200);
    }
    @Test

    public void loginCourierTestWithoutLogin() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "",
                "4444"
        );

        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login") // отправь запрос на ручку
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
    @Test

    public void loginCourierTestWithoutPassword() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "ninja",
                ""
        );

        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login") // отправь запрос на ручку
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
    @Test
    public void loginCourierTestWithWrongLogin() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "nin-=ja",
                "434"
        );

        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login") // отправь запрос на ручку
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @Test

    public void loginCourierTestWithWrongPassword() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske190",
                "54566"
        );
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login") // отправь запрос на ручку
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
    @Test

    public void loginCourierTestWithoutData() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "ninja",
                ""
        );
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login") // отправь запрос на ручку
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test

    public void loginWithNonExistCourierTest() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "ninja",
                "12345"
        );

        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login") // отправь запрос на ручку
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @Test

    public void loginCourierTestSuccessId() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske1",
                "12345"
        );

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login") // отправь запрос на ручку
                .then()
                .assertThat().body(containsString("id"))
                .and()
                .statusCode(200);
    }
}
