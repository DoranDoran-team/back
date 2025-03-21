package com.korit.dorandoran.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodayCreator {

    public static String todayCreator(){
        
        LocalDateTime today = LocalDateTime.now(); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = today.format(formatter);


        return formatted;
    }
}
