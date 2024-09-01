package org.example.listeners.db;

import java.sql.*;
import java.time.LocalDate;

public class DBSetupSP {
    private static final String JDBC_URL = "url";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    public static void insertUsersp(String name, double amount, LocalDate lastPayment, LocalDate dueDate) throws SQLException {
        String SQL_INSERT = "INSERT INTO users_sp (name, amount, last_payment, due_date, days_to_payment) VALUES (?, ?, ?, ?, ?)";
        long daysToPayment = java.time.temporal.ChronoUnit.DAYS.between(lastPayment, dueDate);

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, name);
            pst.setDouble(2, amount);
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            pst.setDate(4, java.sql.Date.valueOf(dueDate));
            pst.setLong(5, daysToPayment);
            pst.executeUpdate();
        }
    }

    public static void updateUserAmountsp(String name, double newAmount, LocalDate lastPayment, LocalDate dueDate, int advance_months) throws SQLException {
        String SQL_UPDATE = "UPDATE users_sp SET amount = ?, last_payment = ?, days_to_payment = ?, due_date = ?, advance_months = ? WHERE name = ?";

        LocalDate newDueDate;
        if(advance_months == 0) {
            newDueDate = dueDate.plusMonths(1);
        } else {
            newDueDate = dueDate.plusMonths(advance_months);
        }
        long daysToPayment = java.time.temporal.ChronoUnit.DAYS.between(lastPayment, newDueDate);
        int prevAdvanceMonths = getAdvanceMonthssp(name);

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
            pst.setDouble(1, newAmount);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            pst.setLong(3, daysToPayment);
            pst.setDate(4, java.sql.Date.valueOf(newDueDate));
            pst.setInt(5, advance_months + prevAdvanceMonths);
            pst.setString(6, name);
            pst.executeUpdate();
        }
    }

    public static LocalDate getDueDatesp(String name) throws SQLException {
        String SQL_QUERY = "SELECT due_date FROM users_sp WHERE name = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_QUERY)) {

            pst.setString(1, name);

            // Execute the query and get the result
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the due_date and return it as a LocalDate
                    return rs.getDate("due_date").toLocalDate();
                } else {
                    // Handle the case where the user ID does not exist
                    return null;
                }
            }
        }
    }

    public static int getAdvanceMonthssp(String name) throws SQLException {
        String SQL_QUERY = "SELECT advance_months FROM users_sp WHERE name = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_QUERY)) {

            pst.setString(1, name);

            // Execute the query and get the result
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the due_date and return it as a LocalDate
                    return rs.getInt("advance_months");
                } else {
                    // Handle the case where the user ID does not exist
                    return -123;
                }
            }
        }
    }

    public static int getAmountPaidsp(String name) throws SQLException {
        String SQL_QUERY = "SELECT amount FROM users_sp WHERE name = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_QUERY)) {

            pst.setString(1, name);

            // Execute the query and get the result
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the due_date and return it as a LocalDate
                    return rs.getInt("amount");
                } else {
                    // Handle the case where the user ID does not exist
                    return -1;
                }
            }
        }
    }
}
