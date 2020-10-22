
package com.example.myfirstapp.service;

import com.example.myfirstapp.domain.Good;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class DatabaseServiceTest {

    private DatabaseService databaseSvc;
    private FirebaseDatabase mockedDatabase;
    private FirebaseDatabase mockedGoodsDatabase;
    private DatabaseReference mockedDatabaseRef;
    private DatabaseReference mockedGoodsDatabaseRef;


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



    @Test
    public void writeUser() {
        databaseSvc = new DatabaseService(mockedDatabase);

        User user = new User("John", "Doe", "test@gmail.com", "password");
        databaseSvc.writeUser(user);

        verify(mockedDatabaseRef, times(1)).setValue(user);
    }


    @Test
    public void writeGood() {
        databaseSvc = new DatabaseService(mockedGoodsDatabase);

        Good good = new Good("art statue", "02-nov-21", "test@gmail.com");
        databaseSvc.writeGood(good);

        verify(mockedGoodsDatabaseRef, times(1)).setValue(good);
    }


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

        User resultUser = databaseSvc.readUser("test@gmail.com");
        assertEquals(resultUser.getEmail(), user.getEmail());
        assertEquals(resultUser.getFirstName(), user.getFirstName());
        assertEquals(resultUser.getLastName(), user.getLastName());
        assertEquals(resultUser.getPassword(), user.getPassword());

    }


    @Test
    public void userExists() {

        when(mockedDatabaseRef.child(anyString())).thenReturn(mockedDatabaseRef);
        databaseSvc = new DatabaseService(mockedDatabase);

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

        assertTrue(databaseSvc.userExists("test@gmail.com", "password"));
    }


    @Test
    public void failedEmailUserExists() {

        when(mockedDatabaseRef.child(anyString())).thenReturn(mockedDatabaseRef);
        databaseSvc = new DatabaseService(mockedDatabase);

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

        System.out.println(databaseSvc.readUser("nope@gmail.com").getEmail());
        assertFalse(databaseSvc.userExists("nope@gmail.com", "password"));

    }


    @Test
    public void failedPasswordUserExists() {

        when(mockedDatabaseRef.child(anyString())).thenReturn(mockedDatabaseRef);
        databaseSvc = new DatabaseService(mockedDatabase);

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

        assertFalse(databaseSvc.userExists("test@gmail.com", "failed"));
    }
}
