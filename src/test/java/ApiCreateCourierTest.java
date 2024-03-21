import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.junit.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class ApiCreateCourierTest extends BaseApiTest {

    Faker faker = new Faker();

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
        CreateCourierSerialized json = new CreateCourierSerialized(
                "",
                faker.internet().password(),
                faker.name().firstName()
        );

        Response response = makeRequest(json);
        checkCreatedStatus(response);
    }

    @Test
    public void createExistCourierTest() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );

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
                faker.internet().password(),
                faker.name().firstName()
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
                faker.name().username(),
                "",
                faker.name().firstName()
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
        CreateCourierSerialized json = new CreateCourierSerialized(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );
        Response response = makeRequest(json);
        checkCreatedStatus(response);
    }

    @Test
    public void createCourierSuccessTestWMessage() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );
        Response response = makeRequest(json);
        response.then()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    public void createCourierTestWithoutDataRespondsErrorText() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "",
                faker.internet().password(),
                faker.name().firstName()
        );

        Response response = makeRequest(json);
        response.then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierTestWithExistLogin() {
        String login = faker.name().username();

        CreateCourierSerialized json = new CreateCourierSerialized(
                login,
                faker.internet().password(),
                faker.name().firstName()
        );

        Response response = makeRequest(json);
        checkCreatedStatus(response);

        CreateCourierSerialized jsonSecond = new CreateCourierSerialized(
                login,
                faker.internet().password(),
                faker.name().firstName()
        );

        Response responseSecond = makeRequest(jsonSecond);
        responseSecond.then()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
}