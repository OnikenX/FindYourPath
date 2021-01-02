package pt.isec.GPS.FindYourPath.Model.estados;

import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;

public class NoEcraDeComeco extends EstadoAdapter {
    public NoEcraDeComeco(FindYourPathData findYourPathModel) {
        super(findYourPathModel);
    }

    @Override
    public IEstado comecarTeste(String media) throws Exception {
        if(getFindYourPathData().setMedia(media))
            return new NoTeste(getFindYourPathData());
        return this;
    }

}
