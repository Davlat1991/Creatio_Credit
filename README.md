📘 Creatio Credit — UI Automation Framework (Enterprise Edition)
<p align="center"> <img src="https://img.shields.io/badge/Java-17-red?logo=java&logoColor=white" /> <img src="https://img.shields.io/badge/Selenide-7.x-brightgreen?logo=selenide&logoColor=white" /> <img src="https://img.shields.io/badge/TestNG-orange?logo=testng&logoColor=white" /> <img src="https://img.shields.io/badge/Allure-purple?logo=allure&logoColor=white" /> <img src="https://img.shields.io/badge/Maven-blue?logo=apachemaven&logoColor=white" /> </p> <p align="center"> <img src="https://img.shields.io/github/last-commit/Davlat1991/Creatio_Credit?color=blue" /> <img src="https://img.shields.io/github/repo-size/Davlat1991/Creatio_Credit?color=lightgrey" /> <img src="https://img.shields.io/badge/Status-Active-success" /> </p>
💳 Creatio Credit UI Automation Framework

Надёжный фреймворк для автоматизации UI-тестирования Creatio Credit:
кредитные заявки, карточки клиента, консультации, стандартный и упрощённый маршруты.

# 🎯 Основные цели:

 - стабильные nightly / CI / regression запуски

 - сокращение smoke- и full-regression времени

 - единый корпоративный стандарт автоматизации

 - модульная архитектура PageObject + Components

 - информативные Allure отчёты для команды и руководства


# 🧱 1. Архитектура проекта

```bash

src/
├── main/java/core/
│    ├── base/
│    ├── config/
│    ├── data/
│    ├── pages/
│    │     ├── login
│    │     ├── ui
│    │     ├── workspace
│    │     ├── credit
│    │     └── ...
│    └── common/components/
│           ├── FieldComponent
│           ├── LookupComponent
│           ├── GridComponent
│           └── ButtonsComponent
└── test/java/core/
├── tests/
│     ├── smoke
│     ├── regression
│     ├── negative
│     ├── boundary
│     ├── simple_route
│     └── standard_route
├── steps/
├── listeners/
└── utils/

```

# 📂 Основные модули
✔ base

 - BaseTest — инициализация окружения, драйвера, Allure

 - BasePage — общие методы страниц

✔ components

 - UI-компоненты Creatio:

 - FieldComponent

 - LookupComponent

 - DetailComponent

 - GridComponent

 - ButtonsComponent

✔ config

 - DriverFactory

 - ConfigProperties

 - Environment (users, URL, DB)

✔ data

Тестовые модели:

 - users

 - contacts

 - products

 - DbConnectionData

✔ pages

PageObject-архитектура для всех модулей Creatio.

# 🌍 2. Multi-Environment Configuration

Файлы окружений:

```matlab
src/test/resources/env/
├── environment.local.properties
├── environment.qa.properties
└── environment.dev.properties
```

▶ Выбор окружения

LOCAL (default)

```bash
mvn clean test
```
QA
```bash
mvn clean test -Denv=qa
```
DEV
```bash
mvn clean test -Denv=dev
```
Автоматически подставляются:

✔ base.url

✔ browser

✔ credentials

✔ timeouts

✔ remote / selenoid settings

# ⚙ 3. DriverFactory

Функционал:

 - стабильные таймауты для динамичного Creatio DOM

 - стратегия загрузки normal

 - headless / headed

 - поддержка Selenoid / Selenium Grid

 - отключение лишних Chrome-логов

DriverFactory вызывается в BaseTest @BeforeSuite.

# 🧬 4. Пример PageObject (короткий)

```java
@Step("Открыть страницу логина")
public LoginPage openLoginPage() {
    open(BASE_URL);
    return this;
}

@Step("Авторизация пользователем {user.login}")
public LoginPage loginAs(LoginData user) {
    enterUsername(user.getLogin());
    enterPassword(user.getPassword());
    loginButton.click();
    return this;
}
```
# 🧪 5. Пример теста

```java
@Test(description = "Smoke: создание кредитной заявки")
public void createCreditApplicationTest() {

    loginPage.openLoginPage()
             .loginAs(Environment.USER_DAVLAT);

    workspaceSteps.openWorkspaceAndSection("Розничный менеджер", "Заявки");

    new SimpleRoutePage()
            .waitOpened()
            .fillRequiredFields("Тест Тестер", "9000000000")
            .save()
            .verifyStatus("Создано");
}
```
# 📊 6. Allure Reporting

Фреймворк поддерживает:

 - Timeline

 - Categories

 - History

 - Environment

 - Attachments (скриншоты, логи, HTML)

 - Автоматические шаги через @Step

▶ Генерация отчёта:
```bash
allure serve target/allure-results
```

# 🚦 7. Команды запуска
Все тесты:
```bash
mvn clean test
```
Smoke:
```bash
mvn test -Dgroups=smoke
```
Negative:
```bash
mvn test -Dgroups=negative
```
Boundary:
```bash
mvn test -Dgroups=boundary
```
Параллельно:
```bash
mvn test -Dthreads=5
```

# 📄 8. Как добавить новый тест

1. Создать класс в src/test/java/core/tests/...

2. Наследовать BaseTest

3. Использовать PageObjects

4. Добавить Allure-метки:

 - @Epic

 - @Feature

 - @Story

 - @Severity

 - @Owner

5. Добавить группу TestNG

6. Запустить локально

# 🧱 9. Как добавить новую страницу (PageObject)

1. Создать файл в core/pages/...

2. Добавить локаторы через Selenide

3. Реализовать действия

4. Реализовать проверки и ожидания

5. Вынести общие функции в компоненты

6. Описать шаги Allure

# 📚 10. Документация по маршрутам

   ✔ Упрощённый маршрут (Спринт 3)

Подробная документация, негативные кейсы, boundary, PageObjects:

```
👉 docs/simple-route.md
```
# 🚀 11. Стандартный маршрут (Краткое описание)

```
Фреймворком покрыт полный цикл:
```
 - Создание заявки

 - Подбор продукта

 - Этап «Оформление»

 - Предварительная проверка

 - Обеспечение → Сбор документов

 - Рассмотрение (КК4)

 - Информирование клиента

 - Подписание

 - Выдача кредита

 - Подтверждение ордеров

 - Полный документ размещён здесь:

```
👉 docs/standard-route.md
```
```
🤝 Автор

Davlat — QA Automation Engineer
📧 d.khakimov@eskhata.com

Проект: Creatio Credit UI Automation
Команда: Platform Creatio
```