package core.api.files;

import core.api.session.BrowserSessionHelper;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

public class FileUploadApiHelper {

    private static final String BASE_URL = "http://10.10.202.254";

    @Step("API: загрузить '{fileName}' → parentColumnValue={parentColumnValue}")
    public static void uploadFile(String fileName, String parentColumnValue) {

        File file     = new File("src/test/resources/files/" + fileName);
        long fileSize = file.length();
        String cookie  = BrowserSessionHelper.getCookieString();
        String bpmcsrf = BrowserSessionHelper.getBpmcsrf();
        String mime    = resolveMime(fileName);

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Bpmcsrf",             bpmcsrf)
                .header("Cookie",              cookie)
                .header("X-Requested-With",    "XMLHttpRequest")
                .header("Content-Type",        mime)
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .header("Content-Range",       "bytes 0-" + (fileSize-1) + "/" + fileSize)
                // ✅ fileapi — просто число (без UUID)
                .queryParam("fileapi" + System.currentTimeMillis(), "")
                // ✅ fileId — вот недостающий параметр!
                .queryParam("fileId",            java.util.UUID.randomUUID().toString())
                .queryParam("totalFileLength",   String.valueOf(fileSize))
                .queryParam("mimeType",          mime)
                .queryParam("columnName",        "Data")
                .queryParam("fileName",          fileName)
                .queryParam("parentColumnName",  "Document")
                .queryParam("parentColumnValue", parentColumnValue)
                .queryParam("entitySchemaName",  "DocumentFile")
                .body(file)
                .when()
                .post("/0/rest/TsiFileApiService/Upload");

        // Логируем ответ для диагностики
        int status = response.statusCode();
        if (status != 200) {
            throw new AssertionError(
                    "Upload failed [" + status + "] file=" + fileName +
                            " parent=" + parentColumnValue +
                            " body=" + response.body().asString()
            );
        }
    }

    private static String resolveMime(String fileName) {
        if (fileName.endsWith(".pdf"))  return "application/pdf";
        if (fileName.endsWith(".xlsx")) return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (fileName.endsWith(".jpg"))  return "image/jpeg";
        if (fileName.endsWith(".png"))  return "image/png";
        return "application/octet-stream";
    }
}