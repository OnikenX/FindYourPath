package pt.isec.GPS.FindYourPath.View.gui;

import javafx.scene.layout.VBox;
import pt.isec.GPS.FindYourPath.Controller.FindYourPathObservable;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeComeco;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeResultados;

public class GNoEcraDeResultados extends VBox {
    final FindYourPathObservable findYourPathObservable;
    public GNoEcraDeResultados(FindYourPathObservable findYourPathObservable) {
        this.findYourPathObservable = findYourPathObservable;
        findYourPathObservable.addPropertyChangeListener(e -> actualizar());
    }

    private void actualizar() {
        if(findYourPathObservable.getEstado() == NoEcraDeResultados.class)
            setVisible(true);
        setVisible(false);
    }
}
