package pt.isec.GPS.FindYourPath.Model.estados;

import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;

import java.io.FileNotFoundException;

public class NoEcraDeResultados extends EstadoAdapter{
    public NoEcraDeResultados(FindYourPathData findYourPathData) {
        super(findYourPathData);
    }

    //TODO: implementar o novo teste
    @Override
    public IEstado novoTeste() {
        getFindYourPathData().resetTeste();
        return new NoEcraDeComeco(getFindYourPathData());
    }
}
