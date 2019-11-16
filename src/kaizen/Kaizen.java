package kaizen;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kaizen.UserData.KaizenDatabase;

public class Kaizen extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        System.out.println("milestone 1");
        Parent root = FXMLLoader.load(getClass().getResource("DeepFocusMode.fxml"));
        Scene scene = new Scene(root);
        System.out.println("milestone 2");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        loadDatabase();
        launch(args);

    }

    public static void loadDatabase() throws ClassNotFoundException, SQLException {
        KaizenDatabase.createUserTable();
        KaizenDatabase.createTimesheetsTable();

        KaizenDatabase.createCategoryTable();
        KaizenDatabase.createLearnings();
        KaizenDatabase.createTasksTable();
        KaizenDatabase.createErrorsTable();

    }
}
