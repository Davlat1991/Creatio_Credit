📘 Creatio Credit — UI Automation Framework (Enterprise Edition)
<p align="center"> <img src="https://img.shields.io/badge/Java-17-red?logo=java&logoColor=white" /> <img src="https://img.shields.io/badge/Selenide-7.x-brightgreen?logo=selenide&logoColor=white" /> <img src="https://img.shields.io/badge/TestNG-Framework-orange?logo=testng&logoColor=white" /> <img src="https://img.shields.io/badge/Allure-Reports-purple?logo=allure&logoColor=white" /> <img src="https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven&logoColor=white" /> </p> <p align="center"> <img src="https://img.shields.io/github/last-commit/Davlat1991/Creatio_Credit?color=blue" /> <img src="https://img.shields.io/github/repo-size/Davlat1991/Creatio_Credit?color=lightgrey" /> <img src="https://img.shields.io/badge/Status-Active-success" /> </p>
💳 Creatio Credit UI Automation

Фреймворк разработан для автоматизации UI-тестирования модулей Creatio Credit, включая кредитные заявки, карточки клиента, консультации, стандартный и упрощённый маршруты.

Основные цели:

обеспечение стабильных nightly/CI запусков

ускорение smoke и regression тестирования

покрытие ключевых кредитных процессов

повышение качества релизов платформенной команды

создание единого архитектурного стандарта автоматизации

🧱 1. Архитектура фреймворка
src/main/java/core/

📂 Основные модули
✔ base

BaseTest — настройка окружения, Allure, WebDriver

BasePage — общий функционал страниц

common/components — FieldComponent, LookupComponent, GridComponent

универсальные действия Creatio DOM

✔ config

DriverFactory — конфигурация браузера

ConfigProperties — загрузка env-настроек

Environment — пользователи, URL

✔ data

Модели и тестовые сущности:

users

contacts

products

DbConnectionData

✔ pages

PageObject для всех модулей:

login

ui (общие UI-компоненты)

workspace

credit (маршруты Creatio)

🧪 2. Тестовая архитектура
src/test/java/core/

✔ tests

smoke

regression

negative

boundary

упрощённый маршрут

стандартный маршрут

✔ steps

Опциональный слой бизнес-степов для Allure.

✔ listeners

AllureTestListener (attachments)

WebDriver handlers

✔ utils

Вспомогательные утилиты.

🌍 3. Multi-Environment Configuration
src/test/resources/env/
├── environment.local.properties
├── environment.qa.properties
└── environment.dev.properties

▶ Выбор окружения:

LOCAL

mvn clean test


QA

mvn clean test -Denv=qa


DEV

mvn clean test -Denv=dev


Автоматически подставляет:

URL Creatio

browser

таймауты

авторизацию

параметры remote-запуска

⚙ 4. DriverFactory

Функционал:

настройка таймаутов Creatio (DOM очень динамичный)

стратегия загрузки страницы normal

headless / non-headless режим

remote Selenoid/Grid

отключение шумных логов Chrome

DriverFactory вызывается в BaseTest @BeforeSuite.

🧬 5. Пример PageObject (коротко)
@Step("Открыть страницу логина")
public LoginPage openLoginPage() {
open(BASE_URL);
return this;
}

@Step("Авторизация пользователем {login}")
public LoginPage loginAs(LoginData user) {
enterUsername(user.getLogin());
enterPassword(user.getPassword());
clickLoginButton();
return this;
}

🧪 6. Пример теста
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

📊 7. Allure Reporting (Standard)

Фреймворк поддерживает:

Timeline

History

Categories

Environment

Attachments

AllureSelenide

▶ Генерация отчёта:
allure serve target/allure-results

🚦 8. Команды запуска
Все тесты:
mvn clean test

Smoke:
mvn test -Dgroups=smoke

Negative:
mvn test -Dgroups=negative

Boundary:
mvn test -Dgroups=boundary

Параллельно:
mvn test -Dthreads=5

📄 9. Как добавить новый тест

Создать класс в src/test/java/core/tests/...

Наследовать BaseTest

Использовать PageObject

Добавить аннотации:

@Epic

@Feature

@Story

@Severity

@Owner

Добавить тест в нужную TestNG-группу

Запустить локально

🧱 10. Как добавить новую страницу (PageObject)

Создать файл в /core/pages/...

Добавить локаторы через Selenide

Реализовать методы действий

Реализовать проверки и ожидания

Вынести общие элементы в компоненты

Добавить шаги Allure

🧭 11. Документация по Упрощённому маршруту (Спринт 3)

Полный документ вынесен в:

docs/simple-route.md


Содержит:

Smoke сценарий

Negative сценарии

Boundary сценарии

Структуру PageObjects

Навигационные шаги

Примеры тестов

Стандарты Allure

Гайд по расширению покрытия

🤝 Автор

Davlat — QA Automation Engineer
📧 d.khakimov@eskhata.com

Проект: Creatio Credit UI Automation
Команда: Platform Creatio