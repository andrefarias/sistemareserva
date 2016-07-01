package com.jornada.client.classes.listBoxes;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.Turno;


public class MpListBoxTurno extends MpSelection {
    
    public MpListBoxTurno(){
        
        addItem(Turno.ALMOCO,Turno.ALMOCO);
        addItem(Turno.TARDE,Turno.TARDE);
        addItem(Turno.JANTAR,Turno.JANTAR);
        
    }
    
}