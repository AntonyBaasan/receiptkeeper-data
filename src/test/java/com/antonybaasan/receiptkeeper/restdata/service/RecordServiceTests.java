package com.antonybaasan.receiptkeeper.restdata.service;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.model.UserProfile;
import com.antonybaasan.receiptkeeper.restdata.repositories.RecordRepository;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import com.antonybaasan.receiptkeeper.restdata.security.FbUserInfo;
import com.antonybaasan.receiptkeeper.restdata.serviceimpl.RecordServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceTests {

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
        currentUserProfile = createUserProfile();
        when(authMock.getUser()).thenReturn(currentUser);
        when(userProfileService.getUserById(currentUser.getUid())).thenReturn(currentUserProfile);
    }

    private UserProfile createUserProfile(){
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1);
        userProfile.setFirstName("Antony");
        userProfile.setLastName("Baasan");
        return userProfile;
    }

    @Test
    public void createSingleReceipt_Success(){
        Record record = new Record();
        recordService.create(record);
        verify(this.repositoryMock,
                times(1)).save(record);
    }

    @Test
    public void create_SetOwner_Success(){
        Record record = new Record();
        recordService.create(record);
        verify(this.repositoryMock,
                times(1)).save(record);
        Assert.assertEquals(record.getOwner(), currentUserProfile);
    }

//    @Test
//    public void removeSingleReceipt_Success() {
//        Receipt receipt = new Receipt(1, currentUser.getUid());
//
//        when(authMock.getUser()).thenReturn(currentUser);
//
//        when(repositoryMock.findById(receipt.getId())).thenReturn(ofNullable(receipt));
//
//        Optional<Receipt> returned = this.recordService.delete(receipt.getId());
//
//        verify(this.repositoryMock, times(1)).delete(receipt);
//        Assert.assertEquals(receipt.getId(), returned.get().getId());
//    }
//
//    @Test(expected = IllegalAccessException.class)
//    public void testThrowExceptionIfWrongOwnerId() throws IllegalAccessException, NotFoundException {
//        Receipt receipt = new Receipt(1, "owner2");
//
//        when(authMock.getUser()).thenReturn(currentUser);
//        when(repositoryMock.findById(receipt.getId())).thenReturn(ofNullable(receipt));
//
//        this.recordService.delete(receipt.getId());
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void testThrowExceptionIfCantFindReceipt() throws IllegalAccessException, NotFoundException {
//        Receipt receipt = new Receipt(1, currentUser.getUid());
//
//        when(authMock.getUser()).thenReturn(currentUser);
//        when(repositoryMock.findById(receipt.getId())).thenReturn(ofNullable(null));
//
//        this.recordService.delete(receipt.getId());
//    }
}
