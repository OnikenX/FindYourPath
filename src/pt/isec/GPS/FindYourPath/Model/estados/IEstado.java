package pt.isec.GPS.FindYourPath.Model.estados;

public interface IEstado {
    IEstado comecarTeste(int media);
    IEstado selecionarResposta(int nDaResposta);
    IEstado novoTeste();
}
