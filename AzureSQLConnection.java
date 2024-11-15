import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AzureSQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://kott0012-sql-server.database.windows.net:1433;"
                   + "database=Patient-Assistant-Network;"
                   + "user=kott0012@kott0012-sql-server;" 
                   + "password=Bolsol@2024@;" 
                   + "encrypt=true;"
                   + "trustServerCertificate=false;"
                   + "loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(url)) {
            System.out.println("Connected to Azure SQL database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
