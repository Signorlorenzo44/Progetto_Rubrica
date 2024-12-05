import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView();
        AddressBookModel model = new AddressBookModel();
        MainController controller = new MainController(model, mainView);

        Scene scene = new Scene(mainView.getRoot(), 800, 600);
        primaryStage.setTitle("Address Book");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

