import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;

public class BaseApiTest {
    @Before
    @Step("Opening the web site")
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
}
