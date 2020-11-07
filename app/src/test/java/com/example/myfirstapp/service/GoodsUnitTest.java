package com.example.myfirstapp.service;

import com.example.myfirstapp.domain.Good;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class GoodsUnitTest {
    @Test
    public void isExpiredWorks(){
        String date = "2020-06-11";
        LocalDate ldate = LocalDate.of(2150,12,9);
        Good testGood = new Good("title","2020-05-11","description","location","email");
        assertEquals(ldate, testGood.isExpired("2150-12-09"));
    }
}
