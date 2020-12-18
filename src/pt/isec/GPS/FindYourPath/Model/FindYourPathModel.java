package pt.isec.GPS.FindYourPath.Model;

import pt.isec.GPS.FindYourPath.Model.data.FindYourPathData;
import pt.isec.GPS.FindYourPath.Model.estados.IEstado;
import pt.isec.GPS.FindYourPath.Model.estados.NoEcraDeComeco;

public class FindYourPathModel {
    private IEstado estado;
    private final FindYourPathData findYourPathData;


    public FindYourPathModel() {
        findYourPathData = new FindYourPathData();
        estado = new NoEcraDeComeco(findYourPathData);
    }
}
