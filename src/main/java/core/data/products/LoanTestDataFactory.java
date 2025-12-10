package core.data.products;

public class LoanTestDataFactory {

    // 🟩 Потребительский кредит
    public static LoanTestData consumerLoan() {
        return new LoanTestData(
                "Потребительский кредит",
                20_000_000,
                24,
                26.0,
                "TJS",
                0,
                "11111111-1111-1111-1111-111111111111"
        );
    }

    // 🟦 Ипотека
    public static LoanTestData mortgageLoan() {
        return new LoanTestData(
                "Ипотечный кредит",
                300_000_000,
                240,
                17.0,
                "TJS",
                30_000_000,
                "22222222-2222-2222-2222-222222222222"
        );
    }

    // 🟧 Автокредит
    public static LoanTestData autoLoan() {
        return new LoanTestData(
                "Автокредит",
                150_000_000,
                60,
                22.0,
                "TJS",
                30_000_000,
                "33333333-3333-3333-3333-333333333333"
        );
    }

    // 🟨 Малый бизнес кредит
    public static LoanTestData businessLoan() {
        return new LoanTestData(
                "Кредит для бизнеса",
                200_000_000,
                36,
                20.0,
                "TJS",
                0,
                "44444444-4444-4444-4444-444444444444"
        );
    }
}

