package com.tiringbring.dailyexpenses.DataController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDataController {
    private  SimpleDateFormat formatter;

    public DateDataController() {
        this.formatter = new SimpleDateFormat("dd/MM/yyyy");
    }

    public int DateToDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar.get(Calendar.DAY_OF_MONTH);
    }
    public int DateToMonthNo(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar.get(Calendar.MONTH);
    }
    public int DateToYearNo(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar.get(Calendar.YEAR);
    }


    public Date CropTimeFromDate(Calendar calendar){
        Date date;
        try {
            date = formatter.parse(formatter.format(calendar.getTime()));
        }catch (Exception ex){
            date = null;
        }
        return  date;

    }
    public  Date StringToDate(String dateString){
        try {
            Date date = formatter.parse(dateString);
            return date;
        }catch (Exception ex){
            return null;
        }

    }
    public String DatetoDateMonthBigYear(Date date){
        return formatter.format(date);
    }
    public  String DatetoDateMonthYear(Date date){
        SimpleDateFormat newFormatter = new SimpleDateFormat("dd/MM/yy");
        return newFormatter.format(date);
    }
    public  String DatetoBigDateMonthYear(Date date){
        SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMMM, yyyy");
        return newFormatter.format(date);
    }
    public  String DatetoString(Date date){
        SimpleDateFormat newFormatter = new SimpleDateFormat("dd/MM");
        return newFormatter.format(date);
    }
    public String DateToMonthYear(Date date){
        SimpleDateFormat newFormatter = new SimpleDateFormat("MMM yy");
        return newFormatter.format(date);
    }
    public String DateToYear(Date date){
        SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy");
        return newFormatter.format(date);
    }
    public String DateToMonth(Date date){
        SimpleDateFormat newFormatter = new SimpleDateFormat("MMM");
        return newFormatter.format(date);
    }
    public String DateToDate(Date date){
        SimpleDateFormat newFormatter = new SimpleDateFormat("dd EEE");
        return newFormatter.format(date);
    }
}
