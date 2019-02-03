package com.gamitology.kevent.kevent.constTest;

import com.gamitology.kevent.kevent.constant.DateFormatConstant;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@SpringBootTest
public class DateFormatTest {

    @Test
    public void dateFromatTest() throws ParseException {
//        String dateText = "2019-02-28T15:59:00.000Z";
        String dateText = "2019-02-28";
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date d = format.parse(dateText);

        System.out.println(d.toString());

        d = Date.from(Instant.parse( "2019-02-28T15:59:00.000Z" ));
        System.out.println(d.toString());
    }

}
