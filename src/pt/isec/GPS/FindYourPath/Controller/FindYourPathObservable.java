package pt.isec.GPS.FindYourPath.Controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import pt.isec.GPS.FindYourPath.Model.FindYourPathModel;

public class FindYourPathObservable implements Serializable {
    //versao serializada para guardar estado
    //private static final long serialVersionUID = 1L;

    //support para listener de eventos
    private final PropertyChangeSupport propertyChangeSupport;

    //modelo
    private FindYourPathModel findYourPathModel;

    public FindYourPathObservable(FindYourPathModel findYourPathModel) {
        this.findYourPathModel = findYourPathModel;
        propertyChangeSupport = new PropertyChangeSupport(findYourPathModel);
    }


    //metodos de listening
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }


    ////delegacoes do model
    //gets

    //sets


}