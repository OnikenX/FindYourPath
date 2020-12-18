package pt.isec.GPS.FindYourPath.Model.estados;

import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;

public class EstadoAdapter implements IEstado{
    private final FindYourPathData findYourPathData;

    public EstadoAdapter(FindYourPathData findYourPathData){
        this.findYourPathData = findYourPathData;
    }

    protected FindYourPathData getFindYourPathData() {
        return findYourPathData;
    }

    @Override
    public IEstado comecarTeste(int media) {
        return this;
    }

    @Override
    public IEstado selecionarResposta(int nDaResposta) {
        return this;
    }

    @Override
    public IEstado novoTeste() {
        return this;
    }
}
