<<<<<<< HEAD
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

//        HBox topControls = createTopControls();
//        VBox tableSection = createTableSection();
//
//        root.setTop(topControls);
//        root.setCenter(tableSection);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Employee Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

=======
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;

public class Main {
//    @Override
//    public void start(Stage primaryStage) {
//        BorderPane root = new BorderPane();
//        root.setPadding(new Insets(10));
//
////        HBox topControls = createTopControls();
////        VBox tableSection = createTableSection();
////
////        root.setTop(topControls);
////        root.setCenter(tableSection);
//
//        Scene scene = new Scene(root, 900, 600);
//        primaryStage.setTitle("Employee Management System");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    public static void main(String[] args) {
//        launch(args);
        System.out.println("hi");
    }

>>>>>>> master
}