package at.htl.boundry;

import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class StoreServiceTest {

    @Test
    void addStore() {
        JsonObject jsonObject = JsonbBuilder.create().fromJson("{\n" +
                "  \"name\": \"Mcdonalds\",\n" +
                "  \"rent\": \"1200\",\n" +
                "  \"storekeeper\": \"Ronald Mcdonald\",\n" +
                "  \"category\": \"gastronomy\"\n" +
                "}", JsonObject.class);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/stores/addStore/")
                .then()
                .statusCode(200);
    }


    @Test
    void findStore() {
        given()
                .param("name","Mediamarkt")
                .when()
                .get("/stores/findStoreName/")
                .then()
                .statusCode(200);
    }

    @Test
    void findStoreId() {
        given()
                .param("id","2")
                .when()
                .get("/stores/")
                .then()
                .statusCode(200);
    }
}
