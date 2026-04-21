package flows.credit.documents;

// DocumentType.java — enum с названиями деталей как в UI
public enum DocumentType {
    INCOME  ("Финансовое досье", "Income.pdf"),
    PASSPORT("Досье клиента",    "Passport.pdf"),
    INN     ("Досье клиента",    "INN.pdf");

    public final String detailCaption;
    public final String fileName;

    DocumentType(String detailCaption, String fileName) {
        this.detailCaption = detailCaption;
        this.fileName = fileName;
    }
}
