package com.jornada.client.classes.listBoxes;

import com.jornada.client.classes.listBoxes.MpListBox;


public class MpListBoxSimNao extends MpListBox {
    
    public MpListBoxSimNao(){
        
        setStyleName("design_list_boxes_avaliacao");
        
        addItem("Sim","Sim");
        addItem("Não","Não");
        addItem("Talvez","Talvez");
        
        this.setMultipleSelect(false);
        this.setVisibleItemCount(3);
        this.setSize("180px", "100px");
        
    }
    
}