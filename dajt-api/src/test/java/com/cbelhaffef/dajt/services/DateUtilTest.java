package com.cbelhaffef.dajt.services;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtilTest {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void shouldExtractDateFromString() throws ParseException {
        String text = "عمليـة إرهابيـة بتاريخ 03/04/2012";
        Date date = extractDateFromString(text);
        Assert.assertEquals(date,formatter.parse("03/04/2012"));
    }

    public Date extractDateFromString(String text) throws ParseException {

        String regex = "\\d{2}/\\d{2}/\\d{4}";
        Matcher m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
            return formatter.parse(m.group(0));
        }
        return null;
    }

}
