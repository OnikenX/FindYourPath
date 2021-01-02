package pt.isec.GPS.FindYourPath.Model.estados;

import org.xml.sax.SAXException;
import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class NoTeste extends EstadoAdapter {
    public NoTeste(FindYourPathData findYourPathData) throws Exception {
        super(findYourPathData);
        getFindYourPathData().nextPage();


    }

    //TODO: implementar selecionar resposta
    @Override
    public IEstado selecionarResposta(int nDaResposta) throws Exception {
        getFindYourPathData().addPontosCat(nDaResposta, getFindYourPathData().getCategoria());
        if (getFindYourPathData().nextPage())
            return this;
        else{
            System.out.println("entrei no ecra de resultados");
            return new NoEcraDeResultados(getFindYourPathData());
        }
    }
}
