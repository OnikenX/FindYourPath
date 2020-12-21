package pt.isec.GPS.FindYourPath.View.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.GPS.FindYourPath.Controller.FindYourPathObservable;
import pt.isec.GPS.FindYourPath.Model.data.CursoEConfianca;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeComeco;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeResultados;

import java.util.List;

public class GNoEcraDeResultados extends VBox {
    final FindYourPathObservable findYourPathObservable;
    public GNoEcraDeResultados(FindYourPathObservable findYourPathObservable) {
        this.findYourPathObservable = findYourPathObservable;

        //setup da lista
        organizaComponentes();
        findYourPathObservable.addPropertyChangeListener(e -> actualizar());

    }

    private void organizaComponentes() {
Label labelResult = new Label("Resultados: ");
    }

    private void actualizar() {
        if(findYourPathObservable.getEstado() == NoEcraDeResultados.class){
            setVisible(true);
            try {
                findYourPathObservable.finalizaTesteEObtemResultados(null);
            }catch(Exception e){
                ErrorDialogException.set(e);
            }
        }
        setVisible(false);
    }
}
