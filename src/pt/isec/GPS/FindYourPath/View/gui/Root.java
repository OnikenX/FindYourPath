package pt.isec.GPS.FindYourPath.View.gui;

import javafx.scene.layout.StackPane;
import pt.isec.GPS.FindYourPath.Controller.FindYourPathObservable;

public class Root extends StackPane{
    private final FindYourPathObservable findYourPathObservable;
    StackPane center;

    public Root(FindYourPathObservable findYourPathObservable) {
        this.findYourPathObservable = findYourPathObservable;
        organizaComponentes();
    }

    private void organizaComponentes() {
        GNoEcraDeComeco gNoEcraDeComeco = new GNoEcraDeComeco(findYourPathObservable);
        GNoEcraDeResultados gNoEcraDeResultados = new GNoEcraDeResultados(findYourPathObservable);
        GNoTeste gNoTeste = new GNoTeste(findYourPathObservable);
        getChildren().addAll(gNoEcraDeComeco, gNoTeste, gNoEcraDeResultados);
    }

}
