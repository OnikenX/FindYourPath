package pt.isec.GPS.FindYourPath.Model.estados;

import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;

public class NoTeste extends EstadoAdapter{
    public NoTeste(FindYourPathData findYourPathData) {
        super(findYourPathData);
    }

    //TODO: implementar selecionar resposta
    @Override
    public IEstado selecionarResposta(int nDaResposta) {
        return super.selecionarResposta(nDaResposta);
    }
}
