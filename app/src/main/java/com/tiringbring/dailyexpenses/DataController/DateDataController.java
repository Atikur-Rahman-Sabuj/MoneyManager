package com.tiringbring.dailyexpenses.DataController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDataController {
    private  SimpleDateFormat formatter;

    public DateDataController() {
        this.formatter = new SimpleDateFormat("dd/MM/yyyy");
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
    public  String DatetoString(Date date){
        SimpleDateFormat newFormatter = new SimpleDateFormat("dd/MM");
        return newFormatter.format(date);
    }
}
