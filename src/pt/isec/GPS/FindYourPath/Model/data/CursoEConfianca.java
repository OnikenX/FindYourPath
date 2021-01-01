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
        media /=10;
        double difMedia = media-(curso.getMedia()/10);
        //normalização
        difMedia /=10.5;
        difMedia*=100;

        //pssagem para [-50, 50]
        difMedia /=2;

        //obtem resultado;
        return 50 + difMedia;
    }


    public Curso getCurso() {
        return curso;
    }

    public double getConfianca() {
        return confianca;
    }
}
