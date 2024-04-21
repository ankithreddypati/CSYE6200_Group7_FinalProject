import Util.DatabaseConnection;  // Ensure you import the DatabaseConnection class
import View.LandingPageFrame;


public class Driver {
    public static void main(String[] args) {
        System.out.println("============Main Execution Start===================");

        // Initialize the database connection (and create db and tables if necessary)
        DatabaseConnection.getConnection();

        LandingPageFrame frame = new LandingPageFrame();
        frame.setVisible(true);

        System.out.println("============Main Execution End===================");
    }
}
