import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class GetOrdersTest extends BaseApiTest{
    @Test
    public void getMyInfoStatusCode() {
        // метод given() помогает сформировать запрос
        given()
               .get("/api/v1/orders")
                .then().statusCode(200)
                .and()
                .assertThat().body(containsString("orders"));
    }

}
