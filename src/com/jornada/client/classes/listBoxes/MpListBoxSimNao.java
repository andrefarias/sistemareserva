package com.jornada.client.classes.listBoxes;

import com.jornada.client.classes.listBoxes.MpListBox;
import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;


public class MpListBoxSimNao extends MpListBox {
    
    public MpListBoxSimNao(){
        
        setStyleName("design_list_boxes_avaliacao");
        
        addItem(Avaliacao.STR_SIM,Avaliacao.STR_SIM);
        addItem(Avaliacao.STR_NAO,Avaliacao.STR_NAO);
        addItem(Avaliacao.STR_TALVEZ,Avaliacao.STR_TALVEZ);
        
        this.setMultipleSelect(false);
        this.setVisibleItemCount(3);
        this.setSize("180px", "100px");
        
    }
    
}