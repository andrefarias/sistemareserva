package com.jornada.client.classes.listBoxes.suggestbox;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;

public class MpSuggestBox extends SuggestBox{
    
    public MpSuggestBox(){
        this.setStyleName("design_text_boxes_avaliacao");
    }
    
    public MpSuggestBox(MultiWordSuggestOracle oracle){
        super(oracle);
        this.setStyleName("design_text_boxes_avaliacao");
    }

}
