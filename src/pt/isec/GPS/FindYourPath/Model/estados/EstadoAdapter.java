package pt.isec.GPS.FindYourPath.Model.estados;

import org.xml.sax.SAXException;
import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class EstadoAdapter implements IEstado{
    private final FindYourPathData findYourPathData;

    public EstadoAdapter(FindYourPathData findYourPathData){
        this.findYourPathData = findYourPathData;
    }

    protected FindYourPathData getFindYourPathData() {
        return findYourPathData;
    }

    @Override
    public IEstado comecarTeste(String media) throws Exception {
        return this;
    }

    @Override
    public IEstado selecionarResposta(int nDaResposta) throws Exception{
        return this;
    }

    @Override
    public IEstado novoTeste() {
        return this;
    }
}
