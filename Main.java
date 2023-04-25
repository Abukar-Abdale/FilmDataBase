import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Menu menu = new Menu();
            menu.run();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO exception: " + e.getMessage());
        }
    }

}
