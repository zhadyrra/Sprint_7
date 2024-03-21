import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiLoginCourierTest extends BaseApiTest {

    @Step("Send post request to /api/v1/courier/login")
    public Response makeRequest(CreateCourierSerialized json) {
        Response response = given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("check status of /api/v1/courier")
    public void checkCreatedStatus(Response response) {
        response.then().statusCode(200);
    }

    @Test
    public void loginCourierTest() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "saske1",
                "12345"
        );
        Response response = makeRequest(json);
        checkCreatedStatus(response);
    }

    @Test
    public void loginCourierTestWithoutLogin() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "",
                "4444"
        );

        Response response = makeRequest(json);

        response
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

        Response response = makeRequest(json);
        response
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void loginCourierTestWithWrongLogin() {
        Faker faker = new Faker();

        CreateCourierSerialized json = new CreateCourierSerialized(
                faker.name().username(),
                faker.internet().password()
        );

        Response response = makeRequest(json);
        response
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test

    public void loginCourierTestWithWrongPassword() {
        Faker faker = new Faker();

        CreateCourierSerialized json = new CreateCourierSerialized(
                faker.name().username(),
                faker.internet().password()
        );
        Response response = makeRequest(json);
        response
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test

    public void loginCourierTestWithoutData() {
        CreateCourierSerialized json = new CreateCourierSerialized(
                "ninja",
                ""
        );
        Response response = makeRequest(json);
        response
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test

    public void loginWithNonExistCourierTest() {
        Faker faker = new Faker();

        CreateCourierSerialized json = new CreateCourierSerialized(
                faker.name().username(),
                faker.internet().password()
        );

        Response response = makeRequest(json);
        response
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

        Response response = makeRequest(json);
        response.then()
                .assertThat().body(containsString("id"))
                .and()
                .statusCode(200);
    }
}
