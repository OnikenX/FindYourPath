package pt.isec.GPS.FindYourPath.View.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pt.isec.GPS.FindYourPath.Controller.FindYourPathObservable;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeComeco;
import pt.isec.GPS.FindYourPath.Model.estados.NoTeste;

public class GNoTeste extends VBox {
    final FindYourPathObservable findYourPathObservable;
    final Label pergunta = new Label();
    Label page = new Label();

    public GNoTeste(FindYourPathObservable findYourPathObservable) {
        this.findYourPathObservable = findYourPathObservable;
        pergunta.setWrapText(true);
        page.setWrapText(true);
        organizaComponentes();
        actualizar();
        findYourPathObservable.addPropertyChangeListener(e -> actualizar());
    }

    private void selecionarRespostaN(int N){
        try {
            findYourPathObservable.selecionarResposta(N);
        } catch (Exception ex) {
            ErrorDialogException.set(ex);
        }
    }

    private void organizaComponentes() {
        Button resposta3 = new Button("M-Gosto Muito");
        resposta3.setOnAction(e -> selecionarRespostaN(3));
        Button resposta2 = new Button("m-Gosto");
        resposta2.setOnAction(e -> selecionarRespostaN(2));
        Button resposta1 = new Button("N-Não Gosto");
        resposta1.setOnAction(e -> selecionarRespostaN(1));
        Button resposta0 = new Button("D-Não Gosto Mesmo nada");
        resposta0.setOnAction(e -> selecionarRespostaN(0));

        getChildren().addAll(pergunta, resposta3, resposta2,resposta1, resposta0, page);
        page.setAlignment(Pos.BOTTOM_LEFT);

    }

    private void actualizar() {
        if (findYourPathObservable.getEstado() == NoTeste.class) {
            System.out.println("no teste");
            setVisible(true);
            pergunta.setText(findYourPathObservable.getPergunta());
            page.setText("Pagina " + findYourPathObservable.getActualPage() + " de " + findYourPathObservable.getAllPages());
        } else
            setVisible(false);
    }
}
