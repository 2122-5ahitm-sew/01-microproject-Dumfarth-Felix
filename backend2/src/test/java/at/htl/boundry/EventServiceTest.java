package at.htl.boundry;

import at.htl.entity.Event;
import at.htl.entity.Person;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class EventServiceTest {

    @Test
    void addEvent() {
        JsonObject jsonObject = JsonbBuilder.create().fromJson("{\n" +
                "  \"name\": \"Party\",\n" +
                "  \"date\": \"13.11.2020\"\n" +
                "}", JsonObject.class);
        JsonObject jsonResObject = JsonbBuilder.create().fromJson("{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Party\",\n" +
                "  \"date\": 1605222000000,\n" +
                "  \"involvedStores\": null\n" +
                "}", JsonObject.class);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/events/addEvent/")
                .then()
                .statusCode(200)
                .body(is(jsonResObject.toString()));
    }

    @Test
    void delEvent() {
        JsonObject jsonObject= JsonbBuilder.create().fromJson("{\n" +
               "  \"name\": \"Weihnachtssale\"\n" +
               "}\n", JsonObject.class);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .delete("/events/delEvent")
                .then()
                .statusCode(200);
    }

    /*@Test
    void updateDate() {
        JsonObject jsonObject = JsonbBuilder.create().fromJson("{\n" +
                "  \"name\": \"Weihnachtssale\",\n" +
                "  \"newDate\": \"13.11.2020\"\n" +
                "}", JsonObject.class);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .patch("/events/updateDate/")
                .then()
                .statusCode(200)
                .body(is("{\"id\":2,\"name\":\"Weihnachtssale\",\"date\":1605222000000,\"involvedStores\":[]}"));
    }
*/
   /* @Test
    void findEventName() {
        given()
                .param("name","Weihnachtssale")
                .when()
                .get("/events/findEventName/")
                .then()
                .statusCode(200);
    }

    @Test
    void findEventDate() {
        given()
                .param("date","24.12.2020")
                .when()
                .get("/events/findEventDate/")
                .then()
                .statusCode(200);
    }*/
}
