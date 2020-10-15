package com.example.myfirstapp.service;

import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DatabaseService class
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class DatabaseServiceTest {

    private DatabaseService databaseSvc;
    private FirebaseDatabase mockedDatabase;
    private FirebaseDatabase mockedGoodsDatabase;
    private DatabaseReference mockedDatabaseRef;
    private DatabaseReference mockedGoodsDatabaseRef;

    /**
     * Set up for tests, mocks firebase database for local testing
     */
    @Before
    public void before() {
        //users section
        mockedDatabaseRef = Mockito.mock(DatabaseReference.class);

        mockedDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedDatabase.getReference("users")).thenReturn(mockedDatabaseRef);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedDatabase);

        //goods section
        mockedGoodsDatabaseRef = Mockito.mock(DatabaseReference.class);

        mockedGoodsDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedGoodsDatabase.getReference("goods")).thenReturn(mockedGoodsDatabaseRef);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedGoodsDatabase);
    }


    /**
     * Tests that you can save a user to the db
     */
    @Test
    public void writeUser() {
        databaseSvc = new DatabaseService(mockedDatabase);

        User user = new User("John", "Doe", "test@gmail.com", "password");
        databaseSvc.writeUser(user);

        verify(mockedDatabaseRef, times(1)).setValue(user);
    }

    /**
     * Tests that you can save a good to the db
     */
    @Test
    public void writeGood() {
        databaseSvc = new DatabaseService(mockedGoodsDatabase);

        Good good = new Good("art statue", "02-nov-21", "test@gmail.com");
        databaseSvc.writeGood(good);

        verify(mockedGoodsDatabaseRef, times(1)).setValue(good);
    }

}
