package flows.credit.documents;

import com.codeborne.selenide.Selenide;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// DocumentUploadMap.java — маппинг через JS по заголовку + порядку внутри детали
public class DocumentUploadMap {

    private final Map<String, String> map = new LinkedHashMap<>();

    public DocumentUploadMap build(int offset, String... docTypes) {
        List<String> uuids = getAllUuids();

        if (uuids.size() < offset + docTypes.length) {
            throw new RuntimeException(
                    "❌ Ожидалось минимум " + (offset + docTypes.length) +
                            " input-ов, найдено: " + uuids.size()
            );
        }

        for (int i = 0; i < docTypes.length; i++) {
            map.put(docTypes[i], uuids.get(offset + i));
        }

        return this;
    }

    public DocumentUploadMap build(int offset) {
        List<String> uuids = getAllUuids();

        if (uuids.size() < offset + 3) {
            throw new RuntimeException(
                    "❌ Ожидалось минимум " + (offset + 3) + " input-ов, найдено: " + uuids.size()
            );
        }

        map.put("Income",   uuids.get(offset));
        map.put("Passport", uuids.get(offset + 1));
        map.put("INN",      uuids.get(offset + 2));

        return this;
    }

    public String get(String docType) {
        String value = map.get(docType);
        if (value == null) {
            throw new RuntimeException("❌ parentColumnValue не найден для: " + docType);
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private List<String> getAllUuids() {
        List<String> result = (List<String>) Selenide.executeJavaScript(
                "var inputs = document.querySelectorAll(\"input[data-item-marker='AddRecordButton']\");" +
                        "var uuids = [];" +
                        "for (var i = 0; i < inputs.length; i++) {" +
                        "  var m = inputs[i].id.match(/V2_([a-f0-9\\-]{36})Tsi/);" +
                        "  if (m) uuids.push(m[1]);" +
                        "}" +
                        "return uuids;"
        );
        return result != null ? result : new ArrayList<>();
    }
}