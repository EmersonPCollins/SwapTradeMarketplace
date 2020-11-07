package com.example.myfirstapp.service;

import com.example.myfirstapp.domain.Good;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class GoodsUnitTest {
    @Test
    public void isExpiredWorks(){
        String today = LocalDate.now().toString();
        Good testGood = new Good("title", today,"2021-05-11","description","location","email");
        assertEquals(false, testGood.isExpired());
        Good testGood2 = new Good("title", today,"2002-05-11","description","location","email");
        assertEquals(true, testGood2.isExpired());
    }
}
