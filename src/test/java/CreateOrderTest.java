import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTest{

    private final CreateOrderSerial jsonObj;
    private final int statusCode;

    public CreateOrderTest(CreateOrderSerial jsonObj, int statusCode) {
        this.jsonObj = jsonObj;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getJsonData() {
        return new Object[][] {
                {new CreateOrderSerial(new String[]{CreateOrderSerial.BLACK}), 201},
                {new CreateOrderSerial(new String[]{CreateOrderSerial.GREY}), 201},
                {new CreateOrderSerial(new String[]{CreateOrderSerial.GREY, CreateOrderSerial.BLACK}), 201},
                {new CreateOrderSerial(new String[]{}), 201}
        };
    }
    @Test
    public void createOrderTest(){

        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(jsonObj)
                .when()
                .post("/api/v1/orders") // отправь запрос на ручку
                .then()
                .statusCode(statusCode)
                .and()
                .assertThat().body(containsString("track"))
        ;
    }

}
