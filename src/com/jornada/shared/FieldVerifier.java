package com.jornada.shared;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class FieldVerifier {

    public static final String INI_SEPARATOR = "<separator>";
    public static final String END_SEPARATOR = "</separator>";

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        } else if (name.contains(INI_SEPARATOR) || name.contains(END_SEPARATOR)) {
            return false;
        }
        return name.length() > 0;
    }

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return password.length() > 3;
    }

    public static boolean isValidEmail(String emailValue) {

        if (emailValue == null)
            return false;

        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

        return emailValue.matches(emailPattern);

    }

    public static boolean isValidListBoxSelectedValue(int value) {
        if (value < 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidInteger(String value) {

        if (value == null) {
            return false;
        } else {
            try {
                Integer.parseInt(value);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

    }

    public static boolean isValidDouble(String value) {

        if (value == null) {
            return false;
        } else {
            try {
                // Integer.parseInt(value);
                Double.parseDouble(value);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public static boolean isValidDoubleOrEmpty(String value){
        if(value==null || value.isEmpty()){
            return true;
        }else if(isValidDouble(value)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isValidGrade(String value) {

        if (isValidDouble(value)) {

            double double1 = Double.parseDouble(value);
            if (double1 >= 0 && double1 <= 10) {
                return true;
            } else {
                return false;
            }

        }else if(value==null || value.isEmpty()){
            return true;
        }else {        
            return false;
        }
    }

    public static boolean isValidDate(String value) {
        if (value == null) {
            return false;
        } else {
            try {
                @SuppressWarnings("unused")
                Date dateCheck = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL).parseStrict(value);
                return true;
            } catch (Exception ex) {
                return false;
            }

        }

    }

    public static boolean isNumeric(String str) {
        try {
            @SuppressWarnings("unused")
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String cleanSeparator(String textWithSeparator) {
        String cleanedText = textWithSeparator.replace(INI_SEPARATOR, "");
        cleanedText = cleanedText.replace(END_SEPARATOR, "");
        return cleanedText;
    }

}
