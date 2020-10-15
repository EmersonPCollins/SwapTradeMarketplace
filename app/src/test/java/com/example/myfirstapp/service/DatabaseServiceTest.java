package com.example.myfirstapp.service;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for DatabaseService class
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
class DatabaseServiceTest {

    /**
     * Tests that you can save a user to the db
     */
    @Test
    public void writeUser() {
        databaseSvc = new DatabaseService();

        User user = new User("John", "Doe", "test@gmail.com", "password");
        databaseSvc.writeUser(user);

        verify(mockedDatabaseRef, times(1)).setValue(user);
    }

}
