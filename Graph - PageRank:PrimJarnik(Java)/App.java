package graph;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
         new support.graph.GraphVisualizer(stage, null);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
