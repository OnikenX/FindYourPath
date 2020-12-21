package pt.isec.GPS.FindYourPath.Model.data.excelreader;

public class Curso {

    private final String nome;
    private final String Universidade;
    private final double media;


    public Curso(String nome, String universidade, double media) {
        this.nome = nome;
        Universidade = universidade;
        this.media = media;
    }


    public String getNome() {
        return nome;
    }

    public String getUniversidade() {
        return Universidade;
    }

    public double getMedia() {
        return media;
    }
}
