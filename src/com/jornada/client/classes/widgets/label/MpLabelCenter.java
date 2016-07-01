package com.jornada.client.classes.widgets.label;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class MpLabelCenter extends MpLabel{
	
	public MpLabelCenter(String text){
		setText(text);
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);		
	}

}
