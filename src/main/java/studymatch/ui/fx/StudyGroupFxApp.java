package studymatch.ui.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class StudyGroupFxApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StudyGroupFxApp.class.getResource("/studymatch/ui/fx/main_view.fxml");
        System.out.println("FXML URL = " + url);

        FXMLLoader loader = new FXMLLoader(
                StudyGroupFxApp.class.getResource("/studymatch/ui/fx/main_view.fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("스터디그룹 매칭 시뮬레이터");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
