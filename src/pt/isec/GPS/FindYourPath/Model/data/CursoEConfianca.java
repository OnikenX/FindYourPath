package pt.isec.GPS.FindYourPath.Model.data;
import pt.isec.GPS.FindYourPath.Model.data.excelreader.Curso;

public class CursoEConfianca {

    private final Curso curso;
    private final double confianca;


    public CursoEConfianca(Curso curso, double confianca) {
        this.curso = curso;
        this.confianca = confianca;
    }


    public Curso getCurso() {
        return curso;
    }

    public double getConfianca() {
        return confianca;
    }
}
