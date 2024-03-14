import org.junit.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiCreateCourierTest extends BaseApiTest {
    @Test
    public void createCourierSuccessTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske91"+localDateTime,
                "12345",
                "hitachi"
        );

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier") // отправь запрос на ручку
                .then()
                .statusCode(201);
    }
    @Test
    public void createExistCourierTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske91"+ localDateTime,
                "12345",
                "hitachi"
        );
         given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);// отправь запрос на ручку

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier") // отправь запрос на ручку
                .then()
                .assertThat().body("code", equalTo(409))
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
    @Test
    public void createCourierTestWithoutLogin() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "",
                "8778",
                "hitachi"
        );

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier") // отправь запрос на ручку
                .then()
                .assertThat().body("code", equalTo(400))
                .and()
                .statusCode(400);
    }
    @Test
    public void createCourierTestWithoutPassword() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "jjjjj",
                "",
                "hitachi"
        );

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier") // отправь запрос на ручку
                .then()
                .assertThat().body("code", equalTo(400))
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
    @Test
    public void createCourierSuccessTestWCode() {
        LocalDateTime localDateTime = LocalDateTime.now();
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske194"+localDateTime,
                "12345",
                "hitachi"
        );
        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier") // отправь запрос на ручку
                .then()
                .statusCode(201);
    }
    @Test
    public void createCourierSuccessTestWMessage() {
        LocalDateTime localDateTime = LocalDateTime.now();
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske191"+localDateTime,
                "12345",
                "hitachi"
        );
        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier") // отправь запрос на ручку
                .then()
                .assertThat().body("ok", equalTo(true));
    }
    @Test
    public void createCourierTestWithoutDataRespondsErrorText() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "",
                "8778",
                "hitachi"
        );

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier") // отправь запрос на ручку
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    public void createCourierTestWithExistLogin() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske1",
                "123u45",
                "hijtachi"
        );

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier") // отправь запрос на ручку
                .then()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
}