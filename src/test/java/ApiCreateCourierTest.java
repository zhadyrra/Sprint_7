import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.junit.Test;

import java.time.LocalDateTime;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class ApiCreateCourierTest extends BaseApiTest {

    @Step("create json data")
    public CreateCourierSerialized createData() {
        Faker faker = new Faker();

        CreateCourierSerialized json = new CreateCourierSerialized(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );

        return json;
    }
    @Step("Send GET request to /api/v1/courier")
    public Response makeRequest(CreateCourierSerialized json) {
        Response response = given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");

        return response;
    }

    @Step("check status of /api/v1/courier")
    public void checkCreatedStatus(Response response) {
        response.then().statusCode(201);
    }

    @Test
    public void createCourierSuccessTest() {
        CreateCourierSerialized json = createData();
        Response response = makeRequest(json);
        checkCreatedStatus(response);
    }
    @Test
    public void createExistCourierTest() {
        CreateCourierSerialized json = createData();
        Response response = makeRequest(json);
        checkCreatedStatus(response);

        Response responseSecond = makeRequest(json);
        responseSecond.then()
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

        Response response = makeRequest(json);
        response.then()
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

        Response response = makeRequest(json);
        response.then()
                .assertThat().body("code", equalTo(400))
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
    @Test
    public void createCourierSuccessTestWCode() {
        CreateCourierSerialized json = createData();
        Response response = makeRequest(json);
        checkCreatedStatus(response);
    }
    @Test
    public void createCourierSuccessTestWMessage() {
        LocalDateTime localDateTime = LocalDateTime.now();
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske191"+localDateTime,
                "12345",
                "hitachi"
        );
        Response response = makeRequest(json);
        response.then()
                .assertThat().body("ok", equalTo(true));
    }
    @Test
    public void createCourierTestWithoutDataRespondsErrorText() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "",
                "8778",
                "hitachi"
        );

        Response response = makeRequest(json);
        response.then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    public void createCourierTestWithExistLogin() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske1",
                "123u45",
                "hijtachi"
        );
        Response response = makeRequest(json);
        response.then()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
}