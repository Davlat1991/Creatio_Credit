package core.data.contacts;


import java.time.LocalDate;

public class ContactDataFactory {

    // -----------------------------
    // 👤 Базовый тестовый клиент
    // -----------------------------
    public static ContactData defaultContact() {
        return new ContactData(
                "TestFirst",
                "TestMiddle",
                "TestLast",
                LocalDate.of(1990, 1, 1),
                "AA1234567",
                "Internal Affairs",
                LocalDate.of(2010, 5, 10),
                "+998901112233",
                "test@example.com",
                "Khujand, Tajikistan"
        );
    }

    // -----------------------------
    // tj Таджикский резидент (частый кейс в банке)
    // -----------------------------
    public static ContactData tajikResident() {
        return new ContactData(
                "Ali",
                "Valiyevich",
                "Valiyev",
                LocalDate.of(1995, 3, 20),
                "AA9876543",
                "Khujand IIB",
                LocalDate.of(2015, 1, 1),
                "+992900001122",
                "ali.valiyev@example.com",
                "Khujand city"
        );
    }

    // -----------------------------
    // 🌍 Нерезидент (часто нужен в проверках валидаций)
    // -----------------------------
    public static ContactData foreignCitizen() {
        return new ContactData(
                "John",
                "Michael",
                "Smith",
                LocalDate.of(1988, 7, 14),
                "P1234567",
                "USA Embassy",
                LocalDate.of(2018, 4, 20),
                "+12025550111",
                "john.smith@mail.com",
                "New York, USA"
        );
    }

    // -----------------------------
    // 🧓 Пенсионер (кредитные условия)
    // -----------------------------
    public static ContactData retired() {
        return new ContactData(
                "Said",
                "Karimovich",
                "Karimov",
                LocalDate.of(1955, 2, 15),
                "AA7654321",
                "Istaravshan IIB",
                LocalDate.of(2000, 3, 15),
                "+992909998877",
                "said.karimov@example.com",
                "Istaravshan, Tajikistan"
        );
    }

    // -----------------------------
    // 🧑‍🎓 Студент (частые кейсы: инн, справки, льготы)
    // -----------------------------
    public static ContactData student() {
        return new ContactData(
                "Jasur",
                "Rustamovich",
                "Ruziev",
                LocalDate.of(2004, 11, 11),
                "AA1239087",
                "Tashkent IIB",
                LocalDate.of(2022, 1, 10),
                "+992911234567",
                "jasur@example.com",
                "Dushanbe, Tajikistan"
        );
    }

    // -----------------------------
    // 💼 Индивидуальный предприниматель
    // -----------------------------
    public static ContactData entrepreneur() {
        return new ContactData(
                "Bekzod",
                "Mirzayevich",
                "Mirzayev",
                LocalDate.of(1985, 9, 12),
                "AA4455667",
                "Dushanbe IIB",
                LocalDate.of(2010, 7, 21),
                "+992933001122",
                "bekzod.biz@example.com",
                "Dushanbe, Tajikistan"
        );
    }
}

