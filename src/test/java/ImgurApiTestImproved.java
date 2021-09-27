import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiTestImproved {
    private static int commentId;
    private static String vote = "up";
    private static String albumHash = "U2VfA9S";
    private static final String INPUT_IMG_FILE_PATH = "src/test/resources/2Qnp7Og.jpeg";
    private static  String imageDeleteHash;

    ResponseSpecification responseSpecification = null;

    RequestSpecification requestSpecificationToken = null;

    RequestSpecification requestSpecificationClientID = null;

    MultiPartSpecification multiPartSpecification = null;

    @BeforeEach
    void beforeTestResponse() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
//                .expectResponseTime(Matchers.lessThan(5000L))
//                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();
    }


    @BeforeEach
    void beforeTestRequestClientID() {
        requestSpecificationClientID = new RequestSpecBuilder()
                .addHeader("Authorization", "Client-ID 245e10b28ddc002")
                .build();
    }

    @BeforeEach
    void beforeTestRequestToken () {
      requestSpecificationToken = new RequestSpecBuilder()
              .setAuth(oauth2("76ee37117ac4d779519a9a287ef92c40e543334e"))
              .build();
    }

    @BeforeEach
    void beforeTest() throws IOException {
        String file = INPUT_IMG_FILE_PATH;
        byte [] fileContent = FileUtils.readFileToByteArray(new File(file));
        multiPartSpecification = new MultiPartSpecBuilder(fileContent)
                .controlName("image")
                .build();
    }


    @Test
    @DisplayName("Получение информации об аккаунте")
    @Order(1)
    void testGetAccountBase() {
        given()
                .spec(requestSpecificationClientID)
                .when()
                .get("https://api.imgur.com/3/account/levinmk23")
                .then()
                .spec(responseSpecification)
                .body("data.id", is(153514053))
                .body("data.url", is("levinmk23"))
                .log()
                .all();
    }

    @Test
    @DisplayName("Создание комментария")
    @Order(2)
    void testPostComment() {

        commentId = given()
                .spec(requestSpecificationToken)
                .formParam("image_id", "U2VfA9S")
                .formParam("comment", "I'm a giraffe!")
                .when()
                .post("https://api.imgur.com/3/comment")
                .then()
                .spec(responseSpecification)
                .body("data.id", is(Matchers.notNullValue()))
                .extract()
                .response()
                .jsonPath()
                .getInt("data.id");
    }

    @Test
    @Order(3)
    @DisplayName("Получение комментария")
    void testGetComment() {
        given()
                .spec(requestSpecificationClientID)
                .when()
                .get("https://api.imgur.com/3/comment/" + commentId)
                .then()
                .spec(responseSpecification)
                .body("data.id", is(commentId))
                .body("data.image_id", is("U2VfA9S"))
                .body("data.comment", is("I'm a giraffe!"))
                .log()
                .all();
    }

    @Test
    @Order(4)
    @DisplayName("Создание ответа на комментарий")
    void testCreateReply() {
        given()
                .spec(requestSpecificationToken)
                .formParam("image_id", "U2VfA9S")
                .formParam("comment", "Hey there!")
                .when()
                .post("https://api.imgur.com/3/comment/" + commentId)
                .then()
                .spec(responseSpecification)
                .body("data.id", Matchers.notNullValue())
                .log()
                .all();

    }

    @Test
    @Order(5)
    @DisplayName("Получение ответов к коментарию")
    void testGetReplies() {
        given()
                .spec(requestSpecificationClientID)
                .when()
                .get("https://api.imgur.com/3/comment/" + commentId + "/replies")
                .then()
                .body("data.children.comment", contains("Hey there!"))
                .log()
                .all();
    }

    @Test
    @Order(6)
    @DisplayName("Голосование")
    void testVote() {
        given()
                .spec(requestSpecificationToken)
                .when()
                .get("https://api.imgur.com/3/comment/" + commentId + "/replies")
                .then()
                .spec(responseSpecification)
                .log()
                .all();
    }

    @Test
    @Order(7)
    @DisplayName("Удаление комментария")
    void testDeleteComment() {
        given()
                .spec(requestSpecificationToken)
                .when()
                .delete("https://api.imgur.com/3/comment/" + commentId)
                .then()
                .spec(responseSpecification)
                .log()
                .all();
    }

    @Test
    @Order(8)
    @DisplayName("Комментарий не существует")
    void testCommentNotExist() {
        String actually = given()
                .spec(requestSpecificationClientID)
                .when()
                .get("https://api.imgur.com/3/comment/1")
                .then()
                .extract()
                .response()
                .prettyPrint();
        Assertions.assertTrue(actually.contains("<title>imgur: the simple 404 page</title>"));
    }

    @Test
    @Order(9)
    @DisplayName("Получить альбом")
    void getAlbum () {
        given()
                .spec(requestSpecificationClientID)
                .when()
                .get("https://api.imgur.com/3/album/" + albumHash)
                .then()
                .spec(responseSpecification)
                .log()
                .all();
    }

    @Test
    @Order(10)
    @DisplayName("Получить картинки альбома")
    void getAlbumImages () {
        given()
                .spec(requestSpecificationClientID)
                .when()
                .get("https://api.imgur.com/3/album/" + albumHash + "/images")
                .then()
                .spec(responseSpecification)
                .body("data.id", Matchers.notNullValue())
                .log()
                .all();
    }

    @Test
    @Order(11)
    @DisplayName("Загрузка картинки")
    void imageUpload () {

        String imageDeleteHash  = given()
                .spec(requestSpecificationClientID)
                .multiPart(multiPartSpecification)
                .when()
                .post("https://api.imgur.com/3/upload")
                .then()
                .body("data.deletehash", Matchers.notNullValue())
                .log()
                .all()
                .extract()
                .response()
                .jsonPath()
                .get("data.deletehash");
        Assertions.assertNotNull(imageDeleteHash);

        System.out.println(imageDeleteHash);
    }
}
