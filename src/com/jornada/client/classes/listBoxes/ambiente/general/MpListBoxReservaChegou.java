package com.jornada.client.classes.listBoxes.ambiente.general;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.Turno;


public class MpListBoxReservaChegou extends MpSelection {
    
    Turno turno;
    
    public MpListBoxReservaChegou(){
        
        addItem("Sim","Sim");
        addItem("Não","Não");
//        addItem("Saiu","Saiu");
        
    }
    
}