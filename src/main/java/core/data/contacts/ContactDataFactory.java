package core.data.contacts;


import java.time.LocalDate;

public class ContactDataFactory {

    // -----------------------------
    // 👤 Базовый тестовый клиент
    // Нет просроченных дней! Нет заполженности!
    // Сегмент клиента в банке: Розница
    // -----------------------------

    public static ContactData defaultContact() {
        return new ContactDataBuilder()
                .withFirstName("Шукрона ")
                .withLastName("Ашурова")
                .withMiddleName("Махсудовна")
                .build();
    }


    // -----------------------------
    // Работает в организации
    // Нет просроченных дней!
    // У клиента есть 3 дня просрочки
    // Сегмент клиента в банке: Розница
    // -----------------------------
    public static ContactData employee() {
        return new ContactDataBuilder()
                .withFirstName("Мукаддас")
                .withLastName("Ганиева")
                .withMiddleName("Абдумаликовна")
                .build();
    }

    // -----------------------------
    // 🌍 Самозанятый
    // Нет просроченных дней!
    // У клиента есть 3 дня просрочки
    // Сегмент клиента в банке: Розница
    // -----------------------------
    public static ContactData selfemployment() {
        return new ContactDataBuilder()
                .withFirstName("Давлатахмад")
                .withLastName("Одинаев")
                .withMiddleName("Сангахмадович")
                .build();
    }



    // -----------------------------
    // 🧑‍🎓 Имеет депозит!
    // Сумма: 3010,80 TJS До: 16.02.2027
    // Нет просроченных дней! Нет заполженности!
    // Сегмент клиента в банке: Розница
    // -----------------------------
    public static ContactData selfdeposit1() {
        return new ContactDataBuilder()
                .withFirstName("Каримчон")
                .withLastName("Шарипов")
                .withMiddleName("Бахтиерчонович")
                .build();
    }

    // -----------------------------
    // 🧑‍🎓 Имеет депозит!
    // Сумма: 700 TJS До: 22.09.2025
    // Нет просроченных дней! Нет заполженности!
    // Сегмент клиента в банке: Розница
    // -----------------------------
    public static ContactData selfdeposit2() {
        return new ContactDataBuilder()
                .withFirstName("Фарангис")
                .withLastName("Ашурова")
                .withMiddleName("Шарифовна")
                .build();
    }



    // -----------------------------
    // 🧓 Имеет другок источник дохода
    // -----------------------------
    public static ContactData otherIncome() {
        return new ContactDataBuilder()
                .withFirstName("Фарангис")
                .withLastName("Ашурова")
                .withMiddleName("Шарифовна")
                .build();
    }




}

