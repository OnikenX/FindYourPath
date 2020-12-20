package pt.isec.GPS.FindYourPath.Model.XMLReader;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;

public final class XMLReader {

    private XMLReader() {
    }

    ;

    public static Questao LeitorXML(String path) throws IOException, SAXException, ParserConfigurationException {

        //abre e "controi" ficheiro
        File fXmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        //normalização do documento-> não obrigatório porem recomendado
        doc.getDocumentElement().normalize();
        //entra no nodo "questão"
        NodeList nList = doc.getElementsByTagName("questao");

        Node nNode = nList.item(0);


        Element eElement = (Element) nNode;
        String pergunta = eElement.getElementsByTagName("pergunta").item(0).getTextContent();
        String categoria = eElement.getElementsByTagName("categoria").item(0).getTextContent();

        Questao questao = new Questao(pergunta, categoria);


        return questao;
    }

}
