package pt.isec.GPS.FindYourPath.Model.data.excelreader;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pt.isec.GPS.FindYourPath.Model.FindYourPathModel;
import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ExcelFileReader {
    private static boolean load = false;        //false: ainda não fez load.. true: já fez load
    private static final List<Curso> listaCursos = new ArrayList<>();

    private ExcelFileReader() {
    }

    //faz o load das coisas da folha Excel para a Lista listaCursos
    //return -1 -> já fez load
    //return -2 -> File não encontrado
    //return -3 -> Problema a criar instancia workbook
    //return normal: return numero de cursos que leu
    public static int loadListaCursos() {
        if (load)       //verifica se o load já foi feito
            return -1;   //devolve -1 se ja tiver sido feito


        //ainda não fez load

        load = true;   //indica que já fez load
        //warning: caso haja erro a ler da primeira vez pode não cnseguir fazer de novo, mesmo sabendo que ainda não "leu" realmente

        //abertura do excel
        FileInputStream fis;
        HSSFWorkbook wb;

        //Obtem o file
        try {
            fis = new FileInputStream(new File(FindYourPathData.ExcelFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -2;
        }

        //cria workbook referente ao file .xls file
        try {
            wb = new HSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return -3;
        }

        //cria a folha excel
        HSSFSheet sheet = wb.getSheetAt(0);
        //indica o tipo da celula
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

        //COLUNAS:
        //A -> 0        vazio
        //D-> 3         Nome instituição
        //E-> 4         Nome curso
        //I-> 8         NOTA ULTIMO COLOCADO
        //LINHAS:
        //começa na 6ª
        int linha = 0;
        for (Row row : sheet) {            //passa por todas as linhas

            linha++;
            if (linha - 1 < 6)                //procura a primeira linha com os dados
                continue;

            String curso = null;
            String instituicao = null;
            double media = -1;

            Cell cell;
            for (int coluna = 0; coluna < 9; coluna++) {

                if (coluna == 3)           //String
                {
                    cell = row.getCell(coluna);
                    instituicao = cell.getStringCellValue();
                } else if (coluna == 4)       //String
                {          //String
                    cell = row.getCell(coluna);
                    curso = cell.getStringCellValue();
                } else if (coluna == 8)       //float
                {
                    cell = row.getCell(coluna);
                    media = cell.getNumericCellValue();
                }
            }

            listaCursos.add(new Curso(curso, instituicao, media));
        }
        return listaCursos.size();
    }

    //Recebe nome do curso, devolve lista com cursos que contêm esse nome (NÃO É CASE SENSITIVE)
    //return null-> a lista ainda não foi loaded
    //return list.size = 0 -> não há cursos com esse nome
    //return list -> lista com cursos que contêm o nome recebido-> ex: recebe "ENGENHARIA"-> devolve todos os cursos com "engenharia" (NÃO É CASE SENSITIVE)
    public static List<Curso> getCursosByNome(String nome) {
        if (!load)                   //lista ainda nao foi loaded
            return null;            //return null

        ArrayList<Curso> listaCursosComNomePedido = new ArrayList<>(); //inicializa lista (mesmo que não haja cursos, não devolve null)

        for (Curso curso : listaCursos) {                                         //para todos os cursos da lista
            if (curso.getNome().toLowerCase().contains(nome.toLowerCase()))      //vcerifica se o seu nome contem o nome colocado
                listaCursosComNomePedido.add(curso);                            //se contiver o nome, coloca na lista
        }

        return listaCursosComNomePedido;                                        //devolve "nova" lista com cursos.
    }


    //Recebe o nome do curso e localização do instituto
    //return null-> a lista ainda não foi loaded
    //return list.size = 0 -> não há cursos com esse nome
    //return list -> lista com cursos que contêm o nome recebido e localização indicada
    public static List<Curso> getCursosByNomeAndLocalizacao(String nome, String localizacao) {
        if (!load)                   //lista ainda nao foi loaded
            return null;            //return null

        ArrayList<Curso> listaCursosComNomePedido = new ArrayList<>(); //inicializa lista (mesmo que não haja cursos, não devolve null)

        for (Curso curso : listaCursos) {                                         //para todos os cursos da lista
            if (curso.getNome().toLowerCase().contains(nome.toLowerCase()))      //vcerifica se o seu nome contem o nome colocado
                listaCursosComNomePedido.add(curso);                            //se contiver o nome, coloca na lista
        }

        if (listaCursosComNomePedido.size() == 0)                                  //caso esteja vazio
            return listaCursosComNomePedido;                                       //devolve "nova" lista com cursos, nao vale a ena filtrar mais


        ArrayList<Curso> listaCursosComNomeELocalPedido = new ArrayList<>(); //inicializa lista (mesmo que não haja cursos, não devolve null)
        
        for (Curso curso : listaCursosComNomePedido) {                                         //para todos os cursos da lista
            if (curso.getUniversidade().toLowerCase().contains(localizacao.toLowerCase()))      //vcerifica se o seu nome contem o nome colocado
                listaCursosComNomeELocalPedido.add(curso);                            //se contiver o nome, coloca na lista
        }

        return listaCursosComNomeELocalPedido;
    }

        /**
    serve para escrever o ficheiro que guarda os resultados
    recebe uma lista de cursos que contem os resultados
    devolve o que representa o ficheiro .XLS --https://howtodoinjava.com/java/library/readingwriting-excel-files-in-java-poi-tutorial/
    para escrever o ficheiro:
    /*
     try
        {
            XSSFWorkbook workbook = guardaListaDeCursosResultada(lista);
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(localização fo ficheiro-> obter do mvc));
            workbook.write(out);
            out.close();
            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
     */
    public static XSSFWorkbook guardaListaDeCursosResultada(List<Curso> listaCursos){

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Resultados");

        //coloca as colunas iniciais
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("pt.isec.GPS.FindYourPath.Model.data.excelreader.Curso");
        cell = row.createCell(1);
        cell.setCellValue("Instituto");
        cell = row.createCell(2);
        cell.setCellValue("Média Último Classificado");

        int linha = 1;
        for(Curso curso : listaCursos){
            row = sheet.createRow(linha);
            cell = row.createCell(0);
            cell.setCellValue(curso.getNome());
            cell = row.createCell(1);
            cell.setCellValue(curso.getUniversidade());
            cell = row.createCell(2);
            cell.setCellValue(curso.getMedia());
            linha++;
        }
        return workbook;
    }


}
