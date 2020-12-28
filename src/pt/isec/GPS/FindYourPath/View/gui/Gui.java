package pt.isec.GPS.FindYourPath.View.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.GPS.FindYourPath.Controller.FindYourPathObservable;
import pt.isec.GPS.FindYourPath.Model.FindYourPathModel;

public class Gui extends Application {

    FindYourPathObservable findYourPathObservable;
    Root root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        findYourPathObservable = new FindYourPathObservable(new FindYourPathModel());
        primaryStage.setTitle("FindYourPath");
        root = new Root(findYourPathObservable);
        root.setCenterShape(true);
        primaryStage.setScene(new Scene(root, 720, 540));
        primaryStage.getScene().getStylesheets().add("pt/isec/GPS/FindYourPath/View/gui/styles.css");
        primaryStage.show();
    }
}
