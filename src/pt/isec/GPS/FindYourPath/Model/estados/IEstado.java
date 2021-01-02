package pt.isec.GPS.FindYourPath.Model.estados;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface IEstado {
    IEstado comecarTeste(String media) throws Exception;
    IEstado selecionarResposta(int nDaResposta) throws Exception;
    IEstado novoTeste();
}
