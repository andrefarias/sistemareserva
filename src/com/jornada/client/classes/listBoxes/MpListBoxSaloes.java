package com.jornada.client.classes.listBoxes;

import com.jornada.shared.classes.salao.Saloes;


public class MpListBoxSaloes extends MpSelection {   

    
    public MpListBoxSaloes() {

        Saloes object = new Saloes();

        addItem(object.getSalaoInterno().getNomeSalao(), object.getSalaoInterno().getNomeSalao());
        addItem(object.getSalaoExternoAberto().getNomeSalao(), object.getSalaoExternoAberto().getNomeSalao());
        addItem(object.getSalaoExternoCoberto().getNomeSalao(), object.getSalaoExternoCoberto().getNomeSalao());
        addItem(object.getSalaoChurrasqueira().getNomeSalao(), object.getSalaoChurrasqueira().getNomeSalao());

        setVisibleItemCount(1);

    }
    

    
}