package core.utils;


import core.data.DbConnectionData;
import io.qameta.allure.Attachment;

import java.sql.*;
import java.time.LocalDate;

public class DbHelper {

    private final DbConnectionData connectionData;

    public DbHelper(DbConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    // -----------------------------
    // 🔌 Получение соединения с БД
    // -----------------------------
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                connectionData.getUrl(),
                connectionData.getUser(),
                connectionData.getPassword()
        );
    }

    // -----------------------------
    // 📄 Логируем SQL в Allure
    // -----------------------------
    @Attachment(value = "SQL Query", type = "text/plain")
    public String attachSql(String sql) {
        return sql;
    }

    // -----------------------------
    // 🔍 SELECT, который возвращает одно значение (String)
    // -----------------------------
    public String selectString(String sql) {
        attachSql(sql);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString(1);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("DB Error: " + e.getMessage(), e);
        }
    }

    // -----------------------------
    // 🔢 SELECT int
    // -----------------------------
    public int selectInt(String sql) {
        attachSql(sql);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e) {
            throw new RuntimeException("DB Error: " + e.getMessage(), e);
        }
    }

    // -----------------------------
    // 💲 SELECT double
    // -----------------------------
    public double selectDouble(String sql) {
        attachSql(sql);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            return rs.next() ? rs.getDouble(1) : 0.0;

        } catch (SQLException e) {
            throw new RuntimeException("DB Error: " + e.getMessage(), e);
        }
    }

    // -----------------------------
    // 📅 SELECT LocalDate
    // -----------------------------
    public LocalDate selectDate(String sql) {
        attachSql(sql);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            return rs.next() ? rs.getDate(1).toLocalDate() : null;

        } catch (SQLException e) {
            throw new RuntimeException("DB Error: " + e.getMessage(), e);
        }
    }

    // -----------------------------
    // 🛠 UPDATE / INSERT / DELETE
    // -----------------------------
    public int executeUpdate(String sql) {
        attachSql(sql);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB Error: " + e.getMessage(), e);
        }
    }
}
