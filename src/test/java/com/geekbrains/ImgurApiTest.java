package com.geekbrains;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiTest {


    private static int commentId;
    private static String vote = "up";
    private static String albumHash = "U2VfA9S";

    @Test
    @DisplayName("Получение информации об аккаунте")
    @Order(1)
    void testGetAccountBase() {
        given()
                .header("Authorization", "Client-ID 245e10b28ddc002")
                .log()
                .all()
                .expect()
                .statusCode(200)
                .body("data.id", is(153514053))
                .body("data.url", is("levinmk23"))
                .body("success", is(true))
                .log()
                .all()
                .when()
                .get("https://api.imgur.com/3/account/levinmk23");
    }

    @Test
    @DisplayName("Создание комментария")
    @Order(2)
    void testPostComment() {

        commentId = given().auth()
                .oauth2("76ee37117ac4d779519a9a287ef92c40e543334e")
                .formParam("image_id", "U2VfA9S")
                .formParam("comment", "I'm a giraffe!")
                .expect()
                .statusCode(200)
                .body("success", is(true))
                .body("data.id", is(Matchers.notNullValue()))
                .log()
                .body()
                .when()
                .post("https://api.imgur.com/3/comment")
                .jsonPath()
                .getInt("data.id");
    }

    @Test
    @Order(3)
    @DisplayName("Получение комментария")
    void testGetComment() {
        given()
                .header("Authorization", "Client-ID 245e10b28ddc002")
                .expect()
                .body("data.id", is(commentId))
                .body("data.image_id", is("U2VfA9S"))
                .body("data.comment", is("I'm a giraffe!"))
                .statusCode(200)
                .log()
                .all()
                .when()
                .get("https://api.imgur.com/3/comment/" + commentId);
    }

    @Test
    @Order(4)
    @DisplayName("Создание ответа на комментарий")
    void testCreateReply() {
        given()
                .auth()
                .oauth2("76ee37117ac4d779519a9a287ef92c40e543334e")
                .formParam("image_id", "U2VfA9S")
                .formParam("comment", "Hey there!")
                .expect()
                .statusCode(200)
                .body("data.id", Matchers.notNullValue())
                .log()
                .all()
                .when()
                .post("https://api.imgur.com/3/comment/" + commentId);

    }

    @Test
    @Order(5)
    @DisplayName("Получение ответов к коментарию")
    void testGetReplies() {
        given()
                .header("Authorization", "Client-ID 245e10b28ddc002")
                .expect()
                .statusCode(200)
                .body("data.children.comment", contains("Hey there!"))
                .log()
                .all()
                .when()
                .get("https://api.imgur.com/3/comment/" + commentId + "/replies");

    }

    @Test
    @Order(6)
    @DisplayName("Голосование")
    void testVote() {
        given()
                .auth()
                .oauth2("76ee37117ac4d779519a9a287ef92c40e543334e")
                .expect()
                .statusCode(200)
                .log()
                .all()
                .when()
                .post("https://api.imgur.com/3/comment/" + commentId + "/vote/"  + vote);
    }

    @Test
    @Order(7)
    @DisplayName("Удаление комментария")
    void testDeleteComment() {
        given()
                .auth()
                .oauth2("76ee37117ac4d779519a9a287ef92c40e543334e")
                .expect()
                .statusCode(200)
                .body("success", is(true))
                .log()
                .all()
                .when()
                .delete("https://api.imgur.com/3/comment/" + commentId);
    }

    @Test
    @Order(8)
    @DisplayName("Комментарий не существует")
    void testCommentNotExist() {
        String actually = given()
                .header("Authorization", "Client-ID 245e10b28ddc002")
                .expect()
                .log()
                .all()
                .when()
                .get("https://api.imgur.com/3/comment/1")
                .body()
                .prettyPrint();
        Assertions.assertTrue(actually.contains("<title>imgur: the simple 404 page</title>"));
    }

    @Test
    @Order(9)
    @DisplayName("Получить альбом")
    void getAlbum () {
        given()
                .header("Authorization", "Client-ID 245e10b28ddc002")
                .expect()
                .statusCode(200)
                .log()
                .all()
                .when()
                .get("https://api.imgur.com/3/album/" + albumHash);
    }

    @Test
    @Order(10)
    @DisplayName("Получить картинки альбома")
    void getAlbumImages () {
        given()
                .header("Authorization", "Client-ID 245e10b28ddc002")
                .expect()
                .statusCode(200)
                .body("data.id", Matchers.notNullValue())
                .log()
                .all()
                .when()
                .get("https://api.imgur.com/3/album/" + albumHash + "/images");
    }
}

