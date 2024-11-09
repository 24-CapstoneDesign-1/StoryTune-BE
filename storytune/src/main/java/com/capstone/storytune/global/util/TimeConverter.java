package com.capstone.storytune.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter {
    public static String transferLocalDateTime(LocalDateTime time){
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
