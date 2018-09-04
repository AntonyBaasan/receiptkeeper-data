package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.service.RecordService;
import static com.antonybaasan.receiptkeeper.restdata.TestUtils.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc(secure = false)
public class RecordControllerTest {
    @MockBean
    private RecordService recordService;

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void getRecord_Success() throws Exception {
        Record record = createRecord(1);
        when(recordService.getRecord(1)).thenReturn(record);

        mockMvc.perform(get("/records/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}
