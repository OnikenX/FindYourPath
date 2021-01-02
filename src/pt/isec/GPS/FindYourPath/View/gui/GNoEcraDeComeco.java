package pt.isec.GPS.FindYourPath.View.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pt.isec.GPS.FindYourPath.Controller.FindYourPathObservable;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeComeco;

public class GNoEcraDeComeco extends VBox {
    private final FindYourPathObservable findYourPathObservable;

    public GNoEcraDeComeco(FindYourPathObservable findYourPathObservable) {
        this.findYourPathObservable = findYourPathObservable;
        findYourPathObservable.addPropertyChangeListener(e -> actualizar());
        organizaComponentes();
    }

    private void actualizar() {
        if (findYourPathObservable.getEstado() == NoEcraDeComeco.class) {
            setVisible(true);
            System.out.println("no actualizar comeco");
        } else
            setVisible(false);
    }

    private void organizaComponentes() {
        Label descricao = new Label(
                "Neste programa vai colcocar a sua média e responder a um teste " +
                        "psicotecnico e no final ser-lhe-à mostrada uma lista de cursos sugerida," +
                        " sendo que pode filtra-la pela localização dos institutos superiores e/ou " +
                        "guardar os resultados obtidos.");
//        descricao.setStyle("-fx-font-style: 'bold';");
        descricao.setWrapText(true);
        TextField media = new TextField();
        media.setPromptText("Insira a media.");
        Button button = new Button("Começar");
        button.setOnAction(e -> {
            if (!findYourPathObservable.setMedia(media.getText())) {
                media.clear();
                media.setPromptText("Valor entre 9.5 a 20.");
            } else {
                try {
                    findYourPathObservable.comecarTeste(media.getText());
                } catch (Exception exception) {
                    ErrorDialogException.set(exception);
                }
            }
        });
        HBox input = new HBox(media, button);
        VBox space = new VBox();
        getChildren().addAll(descricao, space);
        space.setAlignment(Pos.CENTER);
        space.getChildren().addAll(input);
        input.setAlignment(Pos.CENTER);
    }
}
