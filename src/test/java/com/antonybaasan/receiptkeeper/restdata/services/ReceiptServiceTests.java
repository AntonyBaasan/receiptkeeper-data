package com.antonybaasan.receiptkeeper.restdata.services;

import com.antonybaasan.receiptkeeper.restdata.domains.Receipt;
import com.antonybaasan.receiptkeeper.restdata.repositories.ReceiptRepository;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import com.antonybaasan.receiptkeeper.restdata.security.FbUserInfo;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Optional.ofNullable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServiceTests {

    ReceiptService receiptService;

    @Mock
    ReceiptRepository repositoryMock;

    @Mock
    AuthFacade authMock;

    FbUserInfo currentUser;

    @Before
    public void setUp() {
        receiptService = new ReceiptService(repositoryMock, authMock);

        currentUser = new FbUserInfo("owner1", "ant", "", "");
    }

    @Test
    public void testRemoveSingleReceiptSuccess() throws IllegalAccessException, NotFoundException {
        Receipt receipt = new Receipt(1, currentUser.getUid());

        when(authMock.getUser()).thenReturn(currentUser);

        when(repositoryMock.findById(receipt.getId())).thenReturn(ofNullable(receipt));

        this.receiptService.delete(receipt.getId());

        verify(this.repositoryMock, times(1)).delete(receipt);
    }

    @Test(expected = IllegalAccessException.class)
    public void testThrowExceptionIfWrongOwnerId() throws IllegalAccessException, NotFoundException {
        Receipt receipt = new Receipt(1, "owner2");

        when(authMock.getUser()).thenReturn(currentUser);
        when(repositoryMock.findById(receipt.getId())).thenReturn(ofNullable(receipt));

        this.receiptService.delete(receipt.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testThrowExceptionIfCantFindReceipt() throws IllegalAccessException, NotFoundException {
        Receipt receipt = new Receipt(1, currentUser.getUid());

        when(authMock.getUser()).thenReturn(currentUser);
        when(repositoryMock.findById(receipt.getId())).thenReturn(ofNullable(null));

        this.receiptService.delete(receipt.getId());
    }
}
