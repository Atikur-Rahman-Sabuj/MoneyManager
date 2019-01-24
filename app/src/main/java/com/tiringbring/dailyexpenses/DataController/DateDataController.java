package com.tiringbring.dailyexpenses.DataController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDataController {
    public Date CropTimeFromDate(Calendar calendar){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = formatter.parse(formatter.format(calendar.getTime()));
        }catch (Exception ex){
            date = null;
        }
        return  date;

    }
}
