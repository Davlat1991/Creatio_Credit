package core.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class DocumentUploadApi {

    static {
        RestAssured.useRelaxedHTTPSValidation();
    }

    public static void uploadFile(
            String baseUrl,
            String cookie,
            File file,
            String parentColumnValue
    ) {

        Response response =
                given()
                        .baseUri(baseUrl)
                        .header("Cookie", cookie)
                        .multiPart("file", file)
                        .queryParam("fileId", System.currentTimeMillis())
                        .queryParam("totalFileLength", file.length())
                        .queryParam("fileName", file.getName())
                        .queryParam("mimeType", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                        .queryParam("columnName", "DocumentFile")
                        .queryParam("parentColumnName", "Document.parentColumnValue")
                        .queryParam("parentColumnValue", parentColumnValue)
                        .queryParam("entitySchemaName", "DocumentFile")
                        .post("/0/rest/TsiFileApiService/UploadFile");

        if (response.statusCode() != 200) {
            throw new RuntimeException("Ошибка загрузки файла: " + response.asString());
        }
    }
}
