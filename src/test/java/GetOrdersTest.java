import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

public class GetOrdersTest extends BaseApiTest{
    @Test
    public void getMyInfoStatusCode() {

        given()
               .get("/api/v1/orders")
                .then().statusCode(200)
                .and()
                .assertThat().body(containsString("orders"))
                .and()
                .assertThat().body("orders", instanceOf(List.class))
        ;
    }

}
