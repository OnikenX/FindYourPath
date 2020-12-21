package pt.isec.GPS.FindYourPath.Model.data;

import pt.isec.GPS.FindYourPath.Model.data.excelreader.Curso;

public class CursoEConfianca {

    private final Curso curso;
    private final double confianca;


    public CursoEConfianca(Curso curso, double media) {
        this.curso = curso;
        this.confianca = setConfiancaWithMedia(media);
    }

    private double setConfiancaWithMedia(double media) {
        return (50 + /*((media - curso.getMedia()) * */ ((media - curso.getMedia()) / 20)) ;   //normalização para 100%
    }


    public Curso getCurso() {
        return curso;
    }

    public double getConfianca() {
        return confianca;
    }
}
