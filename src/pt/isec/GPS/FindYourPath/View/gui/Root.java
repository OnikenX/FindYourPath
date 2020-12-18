package pt.isec.GPS.FindYourPath.View.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.GPS.FindYourPath.Model.FindYourPathModel;

public class Root extends BorderPane{
    private final FindYourPathModel findYourPathModel;
    StackPane center;
    public Root(FindYourPathModel findYourPathModel) {
        this.findYourPathModel = findYourPathModel;
        organizaComponentes();
    }

    private void organizaComponentes() {
        GNoEcraDeComeco gNoEcraDeComeco = new GNoEcraDeComeco(findYourPathModel);

        center = new StackPane();
    }






}
