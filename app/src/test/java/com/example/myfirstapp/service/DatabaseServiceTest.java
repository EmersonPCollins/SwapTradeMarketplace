package com.example.myfirstapp.service;

import com.example.myfirstapp.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
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
    private DatabaseReference mockedDatabaseRef;

    /**
     * Set up for tests, mocks firebase database for local testing
     */
    @Before
    public void before() {
        mockedDatabaseRef = Mockito.mock(DatabaseReference.class);

        mockedDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedDatabase.getReference("users")).thenReturn(mockedDatabaseRef);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedDatabase);
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
     * Tests that you can read a user from db
     */
    @Test
    public void readUser() {

        when(mockedDatabaseRef.child(anyString())).thenReturn(mockedDatabaseRef);
        databaseSvc = new DatabaseService(mockedDatabase);

        User user = new User("John", "Doe", "test@gmail.com", "password");
        final User mockedUser = new User("John", "Doe", "test@gmail.com", "password");

        doAnswer(new Answer() {
                     @Override
                     public Object answer(InvocationOnMock invocation) throws Throwable {

                         ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                         DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                         when(mockedDataSnapshot.getValue(User.class)).thenReturn(mockedUser);
                         valueEventListener.onDataChange(mockedDataSnapshot);

                         return null;
                     }
                 }).when(mockedDatabaseRef).addValueEventListener(any(ValueEventListener.class));

        assertEquals(databaseSvc.readUser("test@gmail.com"), user);

    }

}
