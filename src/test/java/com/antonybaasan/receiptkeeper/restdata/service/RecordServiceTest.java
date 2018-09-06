package com.antonybaasan.receiptkeeper.restdata.service;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.model.UserProfile;
import com.antonybaasan.receiptkeeper.restdata.repositories.RecordRepository;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import com.antonybaasan.receiptkeeper.restdata.security.FbUserInfo;
import com.antonybaasan.receiptkeeper.restdata.serviceimpl.RecordServiceImpl;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.antonybaasan.receiptkeeper.restdata.TestUtils.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceTest {

    RecordServiceImpl recordService;

    @Mock
    RecordRepository repositoryMock;

    @Mock
    AuthFacade authMock;

    @Mock
    UserProfileService userProfileService;

    FbUserInfo currentUser;
    UserProfile currentUserProfile;

    @Before
    public void setUp() {
        recordService = new RecordServiceImpl(repositoryMock, authMock, userProfileService);
        currentUser = new FbUserInfo("1", "ant", "", "");
        currentUserProfile = createUserProfile(1);
        when(authMock.getUser()).thenReturn(currentUser);
        when(userProfileService.getUserById(currentUser.getUid())).thenReturn(currentUserProfile);
    }

    @Test
    public void create_Success() {
        Record record = new Record();
        recordService.create(record);
        verify(this.repositoryMock,
                times(1)).save(record);
    }

    @Test
    public void create_SetOwner_Success() {
        Record record = new Record();
        recordService.create(record);
        Assert.assertEquals(record.getOwner(), currentUserProfile);
    }

    @Test
    public void update_Success() throws NotFoundException, IllegalAccessException {
        Record record = new Record();
        record.setOwner(currentUserProfile);
        when(repositoryMock.findById(record.getId())).thenReturn(Optional.of(record));

        recordService.update(record);

        verify(this.repositoryMock,
                times(1)).save(record);
    }

    @Test
    public void update_RecordNotExist_Fail() {
        Throwable e = null;
        Record record = new Record();
        when(repositoryMock.findById(record.getId())).thenReturn(Optional.empty());

        try {
            recordService.update(record);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof NotFoundException);
    }

    @Test
    public void update_RecordNotBelongUser_Fail() {
        Record record = new Record();
        record.setOwner(createUserProfile(2));
        when(repositoryMock.findById(record.getId())).thenReturn(Optional.of(record));

        Throwable e = null;
        try {
            recordService.update(record);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IllegalAccessException);
    }

    @Test
    public void getRecords_Success() throws IllegalAccessException {
        Record record = new Record();
        record.setOwner(currentUserProfile);
        when(repositoryMock.findById(record.getId())).thenReturn(Optional.of(record));

        Record result = recordService.getRecord(record.getId());

        Assert.assertSame(record, result);
    }

    @Test
    public void getRecords_InvalidUser_Fail() {
        Record record1 = createRecord(1, createUserProfile(2));

        when(repositoryMock.findById(record1.getId())).thenReturn(Optional.of(record1));

        Throwable e = null;
        try {
            recordService.getRecord((long) 1);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IllegalAccessException);
    }

    @Test
    public void deleteRecord_Success() throws IllegalAccessException, NotFoundException {
        Record record = createRecord(1, currentUserProfile);
        when(repositoryMock.findById(record.getId())).thenReturn(Optional.of(record));

        recordService.deleteRecord(record.getId());

        verify(this.repositoryMock,
                times(1)).delete(record);
    }

    @Test
    public void deleteRecord_ReturnOldRecord_Success() throws IllegalAccessException, NotFoundException {
        Record record = createRecord(1, currentUserProfile);
        when(repositoryMock.findById(record.getId())).thenReturn(Optional.of(record));

        Record result = recordService.deleteRecord(record.getId());
        Assert.assertSame(result, record);
    }

    @Test
    public void deleteRecord_InvalidUser_Fail() {
        Record record1 = createRecord(1, createUserProfile(2));

        when(repositoryMock.findById(record1.getId())).thenReturn(Optional.of(record1));

        Throwable e = null;
        try {
            recordService.deleteRecord((long) 1);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IllegalAccessException);
    }

    @Test
    public void deleteRecord_NoRecord_Fail() {
        Record record1 = createRecord(1, currentUserProfile);

        when(repositoryMock.findById(record1.getId())).thenReturn(Optional.empty());

        Throwable e = null;
        try {
            recordService.deleteRecord((long) 1);
        } catch (Throwable ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof NotFoundException);
    }

}
