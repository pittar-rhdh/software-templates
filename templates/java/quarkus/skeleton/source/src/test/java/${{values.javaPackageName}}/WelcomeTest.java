package ${{values.groupId}}.${{values.artifactId}};

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class WelcomeTest {

    @Test
    public void testWelcomeGet() {
        performTest("/hello");
    }

    private void performTest(String path) {
        //List all, should have all 3 fruits the database has initially:
        given()
                .when().get(path)
                .then()
                .statusCode(200)
                .body(
                        containsString("welcome"));
    }

}
