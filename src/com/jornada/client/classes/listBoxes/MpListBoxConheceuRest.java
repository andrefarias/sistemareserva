package com.jornada.client.classes.listBoxes;



public class MpListBoxConheceuRest extends MpListBox {
    
    public MpListBoxConheceuRest(){
        
        
        setStyleName("design_list_boxes_avaliacao");
        addItem("Internet","Internet");        
        addItem("Amigos/Familiares","Amigos/Familiares");
        addItem("Jornal","Jornal");
        addItem("Outros","Outros");
        
//        addItem("Ruim","Ruim");
        
        this.setMultipleSelect(false);
        this.setVisibleItemCount(4);
        this.setSize("180px", "100px");

    }
    
}