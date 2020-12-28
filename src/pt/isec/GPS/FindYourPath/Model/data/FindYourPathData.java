package pt.isec.GPS.FindYourPath.Model.data;

import org.apache.poi.hpsf.ReadingNotSupportedException;
import org.xml.sax.SAXException;
import pt.isec.GPS.FindYourPath.Model.data.XMLReader.Questao;
import pt.isec.GPS.FindYourPath.Model.data.XMLReader.XMLReader;
import pt.isec.GPS.FindYourPath.Model.data.excelreader.Curso;
import pt.isec.GPS.FindYourPath.Model.data.excelreader.ExcelFileReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FindYourPathData {
    public final static String XMLfolder = "perguntas", ExcelFolder = "Excel", ExcelFile = "medias.xls",
            ExcelFilePath = ExcelFolder + File.separator + ExcelFile;

    private double media = -1;

    private final int allPages;
    private int actualPage = 0;
    Questao questaoActual = null;
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

    public FindYourPathData() {
        allPages = Objects.requireNonNull(new File(XMLfolder).list()).length;

    }

    /**
     * recebem numnero da pergunta a carregar
     * lê e obtem pergunta
     * return Questão pedida
     */
    public static synchronized Questao obtemQuestao(int num_pergunta) throws ParserConfigurationException, SAXException, IOException {
        String path = "perguntas\\" + num_pergunta;
        return XMLReader.LeitorXML(path);
    }

    /**
     * recebe média em string
     * verifica se média [9.5; 20]
     * return false:a média não pertence ao intervalo
     * return true: a média foi devidamente colocada e
     */
    public boolean setMedia(String med) {
        double n;
        try {
            n = Double.parseDouble(med);
        } catch (NullPointerException | NumberFormatException e) {
            //System.out.println("returnou excecao");
            //e.printStackTrace();
            return false;
        }
        if (n < 9.5 || n > 20.0)
            return false;
        media = n;
        return true;
    }


    public int getAllPages() {
        return allPages;
    }

    public double getMedia() {
        return media;
    }

    public int getActualPage() {
        return actualPage;
    }

    public String getPergunta() {
        return questaoActual.getPergunta();
    }

    public String getCategoria() {
        return questaoActual.getCategoria();
    }

    public boolean nextPage() throws Exception {
        if (actualPage < allPages) {
            questaoActual = XMLReader.LeitorXML(XMLfolder + File.separator + ++actualPage + ".xml");
            return true;
        }
        return false;
    }

    /**
     * @param filtro -> nome do filtro, NULL se não houver filtro
     * @return Lista CuresosEConfianca Ordenada pela confiança ou algo do genero
     * mostrar os primeiros 5 resultados dessa lista
     */
    public List<CursoEConfianca> finalizaTesteEObtemResultados(String filtro) throws FileNotFoundException, CloneNotSupportedException {

        List<String> listCatMaxValue = getMaxValueCategorias();
        List<String> listNomeCursos = getNomeCursos(listCatMaxValue);
        List<Curso> listaCursos;

        if (filtro == null)
            listaCursos = getListCursosByName(listNomeCursos, null);   //modificar esta para aceitar filtro
        else
            listaCursos = getListCursosByName(listNomeCursos, filtro);

        return orderCursosByConfianca(listaCursos);


    }



    /*
    obtem categoria com valor máximo;           <-      escolhe categorias
    obtem area baseado na categoria             //getMaxValueCategorias
    obtem nome de cursos baseado na area          //getMaxValueCategorias
    obtem cursos baseado no seu nome                //getListCursosByName
    ordena cursos baseado na proximidade da média   //orderCursosByConfianca
    cria niveis de confianca baseado nas diferencas da média (maior ou menor)
    escolhe 5 cursos e devolve
    */

    /**
     * obtem a categoria com valor máximo
     *
     * @return categoria com valor máximo e categorias com 90% dos pontos da cat com o valor máximo
     */
    private List<String> getMaxValueCategorias() {
        String max;
        String second;
        String third;

        //cria lista secundária para ajudar a ordenas as coisas
        ArrayList<PontosECategoria> listaSecundaria = new ArrayList<>();

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
        List<String> listaSaidas = new ArrayList<>();

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
        List<String> nomeCursos = new ArrayList<>();

        for (String categoria : categorias) {
            switch (categoria) {
                case "ciencia" -> {
                    nomeCursos.add("Biologia");
                    nomeCursos.add("Bioquímica");
                    nomeCursos.add("Ciências de Engenharia - Engenharia de Minas e Geoambiente");
                    nomeCursos.add("Engenharia Biológica");
                    nomeCursos.add("Engenharia Biomédica");
                    nomeCursos.add("Geologia");
                    nomeCursos.add("Matemática Aplicada");
                    nomeCursos.add("Medicina");
                    nomeCursos.add("Química Aplicada");
                }
                case "tecnologia" -> nomeCursos.add("Engenharia");
                case "economia" -> {
                    nomeCursos.add("Arquitectura");
                    nomeCursos.add("Biotecnologia Alimentar");
                    nomeCursos.add("Conservação - Restauro");
                    nomeCursos.add("Design");
                    nomeCursos.add("Dieta Mediterrânica e Ciência dos Alimentos");
                    nomeCursos.add("Enologia");
                    nomeCursos.add("Património Cultural e Arqueologia");
                    nomeCursos.add("Produção Alimentar em Restauração");
                    nomeCursos.add("Tecnologia Alimentar");
                    nomeCursos.add("Restauração e Catering");
                }
                case "negocios" -> {
                    nomeCursos.add("Ciência Política");
                    nomeCursos.add("Contabilidade e Gestão Pública");
                    nomeCursos.add("Economia");
                    nomeCursos.add("Engenharia");
                    nomeCursos.add("Gestão");
                    nomeCursos.add("Informática");
                    nomeCursos.add("Marketing");
                    nomeCursos.add("Matemática Aplicada");
                    nomeCursos.add("Negócios Internacionais");
                    nomeCursos.add("Recursos Humanos");
                    nomeCursos.add("Relações Humanas e Comunicação Organizacional");
                }
                case "borucracia" -> {
                    nomeCursos.add("Administração");
                    nomeCursos.add("Comunicação");
                    nomeCursos.add("Contabilidade");
                    nomeCursos.add("Estudos ");
                    nomeCursos.add("Finanças");
                    nomeCursos.add("Finanças");
                    nomeCursos.add("Fiscalidade");
                    nomeCursos.add("Gestão");
                    nomeCursos.add("Secretariado");
                }
                case "arte" -> {
                    nomeCursos.add("Arte");
                    nomeCursos.add("Arquitetura");
                    nomeCursos.add("Arte e Design");
                    nomeCursos.add("Cinema");
                    nomeCursos.add("Dança");
                    nomeCursos.add("Escultura");
                    nomeCursos.add("Estudos Artísticos");
                    nomeCursos.add("Estudos Musicais");
                    nomeCursos.add("Música");
                }
                case "servicos" -> {
                    nomeCursos.add("Ciências");
                    nomeCursos.add("Desporto");
                    nomeCursos.add("Educação");
                    nomeCursos.add("Enfermagem");
                    nomeCursos.add("Farmácia");
                    nomeCursos.add("Fisiologia Clínica");
                    nomeCursos.add("Gestão de Eventos");
                    nomeCursos.add("Optometria e Ciências da Visão");
                    nomeCursos.add("Ortoprotesia");
                    nomeCursos.add("Ortóptica e Ciências da Visão");
                    nomeCursos.add("Osteopatia");
                    nomeCursos.add("Psicologia");
                    nomeCursos.add("Treino Desportivo");
                    nomeCursos.add("Serviço Social");
                }
                case "cienciaTec" -> {
                    nomeCursos.add("Criminologia");
                    nomeCursos.add("Farmácia");
                    nomeCursos.add("Gestão de Bioindústrias");
                    nomeCursos.add("Optometria e Ciências da Visão");
                    nomeCursos.add("Ortóptica e Ciências da Visão");
                    nomeCursos.add("Prótese Dentária");
                    nomeCursos.add("Química Medicinal");
                    nomeCursos.add("Tecnologias Bioanalíticas");
                    nomeCursos.add("Tecnologia Química");
                }
                case "tecnologiaTec" -> {
                    nomeCursos.add("Informática");
                    nomeCursos.add("Engenharia");
                    nomeCursos.add("Tecnologia");
                }
                case "exterior" -> {
                    nomeCursos.add("Agricultura Biológica");
                    nomeCursos.add("Agronomia");
                    nomeCursos.add("Ciências Agrárias");
                    nomeCursos.add("Ciências do Ambiente");
                    nomeCursos.add("Ecologia e Ambiente");
                    nomeCursos.add("Educação Ambiental");
                    nomeCursos.add("Energia e Ambiente");
                    nomeCursos.add("Educação Ambiental e Turismo de Natureza");
                    nomeCursos.add("Engenharia Agronómica");
                    nomeCursos.add("Engenharia Agro-Pecuária");
                    nomeCursos.add("Engenharia Alimentar");
                    nomeCursos.add("Engenharia do Ambiente");
                    nomeCursos.add("Engenharia do Ambiente e Geoinformática");
                    nomeCursos.add("Engenharia Florestal");
                    nomeCursos.add("Engenharia Florestal e dos Recursos Naturais");
                    nomeCursos.add("Tecnologia e Gestão Ambiental");
                }
                case "negociosTec" -> {
                    nomeCursos.add("Gestão de Negócios");
                    nomeCursos.add("ensino em Inglês)");
                    nomeCursos.add("Gestão de Recursos Humanos");
                    nomeCursos.add("Tecnologia e Gestão Agroindustrial");
                    nomeCursos.add("Gestão do Parimónio");
                    nomeCursos.add("Gestão Turística");
                    nomeCursos.add("Marketing e Comunicação Empresarial");
                    nomeCursos.add("Marketinng e Negócios Internacionais");
                    nomeCursos.add("Publicidade e Marketing");
                    nomeCursos.add("Gestão da Restauração e Catering");
                    nomeCursos.add("Gestão de Marketing");
                }
                case "comunicacao" -> {
                    nomeCursos.add("Administração");
                    nomeCursos.add("Informação");
                    nomeCursos.add("Ciência Política e Relações Internacionais");
                    nomeCursos.add("Comunicação");
                    nomeCursos.add("Direito");
                    nomeCursos.add("Jornalismo");
                    nomeCursos.add("Línguas");
                    nomeCursos.add("Português");
                    nomeCursos.add("Secretariado");
                }
                case "arteTec" -> {
                    nomeCursos.add("Multimédia");
                    nomeCursos.add("Arte e Design");
                    nomeCursos.add("Design");
                    nomeCursos.add("Engenharia Topográfica");
                    nomeCursos.add("Fotografia");
                    nomeCursos.add("Teatro");
                }
                case "servicosTec" -> {
                    nomeCursos.add("Animação");
                    nomeCursos.add("Turismo ");
                    nomeCursos.add("Dietética e Nutrição");
                    nomeCursos.add("Direção e Gestão Hoteleira");
                    nomeCursos.add("Gestão");
                    nomeCursos.add("Segurança do Trabalho e Ambiente");
                    nomeCursos.add("Serviço Social");
                }
            }
        }

        return nomeCursos;
    }

    /**
     * @param nomeCursosList<string> com nome dos cursos
     * @param filtro                 -> filtro de localização
     * @return devolve lista<curso> com todos os cursos
     */
    private List<Curso> getListCursosByName(List<String> nomeCursosList, String filtro) throws FileNotFoundException, CloneNotSupportedException {
        List<Curso> listCursos = new ArrayList<>();

        int flag = ExcelFileReader.loadListaCursos();          //carrega os cursos
        switch (flag) {
            case -1:
                break;
            case -2:
                throw new FileNotFoundException();
            case -3:
                throw new CloneNotSupportedException();
        }


        for (String nomeCurso : nomeCursosList) {             //para cada um dos nomes, obtem ops cursos
            List<Curso> listaAuxiliar;
            if (filtro == null)
                listaAuxiliar = ExcelFileReader.getCursosByNome(nomeCurso);     //devolve lista de cursos com o nome
            else
                listaAuxiliar = ExcelFileReader.getCursosByNomeAndLocalizacao(nomeCurso, filtro);

            assert listaAuxiliar != null;
            for (Curso c : listaAuxiliar)            //adiciona se já não tiver sido adiconado
            {
                if (!listCursos.contains(c))             //se não tiver na lista
                    listCursos.add(c);                     //adiciona o curso
            }
        }

        return listCursos;
    }


    private List<CursoEConfianca> orderCursosByConfianca(List<Curso> listaCursos) {
        List<CursoEConfianca> listaSecundariaCursosEConfianca = new ArrayList<>();
        List<CursoEConfianca> listaCursosEConfianca = new ArrayList<>();
        for (Curso curso : listaCursos)        //cria lista de cursosEConfiançaBaseado na Lista de cursos
        {
            listaSecundariaCursosEConfianca.add(new CursoEConfianca(curso, media));
        }


        //ordena os cursos baseado na sua confiança
        for (CursoEConfianca cEC : listaSecundariaCursosEConfianca) {
            if (cEC.getConfianca() == 60)           //pioritoza cursos com 60 de confiança
                listaCursosEConfianca.add(0, cEC);
            else if (cEC.getConfianca() < 60 && cEC.getConfianca() > 40)    //2a prioridade: 40-60 confiança
            {
                int i;                                      //guarda index do primeiro curso com menor confiança

                for (i = 0; i < listaCursosEConfianca.size(); i++)                          //procura em todos os cursos
                    if (cEC.getConfianca() > listaCursosEConfianca.get(i).getConfianca())   //um que tenha menor confiança que o atual
                        break;                                                               //quando encontra para de procurar
                listaCursosEConfianca.add(i, cEC);                                           //adiciona na posicao de forma a mantern a ordem
            } else {
                listaCursosEConfianca.add(cEC);
            }
        }
        return listaCursosEConfianca;
    }


    /**
     * @param acrescento para a categoria
     * @param cat        o character associado a categoria
     */
    public void addPontosCat(int acrescento, String cat) {
        switch (cat) {
            case "A" -> catA += acrescento;
            case "B" -> catB += acrescento;
            case "C" -> catC += acrescento;
            case "D" -> catD += acrescento;
            case "E" -> catE += acrescento;
            case "F" -> catF += acrescento;
            case "G" -> catG += acrescento;
            case "H" -> catH += acrescento;
            case "I" -> catI += acrescento;
            case "J" -> catJ += acrescento;
            case "K" -> catK += acrescento;
            case "L" -> catL += acrescento;
            case "M" -> catM += acrescento;
            case "N" -> catN += acrescento;
        }
    }

    public int getPontosCat(String cat) {
        //if(cat.equals("N"))
        return switch (cat) {
            case "A" -> catA;
            case "B" -> catB;
            case "C" -> catC;
            case "D" -> catD;
            case "E" -> catE;
            case "F" -> catF;
            case "G" -> catG;
            case "H" -> catH;
            case "I" -> catI;
            case "J" -> catJ;
            case "K" -> catK;
            case "L" -> catL;
            case "M" -> catM;
            default -> catN;
        };
    }

    private List<Curso> getListCursos(List<String> listaNomeCursos) {

        List<Curso> listaCursos = new ArrayList<>();       //cria uma lista que vai guardar todos os cursos

        for (String nomeCurso : listaNomeCursos) {                  //para cada um dos nomes dos cursos

            try {
                //adiciona à lista de cursos todos os cursos encontrados para o nome a procurar no for each
                listaCursos.addAll(Objects.requireNonNull(ExcelFileReader.getCursosByNome(nomeCurso)));

            } catch (NullPointerException e) {      //virtualmente não acontece porque a função estsa preparada para isto. PODE é vir vazia
            }
        }

        return listaCursos;         //devolve lista com todos os cursos
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
