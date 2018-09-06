package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.service.RecordService;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.antonybaasan.receiptkeeper.restdata.FirebaseTestUtils.generateToken;
import static com.antonybaasan.receiptkeeper.restdata.TestUtils.createRecord;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecordControllerTest {
    @Value("${FIREBASE_PRIVATEKEY}")
    private String firebasePrivateKey;
    @MockBean
    private RecordService recordService;
    @Autowired
    public MockMvc mockMvc;

    private String accessToken;

    @Before
    public void beforeMethods() throws FirebaseAuthException {
        if(accessToken == null) {
            accessToken = generateToken(firebasePrivateKey, "1");
        }
    }

    @Test
    public void getRecordById_Success() throws Exception {
        Record record = createRecord(1);
        when(recordService.getRecord(1)).thenReturn(record);

        mockMvc.perform(get("/records/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{}"));
    }

    @Test
    public void getRecordById_WhenNoRecord_ReturnEmptyBody() throws Exception {
        when(recordService.getRecord(2)).thenReturn(null);

        mockMvc.perform(get("/records/2")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void getRecordById_WhenIllegalAccess_ReturnUnauthorized() throws Exception {
        when(recordService.getRecord(2)).thenThrow(IllegalAccessException.class);

        mockMvc.perform(get("/records/2")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isUnauthorized());
    }

}
