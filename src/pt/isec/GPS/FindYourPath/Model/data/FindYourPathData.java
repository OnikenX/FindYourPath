package pt.isec.GPS.FindYourPath.Model.data;

import org.xml.sax.SAXException;
import pt.isec.GPS.FindYourPath.Model.XMLReader.Questao;
import pt.isec.GPS.FindYourPath.Model.XMLReader.XMLReader;
import pt.isec.GPS.FindYourPath.Model.excelreader.Curso;
import pt.isec.GPS.FindYourPath.Model.excelreader.ExcelFileReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FindYourPathData {

    private double media;

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

    //recebe média em string
    //verifica se média [9.5; 20]
    //return false:a média não pertence ao intervalo
    //return true: a média foi devidamente colocada e
    public boolean setMedia(String med) {
        double n;
        try {
            n = Double.parseDouble(med);
        }catch(NullPointerException | NumberFormatException e){
            return false;
        }


        if (n < 9.5 || n > 20.0)
            return false;
        media = n;
        return true;
    }

    /*
    obtem categoria com valor máximo;           <-      escolhe categorias
    obtem area baseado na categoria
    obtem nome de cursos baseado na area
    obtem cursos baseado no seu nome
    ordena cursos baseado na proximidade da média
    cria niveis de confianca baseado nas diferencas da média (maior ou menor)
    escolhe 5 cursos e devolve
     */


    //obtem a categoria com valor máximo
    //return: categoria com valor máximo e categorias com 90% dos pontos da cat com o valor máximo
    private List<String> getMaxValueCategorias() {
        String max;
        String second;
        String third;

        //cria lista secundária para ajudar a ordenas as coisas
        ArrayList<PontosECategoria> listaSecundaria = new ArrayList<PontosECategoria>();

        //coloca "cursos" e pontos dos cursos na lista secundaria
        listaSecundaria.add(new PontosECategoria("ciencia", catA));
        listaSecundaria.add(new PontosECategoria("tecnologia", catB));
        listaSecundaria.add(new PontosECategoria("economia", catC));
        listaSecundaria.add(new PontosECategoria("negocios", catD));
        listaSecundaria.add(new PontosECategoria("borucracia", catE));
        listaSecundaria.add(new PontosECategoria("arte", catF));
        listaSecundaria.add(new PontosECategoria("servicos", catG));
        listaSecundaria.add(new PontosECategoria("cienciaTec", catH));
        listaSecundaria.add(new PontosECategoria("tecnologiaTec", catI));
        listaSecundaria.add(new PontosECategoria("exterior", catJ));
        listaSecundaria.add(new PontosECategoria("negociosTec", catK));
        listaSecundaria.add(new PontosECategoria("comunicacao", catL));
        listaSecundaria.add(new PontosECategoria("arteTec", catM));
        listaSecundaria.add(new PontosECategoria("servicosTec", catN));

        //ordena essa lista secundaria do maior para o menor
        listaSecundaria.sort(new CustomComparatorPontosECategoria());

        //adiciona à lista a dar return o maior curso e os cursos com 90%+ dos seus ponto
        List<String> listaSaidas = new ArrayList<String>();

        listaSaidas.add(listaSecundaria.get(0).getNome());      //adiciona o primeiro curso

        //enquanto houver cursos e pontos > 0.9*pontos do maior
        for (int i = 1; i < listaSecundaria.size(); i++) {                                                  //para todos os cursos
            if (listaSecundaria.get(i).getPontos() > 0.9 * listaSecundaria.get(0).getPontos())             //se tiver 90% pontos do maior curso
                listaSaidas.add(listaSecundaria.get(i).getNome());                                      //adiciona
            else                                                                                        //caso não tenha 90% dos pontos da saida com mais pontos
                break;                                                                                  //sai do loop, uma vez que os cursos estão ordenados
        }

        return listaSaidas;
    }


    //recebe lista de cagorias da função getMaxValueCategorias()
    //faz um lista com o nome dos cursos a procurar baseado na categoria
    //devolve essa lista com TODOS os nomes de cursos baseado nessa categoria
    private List<String> getNomeCursos(List<String> categorias) {
        List<String> nomeCursos = new ArrayList<String>();

        for (String categoria : categorias) {
            if (categoria.equals("ciencia")) {

            } else if (categoria.equals("tecnologia")) {

            } else if (categoria.equals("economia")) {

            } else if (categoria.equals("negocios")) {

            } else if (categoria.equals("borucracia")) {

            } else if (categoria.equals("arte")) {

            } else if (categoria.equals("servicos")) {

            } else if (categoria.equals("cienciaTec")) {

            } else if (categoria.equals("tecnologiaTec")) {

            } else if (categoria.equals("exterior")) {

            } else if (categoria.equals("negociosTec")) {

            } else if (categoria.equals("comunicacao")) {

            } else if (categoria.equals("arteTec")) {

            } else if (categoria.equals("servicosTec")) {

            }

        }

        return nomeCursos;
    }


    private List<Curso> getListCursos(List<String> listaNomeCursos) {

        List<Curso> listaCursos = new ArrayList<Curso>();       //cria uma lista que vai guardar todos os cursos

        for (String nomeCurso : listaNomeCursos) {                  //para cada um dos nomes dos cursos

            try {
                //adiciona à lista de cursos todos os cursos encontrados para o nome a procurar no for each
                listaCursos.addAll(Objects.requireNonNull(ExcelFileReader.getCursosByNome(nomeCurso)));

            } catch (NullPointerException e) {      //virtualmente não acontece porque a função estsa preparada para isto. PODE é vir vazia
            }
        }

        return listaCursos;         //devolve lista com todos os cursos
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


    public class PontosECategoria {
        private int pontos;
        private String nome;


        public PontosECategoria(String nome, int pontos) {
            this.pontos = pontos;
            this.nome = nome;
        }

        public int getPontos() {
            return pontos;
        }

        public String getNome() {
            return nome;
        }

    }

    public class CustomComparatorPontosECategoria implements Comparator<PontosECategoria> {

        //return 1-> primeiro é maior
        //return 0-> sao iguais
        //return -1 -> segundo é maior
        @Override
        public int compare(PontosECategoria o1, PontosECategoria o2) {
            if (o1.getPontos() > o2.getPontos())
                return 1;
            else if (o1.getPontos() == o2.getPontos())
                return 0;
            return -1;
        }
    }


}