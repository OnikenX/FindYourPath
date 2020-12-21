package pt.isec.GPS.FindYourPath.Model.data.XMLReader;

public class Questao {

    private final String pergunta;
    private final String categoria;


    public Questao(String pergunta, String categoria) {
        this.pergunta = pergunta;
        this.categoria = categoria;
    }


    public String getPergunta() {
        return pergunta;
    }

    public String getCategoria() {
        return categoria;
    }
}
