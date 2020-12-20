package pt.isec.GPS.FindYourPath.Model.data;

import org.xml.sax.SAXException;
import pt.isec.GPS.FindYourPath.Model.XMLReader.Questao;
import pt.isec.GPS.FindYourPath.Model.XMLReader.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindYourPathData {

    private long media;

    private int catA = 0;
    private int catB = 0;
    private int catC = 0;
    private int catD = 0;
    private int catE = 0;
    private int catF = 0;
    private int catG = 0;
    private int catH = 0;
    private int catI = 0;
    private int catJ = 0;
    private int catK = 0;
    private int catL = 0;
    private int catM = 0;
    private int catN = 0;

    //recebem numnero da pergunta a carregar
    //lê e obtem pergunta
    //return Questão pedida
    public static synchronized Questao obtemQuestao(int num_pergunta) throws ParserConfigurationException, SAXException, IOException {

        String path = "perguntas\\" + num_pergunta;

        return XMLReader.LeitorXML(path);
    }

    //recebe média
    //verifica se média [9.5; 20]
    //return false:a média não pertence ao intervalo
    //return true: a média foi devidamente colocada e
    public boolean setMedia(int n){
        if(n < 9.5 || n > 20.0)
            return false;
        media = n;
        return true;
    }



    public int getCatA() {
        return catA;
    }
    public void incCatA() {
        catA++;
    }

    public int getCatB() {
        return catB;
    }
    public void incCatB() {
        catB++;
    }

    public int getCatC() {
        return catC;
    }
    public void incCatC() {
        catC++;
    }

    public int getCatD() {
        return catD;
    }
    public void incCatD() {
        catD++;
    }

    public int getCatE() {
        return catE;
    }
    public void incCatE() {
        catE++;
    }

    public int getCatF() {
        return catF;
    }
    public void incCatF() {
        catF++;
    }

    public int getCatG() {
        return catG;
    }
    public void incCatG() {
        catG++;
    }

    public int getCatH() {
        return catH;
    }
    public void incCatH() {
        catH++;
    }

    public int getCatI() {
        return catI;
    }
    public void incCatI() {
        catI++;
    }

    public int getCatJ() {
        return catJ;
    }
    public void incCatJ() {
        catJ++;
    }

    public int getCatK() {
        return catK;
    }
    public void incCatK() {
        catK++;
    }

    public int getCatL() {
        return catL;
    }
    public void incCatL() {
        catL++;
    }

    public int getCatM() {
        return catM;
    }
    public void incCatM() {
        catM++;
    }

    public int getCatN() {
        return catN;
    }
    public void incCatN() {
        catN++;
    }
}
