package pt.isec.GPS.FindYourPath.Model;

import org.xml.sax.SAXException;
import pt.isec.GPS.FindYourPath.Model.data.CursoEConfianca;
import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;
import pt.isec.GPS.FindYourPath.Model.data.XMLReader.Questao;
import pt.isec.GPS.FindYourPath.Model.estados.IEstado;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeComeco;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class FindYourPathModel {
    public List<CursoEConfianca> finalizaTesteEObtemResultados(String filtro) throws FileNotFoundException, CloneNotSupportedException {
        return findYourPathData.finalizaTesteEObtemResultados(filtro);
    }

    private IEstado estado;
    private final FindYourPathData findYourPathData;

    public FindYourPathModel() {
        this.findYourPathData = new FindYourPathData();
        this.estado = new NoEcraDeComeco(findYourPathData);
    }

    public static Questao obtemQuestao(int num_pergunta) throws ParserConfigurationException, SAXException, IOException {
        return FindYourPathData.obtemQuestao(num_pergunta);
    }

    public boolean setMedia(String med) {
        return findYourPathData.setMedia(med);
    }

    public Class<? extends IEstado> getEstado(){
        return estado.getClass();
    }

    public void comecarTeste(String media) throws Exception {
        estado = estado.comecarTeste(media);
    }

    public void selecionarResposta(int nDaResposta) throws Exception {
        estado = estado.selecionarResposta(nDaResposta);
    }

    public int getAllPages() {
        return findYourPathData.getAllPages();
    }

    public double getMedia() {
        return findYourPathData.getMedia();
    }

    public int getActualPage() {
        return findYourPathData.getActualPage();
    }

    public String getPergunta() {
        return findYourPathData.getPergunta();
    }

    public String getCategoria() {
        return findYourPathData.getCategoria();
    }

    public void novoTeste() {
        estado = estado.novoTeste();
    }


}
