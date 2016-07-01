package com.jornada.client.classes.widgets.multibox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ListItem extends ComplexPanel implements HasText, HasHTML, HasClickHandlers, HasKeyDownHandlers, HasBlurHandlers {
	HandlerRegistration clickHandler;

    @SuppressWarnings("deprecation")
    public ListItem() {
        setElement(DOM.createElement("LI"));
    }

    @SuppressWarnings("deprecation")
    public void add(Widget w) {
        super.add(w, getElement());
    }

    @SuppressWarnings("deprecation")
    public void insert(Widget w, int beforeIndex) {
        super.insert(w, getElement(), beforeIndex, true);
    }

    @SuppressWarnings("deprecation")
    public String getText() {
        return DOM.getInnerText(getElement());
    }

    @SuppressWarnings("deprecation")
    public void setText(String text) {
        DOM.setInnerText(getElement(), (text == null) ? "" : text);
    }

    @SuppressWarnings("deprecation")
    public void setId(String id) {
        DOM.setElementAttribute(getElement(), "id", id);
    }

    @SuppressWarnings("deprecation")
    public String getHTML() {
        return DOM.getInnerHTML(getElement());
    }

    @SuppressWarnings("deprecation")
    public void setHTML(String html) {
        DOM.setInnerHTML(getElement(), (html == null) ? "" : html);
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
        return addDomHandler(handler, KeyDownEvent.getType());
    }

    public HandlerRegistration addBlurHandler(BlurHandler handler) {
        return addDomHandler(handler, BlurEvent.getType());
    }
}
