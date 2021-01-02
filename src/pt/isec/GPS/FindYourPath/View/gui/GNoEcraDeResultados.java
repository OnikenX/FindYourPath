package pt.isec.GPS.FindYourPath.View.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pt.isec.GPS.FindYourPath.Controller.FindYourPathObservable;
import pt.isec.GPS.FindYourPath.Model.data.CursoEConfianca;
import pt.isec.GPS.FindYourPath.Model.data.excelreader.Curso;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeResultados;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class GNoEcraDeResultados extends VBox {

    TextField campoDeProcura = new TextField();
    GridPane resultados = new GridPane();
    List<CursoEConfianca> listaDeCursosEConfiancas = null;
    Label mediaInserida = null;
    final FindYourPathObservable findYourPathObservable;


    static final String Curso = "Curso";
    static final String Universidade = "Universidade";
    static final String Media = "Media";
    static final String Confiança = "Confiança";


    public GNoEcraDeResultados(FindYourPathObservable findYourPathObservable) {
        this.findYourPathObservable = findYourPathObservable;

        //setup da lista
        organizaComponentes();
        findYourPathObservable.addPropertyChangeListener(e -> actualizar());
        actualizar();
    }

    private void organizaComponentes() {
        Label labelResult = new Label("Resultados: ");
        Button procura = new Button("Procurar");
        procura.setOnAction(e -> actualizar());
        Button guardar = new Button("Salvar");
        guardar.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory((new File(System.getProperty("user.dir"))));
            fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Excel file", "xlsx" ));
            fc.setInitialFileName("excel.xls");

            List<Curso> lista = new LinkedList<>();
            for (var i : listaDeCursosEConfiancas) {
                lista.add(i.getCurso());
            }
            try {
                findYourPathObservable.save(fc.showSaveDialog(this.getParent().getScene().getWindow()), lista);
            } catch (Exception exception) {
                (new Alert(Alert.AlertType.ERROR, "Não foi possivel gravar.")).showAndWait();
            }
        });

        Button nextTest = new Button("Novo Teste");
        nextTest.setOnAction(e -> findYourPathObservable.novoTeste());
        campoDeProcura.autosize();
        getChildren().addAll(new HBox(labelResult, campoDeProcura, procura, guardar, nextTest));
        resultados.setGridLinesVisible(true);
        resultados.setHgap(5);
        resultados.setVgap(2);
        var scroll = new ScrollPane(resultados);
        scroll.autosize();
        getChildren().add(scroll);
        mediaInserida = new Label();
        setMediaLabelText();
        getChildren().add(mediaInserida);
    }

    private void setMediaLabelText() {
        mediaInserida.setText("Media inserida: " + findYourPathObservable.getMedia());
    }


    /**
     * @param nome
     * @return Uma label o texto recebido em bold
     */
    LinkedList<Button> titlenodes = new LinkedList<>();

    Button findnode(String name) {
        for (var node : titlenodes)
            if (node.getId().equals(name))
                return node;
        return null;
    }

    private Node setTitleNodes(String nome) {
        Button toreturn = findnode(nome);
        if (toreturn != null)
            return toreturn;
        toreturn = new Button(nome);
        toreturn.setStyle("stype:bold");
        toreturn.setId(nome);
        toreturn.setOnAction(e -> orderby(nome));
        titlenodes.add(toreturn);
        return toreturn;
    }

    static String lastselected = "";

    private void orderby(String nome) {
        switch (nome) {
            case Curso:

                if (lastselected.equals(nome)) {
                    Collections.reverse(listaDeCursosEConfiancas);
                } else {
                    listaDeCursosEConfiancas.sort(Comparator.comparing(a -> a.getCurso().getNome()));
                    lastselected = nome;
                }
                break;
            case Confiança:

                if (lastselected.equals(nome)) {
                    Collections.reverse(listaDeCursosEConfiancas);
                } else {
                    listaDeCursosEConfiancas.sort(Comparator.comparing(CursoEConfianca::getConfianca));
                    lastselected = nome;
                }
                break;
            case Media:

                if (lastselected.equals(nome)) {
                    Collections.reverse(listaDeCursosEConfiancas);
                } else {
                    listaDeCursosEConfiancas.sort(Comparator.comparing(a -> a.getCurso().getMedia()));
                    lastselected = nome;
                }
                break;
            case Universidade:
                if (lastselected.equals(nome)) {
                    Collections.reverse(listaDeCursosEConfiancas);
                } else {
                    listaDeCursosEConfiancas.sort(Comparator.comparing(a -> a.getCurso().getUniversidade()));
                    lastselected = nome;
                }
                break;
        }
        //faz reset a grid depois de dar sort
        criaOGrid();

    }

    /**
     * Recria a grid
     */
    private void criaOGrid() {
        setMediaLabelText();
        //limpa o conteudo anterior
        resultados.getChildren().clear();

        resultados.addRow(0,
                setTitleNodes(Curso),
                setTitleNodes(Universidade),
                setTitleNodes(Media),
                setTitleNodes(Confiança)
        );

        for (int i = 0; i < listaDeCursosEConfiancas.size(); i++)
            resultados.addRow(i + 1,
                    new Label(listaDeCursosEConfiancas.get(i).getCurso().getNome()),
                    new Label(listaDeCursosEConfiancas.get(i).getCurso().getUniversidade()),
                    new Label(String.valueOf(listaDeCursosEConfiancas.get(i).getCurso().getMedia())),
                    new Label(String.format("%.2f", listaDeCursosEConfiancas.get(i).getConfianca())+" %")
            );
    }

    private void actualizar() {
        if (findYourPathObservable.getEstado() == NoEcraDeResultados.class) {
            System.out.println("No Resultados");
            setVisible(true);
            try {
                String termoDeProcura;
                if (campoDeProcura.getText().isBlank())
                    termoDeProcura = null;
                else
                    termoDeProcura = campoDeProcura.getText();
                listaDeCursosEConfiancas = findYourPathObservable.finalizaTesteEObtemResultados(termoDeProcura);
                for (var i : listaDeCursosEConfiancas)
                    System.out.println("Curso: " + i.getCurso().toString() + " ;nome confiança: " + i.getConfianca());
                System.out.println("tamanho da lista: " + listaDeCursosEConfiancas.size());
                lastselected = "";
                criaOGrid();
            } catch (Exception e) {
                ErrorDialogException.set(e);
            }
        } else
            setVisible(false);
    }
}
