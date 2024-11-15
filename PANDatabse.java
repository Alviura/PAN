import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class SQLQueryChooser {
    private static final String DB_URL = "jdbc:sqlserver://kott0012-sql-server.database.windows.net:1433;";
    private static final String USER = "kott0012";
    private static final String PASSWORD = "Bolsol@2024@";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("WELCOME TO THE PATIENT ASSISTANT NETWORK DATABASE SYSTEM");
                System.out.println("1. Enter a new team into the database");
                System.out.println("2. Enter a new client into the database and associate with teams");
                System.out.println("3. Enter a new volunteer into the database and associate with teams");
                System.out.println("4. Enter volunteer hours worked this month");
                System.out.println("5. Enter a new employee into the database and associate with teams");
                System.out.println("6. Enter an expense charged by an employee");
                System.out.println("7. Enter a new donor and associate with donations");
                System.out.println("8. Retrieve doctor name and phone number of a client");
                System.out.println("9. Retrieve total expenses by each employee for a period");
                System.out.println("10. Retrieve list of volunteers in teams supporting a client");
                System.out.println("11. Retrieve team names founded after a date");
                System.out.println("12. Retrieve contact information of all people");
                System.out.println("13. Retrieve donations by donors who are also employees");
                System.out.println("14. Increase salary by 10% for employees with multiple team reports");
                System.out.println("15. Delete clients without insurance and low transport importance");
                System.out.println("16. Import teams from a data file");
                System.out.println("17. Export mailing list to a data file");
                System.out.println("18. Quit");
                System.out.print("Enter your choice: ");
                
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        executeUpdate(conn, "INSERT INTO Team (TeamID, Team_name, Type, Date_formed) VALUES (?, ?, ?, ?)");
                        break;
                    case 2:
                        executeUpdate(conn, "INSERT INTO Client (ClientID, Doctor_name, Doctor_PhoneNo, PolicyID, SSN, Assigned_date) VALUES (?, ?, ?, ?, ?, ?)");
                        break;
                    case 3:
                        executeUpdate(conn, "INSERT INTO Volunteer (VolunteerID, Join_date, Last_training_date, Last_training_location, SSN) VALUES (?, ?, ?, ?, ?)");
                        break;
                    case 4:
                        executeUpdate(conn, "UPDATE VolunterTeam SET Hours_worked = ? WHERE VolunteerID = ? AND TeamID = ?");
                        break;
                    case 5:
                        executeUpdate(conn, "INSERT INTO Employee (EmployeeID, SSN, Salary, Marital_status, Hire_date) VALUES (?, ?, ?, ?, ?)");
                        break;
                    case 6:
                        executeUpdate(conn, "INSERT INTO Expense (ExpenseNo, EmployeeID, Description, Expense_date, Amount) VALUES (?, ?, ?, ?, ?)");
                        break;
                    case 7:
                        executeUpdate(conn, "INSERT INTO Donor (DonorID, Is_anonymous, SSN) VALUES (?, ?, ?)");
                        break;
                    case 8:
                        executeQuery(conn, "SELECT Doctor_name, Doctor_PhoneNo FROM Client WHERE ClientID = ?");
                        break;
                    case 9:
                        executeQuery(conn, "SELECT EmployeeID, SUM(Amount) AS TotalExpenses FROM Expense WHERE Expense_date BETWEEN ? AND ? GROUP BY EmployeeID ORDER BY TotalExpenses DESC");
                        break;
                    case 10:
                        executeQuery(conn, "SELECT VolunteerID FROM VolunterTeam JOIN ClientTeam ON VolunterTeam.TeamID = ClientTeam.TeamID WHERE ClientID = ?");
                        break;
                    case 11:
                        executeQuery(conn, "SELECT Team_name FROM Team WHERE Date_formed > ?");
                        break;
                    case 12:
                        executeQuery(conn, "SELECT Name, SSN, Mailing_address, Email_address, PhoneNumber, EmergencyContact_Name, EmergencyContact_PhoneNumber FROM Person");
                        break;
                    case 13:
                        executeQuery(conn, "SELECT Name, SUM(Donation.Amount) AS TotalDonation, Is_anonymous FROM Donor JOIN Donation ON Donor.DonorID = Donation.DonorID WHERE EXISTS (SELECT 1 FROM Employee WHERE Employee.SSN = Donor.SSN) GROUP BY Name, Is_anonymous ORDER BY TotalDonation DESC");
                        break;
                    case 14:
                        executeUpdate(conn, "UPDATE Employee SET Salary = Salary * 1.10 WHERE EmployeeID IN (SELECT EmployeeID FROM Report GROUP BY EmployeeID HAVING COUNT(DISTINCT TeamID) > 1)");
                        break;
                    case 15:
                        executeUpdate(conn, "DELETE FROM Client WHERE ClientID IN (SELECT ClientID FROM ClientNeed WHERE Importance < 5) AND PolicyID IS NULL");
                        break;
                    case 16:
                        importTeamsFromFile(conn, scanner);
                        break;
                    case 17:
                        exportMailingListToFile(conn, scanner);
                        break;
                    case 18:
                        System.out.println("Exiting...");
                        break;
                    default: 
                        System.out.println("Invalid choice. Please try again.");
                }
                
            } while (choice != 18);

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void executeQuery(Connection conn, String query) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(", ");
                    System.out.print(rs.getString(i));
                }
                System.out.println();
            }
        }
    }

    private static void executeUpdate(Connection conn, String query) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set parameters for pstmt as needed (example: pstmt.setString(1, "value");)
            pstmt.executeUpdate();
            System.out.println("Operation executed successfully.");
        }
    }
    // Method for importing teams from a data file
    private static void importTeamsFromFile(Connection conn, Scanner scanner) {
        System.out.print("Enter the input file name for team data: ");
        String fileName = scanner.nextLine();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String insertQuery = "INSERT INTO Team (TeamID, Team_name, Type, Date_formed) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                while ((line = reader.readLine()) != null) {
                    String[] teamData = line.split(",");
                    if (teamData.length == 4) {
                        pstmt.setString(1, teamData[0].trim()); // TeamID
                        pstmt.setString(2, teamData[1].trim()); // Team_name
                        pstmt.setString(3, teamData[2].trim()); // Type
                        pstmt.setDate(4, Date.valueOf(teamData[3].trim())); // Date_formed
                        pstmt.executeUpdate();
                    } else {
                        System.out.println("Incorrect data format in file: " + line);
                    }
                }
                System.out.println("Teams imported successfully.");
            }
        } catch (IOException | SQLException e) {
            System.out.println("Error importing teams: " + e.getMessage());
        }
    }
     // Method for exporting mailing list to a data file
     private static void exportMailingListToFile(Connection conn, Scanner scanner) {
        System.out.print("Enter the output file name for mailing list: ");
        String fileName = scanner.nextLine();
        
        String query = "SELECT Name, Mailing_address FROM Person WHERE OnMailingList = 1";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery();
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            while (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Mailing_address");
                writer.write(name + ", " + address);
                writer.newLine();
            }
            System.out.println("Mailing list exported successfully to " + fileName);
        } catch (IOException | SQLException e) {
            System.out.println("Error exporting mailing list: " + e.getMessage());
        }
    }
}
