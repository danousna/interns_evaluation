package com.sr03.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Model {
    private String url = "jdbc:mysql://localhost:3306/sr03_interns_evaluation";
    private String user = "root";
    private String password = "natandanous";
    private Connection conn = null;

    public Model() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }
    }

    public void query(String q) {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            /* Do something */
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch(SQLException ignore) {

                }
            }
        }
    }
}

