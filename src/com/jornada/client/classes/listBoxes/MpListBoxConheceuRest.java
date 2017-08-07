package com.jornada.client.classes.listBoxes;

import com.jornada.shared.classes.pesquisasatisfacao.Avaliacao;



public class MpListBoxConheceuRest extends MpListBox {
    

    
    public MpListBoxConheceuRest(){
        
        
        setStyleName("design_list_boxes_avaliacao");
        addItem(Avaliacao.STR_INTERNET, Avaliacao.STR_INTERNET);        
        addItem(Avaliacao.STR_AMIGOS_FAMILIARES, Avaliacao.STR_AMIGOS_FAMILIARES);
        addItem(Avaliacao.STR_JORNAL, Avaliacao.STR_JORNAL);
        addItem(Avaliacao.STR_OUTROS, Avaliacao.STR_OUTROS);
        
        
        this.setMultipleSelect(false);
        this.setVisibleItemCount(4);
        this.setSize("180px", "100px");

    }
    
}