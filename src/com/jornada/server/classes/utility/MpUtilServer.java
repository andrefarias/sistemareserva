package com.jornada.server.classes.utility;

import java.io.File;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.jornada.shared.utility.MpUtilShared;

public class MpUtilServer {
	
	public static final String FORMAT_HOUR="HH:mm a";
	public static final String FORMAT_DATE="yyyy MMM,dd";
	
	public static final String STATUS_TRUE="true";
	
	
	public static String convertDateToString_old_old(Date date, String strLocale){
		String arraylocale[];
		Locale LOCALE;
		
		if(strLocale.contains("_")){
			arraylocale = strLocale.split("_");
			LOCALE = new Locale(arraylocale[0],arraylocale[1]);  
		}else{
			LOCALE = new Locale(strLocale);  
		}
		 
		DateFormat df = new SimpleDateFormat(FORMAT_DATE, LOCALE);	
		
		String reportDate = df.format(date);		
		return reportDate;
	}
	
	public static String convertDateToString(Date date){

		DateFormat df = new SimpleDateFormat(FORMAT_DATE);			
		String reportDate = df.format(date);		
		return reportDate;
	}
	
	   public static String convertDateToString(Date date, String formatDate){

	        DateFormat df = new SimpleDateFormat(formatDate);          
	        String reportDate = df.format(date);        
	        return reportDate;
	    }
	
	
	public static String convertTimeToString(Time time){
		DateFormat df = new SimpleDateFormat(FORMAT_HOUR);	
		Date newDate = new Date();
		newDate.setTime(time.getTime());
		String reportDate = df.format(newDate);		
		return reportDate;
	}
	
	
	public static boolean renameFile(String oldFileAddress, String newFileAddress){
		
		boolean isRenamed;
		
		File oldfile =new File(oldFileAddress);
		File newfile =new File(newFileAddress);
 
		if(oldfile.renameTo(newfile)){
			isRenamed=true;
			System.out.println("Rename succesful");
		}else{
			isRenamed=false;
			System.out.println("Rename failed");
		}	
		
		return isRenamed;
		
	}


	public static Date convertStringToDate(String strDate){
		Calendar mydate = new GregorianCalendar();
		try{		
		
		Date thedate = new SimpleDateFormat(FORMAT_DATE, Locale.ENGLISH).parse(strDate);
		mydate.setTime(thedate);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return mydate.getTime();
	}
	
	public static Date convertStringToDate(String strDate, String formatDate) {
		Calendar mydate = new GregorianCalendar();
		try {

			Date thedate = new SimpleDateFormat(formatDate, Locale.ENGLISH).parse(strDate);
			mydate.setTime(thedate);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		return mydate.getTime();
	}	
	
	public static java.sql.Date convertStringToSqlDate(String strDate) {
		try {
			Date data = MpUtilServer.convertStringToDate(strDate);
			return new java.sql.Date(data.getTime());
		} catch (Exception ex) {
			Date date = new Date();
			return new java.sql.Date(date.getTime());
		}
	}
	
	public static java.sql.Time convertStringToSqlTime(String strDate, boolean isAdicionar){
		
	    String mask;
	    if(isAdicionar){
	        mask="hh:mm a";
	    }else{
	        mask="hh:mm";
	    }
	        
		try {
//			DateFormat formatter = new SimpleDateFormat("hh:mm a");
		    DateFormat formatter = new SimpleDateFormat(mask);
			java.sql.Time timeValue = new java.sql.Time(formatter.parse(strDate).getTime());
			return timeValue;
		} catch (Exception ex) {
			return null;
		}		
		
	}

	public static String isThisDateValid(String dateToValidate, String dateFromat) {

		Locale locale = new Locale("pt", "BR");

		if (dateToValidate == null) {
			return "null";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat, locale);
		sdf.setLenient(false);

		try {

			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {
			// e.printStackTrace();
			// return false;
			return e.getMessage();
		}

		return "true";
	}

    public static ArrayList<String> getMaxTestCaseQueryRowsAllowed(ArrayList<String> strArray, int maxValuesOnQuery) {

        

        ArrayList<String> arrayDest = new ArrayList<String>();

        StringBuilder strBuffer = new StringBuilder();

        int counter = 0;

        int i = 0;
        while (i < strArray.size()) {

            if (strBuffer.length() == 0) {
                strBuffer.append((String) strArray.get(i));
            } else {
                strBuffer.append(",");
                strBuffer.append((String) strArray.get(i));
            }

            counter++;
            if (counter == maxValuesOnQuery) {
                counter = 0;
                arrayDest.add(strBuffer.toString());
                strBuffer.setLength(0);
            }

            i++;
            if (i == strArray.size()) {
                arrayDest.add(strBuffer.toString());
            }

        }

        return arrayDest;

    }
    
    public static String getDecimalFormatedOneDecimalMultipleFive(double doubleNumber){
        
        
        return MpUtilShared.getDecimalFormatedOneDecimalMultipleFive(doubleNumber);
//        String strNumber="";
//        int parteInteira = (int)doubleNumber;
//        int parteDecimal = (int)Math.round((doubleNumber - (int)doubleNumber) * 100);
//        
//        if(parteDecimal>=0 && parteDecimal<=25){
//            strNumber = Integer.toString(parteInteira);
//        }else if(parteDecimal>=25 && parteDecimal<=74){
//            double doubleTemp = ((double)parteInteira) + 0.5;
//            strNumber = Double.toString(doubleTemp);
//        }else if(parteDecimal>=75 && parteDecimal<=99){
//            parteInteira++;
//            strNumber = Integer.toString(parteInteira);
//        }
//        
//        return strNumber;

    }
    
    public static String getDecimalFormatedOneDecimal(double doubleNumber){
        DecimalFormat oneDigit = new DecimalFormat("0.#");
        String strDouble = oneDigit.format(doubleNumber).replace(",", ".");
        return strDouble;
    }
    
    public static String getDecimalFormatedTwoDecimal(double doubleNumber){
        DecimalFormat oneDigit = new DecimalFormat("0.##");
        String strDouble = oneDigit.format(doubleNumber).replace(",", ".");
        return strDouble;
    }
    
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        @SuppressWarnings("unused")
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }

}
