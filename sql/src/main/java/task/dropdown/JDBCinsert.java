package task.dropdown;

import java.sql.*;

public class JDBCinsert {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/company_db";
        String user = "root";  
        String password = "Fenic#202314";  

        String checkSQL = "SELECT COUNT(*) FROM employee WHERE empcode = ?";
        String insertSQL = "INSERT INTO employee (empcode, empname, empage, esalary) VALUES (?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);

            int[][] employees = {
                {101, 25, 10000},
                {102, 30, 20000},
                {103, 20, 40000},
                {104, 40, 80000},
                {105, 25, 90000}
            };
            String[] names = {"Jenny", "Jacky", "Joe", "John", "Shameer"};

            for (int i = 0; i < employees.length; i++) {
                checkStmt.setInt(1, employees[i][0]); 
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) { 
                    insertStmt.setInt(1, employees[i][0]); 
                    insertStmt.setString(2, names[i]);    
                    insertStmt.setInt(3, employees[i][1]); 
                    insertStmt.setDouble(4, employees[i][2]); 
                    insertStmt.executeUpdate();
                } else {
                    System.out.println("Skipping duplicate entry: " + employees[i][0]);
                }
            }

            System.out.println("Data inserted successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
