<p align="center">
  <img src="https://img.shields.io/badge/Java-17-red?logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Selenide-7.x-brightgreen?logo=selenide&logoColor=white" />
  <img src="https://img.shields.io/badge/TestNG-Framework-orange?logo=testng&logoColor=white" />
  <img src="https://img.shields.io/badge/Allure-Reports-purple?logo=allure&logoColor=white" />
  <img src="https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven&logoColor=white" />
</p>

<p align="center">
  <img src="https://img.shields.io/github/last-commit/Davlat1991/Creatio_Credit?color=blue" />
  <img src="https://img.shields.io/github/repo-size/Davlat1991/Creatio_Credit?color=lightgrey" />
  <img src="https://img.shields.io/badge/Status-Active-success" />
</p>


---

# <p align="center">💳 Creatio_Credit UI Automation</p>

Автоматизация UI тестирования системы **Creatio Credit**, построенная на:
- **Java 17**
- **Selenide 7.x**
- **TestNG Framework**
- **Allure Reports**
- **Maven Build**

Фреймворк использует современную архитектуру PageObject + Components + Steps, оптимизирован под Creatio DOM (динамическая перерисовка, мини-страницы, гриды, lookup-компоненты).

---

# 📁 **Структура проекта**

---

# ⚙️ Конфигурация

### ✔ Файл: `framework.properties`
Используется для локальных и CI конфигураций.


### ✔ ENV overrides (для CI/CD)


---

# 🚀 **Запуск тестов**

### ✔ Запуск всех тестов

```bash
mvn clean test
mvn clean test -Dgroups=smoke
mvn clean test -Dbase.url=http://custom-env -Dbrowser=chrome
allure serve target/allure-results
steps.contact()
     .open()
     .fillAddress("Регистрация", "Москва", "01.01.2024")
     .fillCommunicationValue("123456789")
     .save();


✍ Автор

DavLat – QA Automation Engineer
📧 davlatkhamidov1991@gmail.com