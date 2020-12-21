package pt.isec.GPS.FindYourPath.Controller;

import org.xml.sax.SAXException;
import pt.isec.GPS.FindYourPath.Model.FindYourPathModel;
import pt.isec.GPS.FindYourPath.Model.data.CursoEConfianca;
import pt.isec.GPS.FindYourPath.Model.data.XMLReader.Questao;
import pt.isec.GPS.FindYourPath.Model.estados.IEstado;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class FindYourPathObservable extends PropertyChangeSupport  implements Serializable {
    //versao serializada para guardar estado
    //private static final long serialVersionUID = 1L;


    public static Questao obtemQuestao(int num_pergunta) throws ParserConfigurationException, SAXException, IOException {
        return FindYourPathModel.obtemQuestao(num_pergunta);
    }

    public boolean setMedia(String med) {
        return findYourPathModel.setMedia(med);
    }

    public Class<? extends IEstado> getEstado() {
        return findYourPathModel.getEstado();
    }

    public List<CursoEConfianca> finalizaTesteEObtemResultados(String filtro) throws FileNotFoundException, CloneNotSupportedException {
        return findYourPathModel.finalizaTesteEObtemResultados(filtro);
    }

    //modelo
    private FindYourPathModel findYourPathModel;

    public FindYourPathObservable(FindYourPathModel findYourPathModel) {
        super(findYourPathModel);
        this.findYourPathModel  = findYourPathModel;
    }

    //estados
    public void comecarTeste(String media) throws Exception {
        findYourPathModel.comecarTeste(media);
        firePropertyChange(null, false, true);
        System.out.println("firePropertyChange");

    }

    public void selecionarResposta(int nDaResposta) throws Exception {
         findYourPathModel.selecionarResposta(nDaResposta);
        firePropertyChange(null, false, true);
        System.out.println("firePropertyChange");

    }

    public void novoTeste() {
         findYourPathModel.novoTeste();
        firePropertyChange(null, false, true);
        System.out.println("firePropertyChange");
    }

    public int getAllPages() {
        return findYourPathModel.getAllPages();
    }

    public double getMedia() {
        return findYourPathModel.getMedia();
    }

    public int getActualPage() {
        return findYourPathModel.getActualPage();
    }

    public String getPergunta() {
        return findYourPathModel.getPergunta();
    }

    public String getCategoria() {
        return findYourPathModel.getCategoria();
    }

    ////delegacoes do model
    //gets

    //sets


}