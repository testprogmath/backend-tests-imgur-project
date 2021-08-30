package ru.geekbrains.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static ru.geekbrains.base.Endpoints.UPLOAD_IMAGE;
import static ru.geekbrains.base.Images.IMAGE_URL;

public class ImageTests extends BaseTest {

    static final String IMAGE_FILE = "src/test/resources/img.png";
    String imageDeleteHash;
    RequestSpecification multiPartReqSpec;
    String base64Image;
    RequestSpecification imageRequestSpecification;

    @BeforeEach
    void setUp() throws IOException {
        byte[] imageBytesArray = FileUtils.readFileToByteArray(new File(IMAGE_FILE));
        base64Image = Base64.getEncoder().encodeToString(imageBytesArray);
    }

    @Test
    void uploadImageFileTest() {
        imageDeleteHash=  given()
                .spec(requestSpecification)
//                .headers("Authorization", token)
                .multiPart("image", new File(IMAGE_FILE))
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadFileWithLinkTest() {
        imageDeleteHash=  given()
                .spec(requestSpecification)
                .multiPart("image", IMAGE_URL.getPath())
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadBase64FileTest() {
        imageDeleteHash=  given()
                .headers("Authorization", token)
                .multiPart("image", base64Image)
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @AfterEach
    void tearDown() {
        given()
                .header("Authorization", token)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .then()
                .statusCode(200);
    }
}
