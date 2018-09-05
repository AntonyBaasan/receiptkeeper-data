package com.antonybaasan.receiptkeeper.restdata.controllers;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import com.antonybaasan.receiptkeeper.restdata.service.RecordService;

import static com.antonybaasan.receiptkeeper.restdata.TestUtils.*;

import com.antonybaasan.receiptkeeper.restdata.service.UserProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
@SpringBootTest
@AutoConfigureMockMvc
public class RecordControllerTest {
    @MockBean
    private RecordService recordService;

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void getRecordById_Success() throws Exception {

        Record record = createRecord(1);
        when(recordService.getRecord(1)).thenReturn(record);

//        String accessToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEwY2ViNDY3NDJhNjNlMTk2NDIxNjNhNzI4NmRjZDQyZjc0MzYzNjYifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYm9va2luZ3ByZW5ldXIiLCJuYW1lIjoiQW50b255IEJhYXNhbmRvcmoiLCJwaWN0dXJlIjoiaHR0cHM6Ly9hdmF0YXJzMi5naXRodWJ1c2VyY29udGVudC5jb20vdS80OTg2NjAxP3Y9NCIsImF1ZCI6ImJvb2tpbmdwcmVuZXVyIiwiYXV0aF90aW1lIjoxNTI5NDYxODk0LCJ1c2VyX2lkIjoiVGtBcFVGaTZKa2d2SDIxenA1Sm9FSWJnaG0yMiIsInN1YiI6IlRrQXBVRmk2SmtndkgyMXpwNUpvRUliZ2htMjIiLCJpYXQiOjE1MzYxMTE3NjMsImV4cCI6MTUzNjExNTM2MywiZW1haWwiOiJhbnRvbnkuYmFhc2FuQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnaXRodWIuY29tIjpbIjQ5ODY2MDEiXSwiZW1haWwiOlsiYW50b255LmJhYXNhbkBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnaXRodWIuY29tIn19.D1BpuARZodR9sPuVOuGDWCX3vlphi3iQiC39GdxwnNWfx72mUTEsoM1C1K1otOBYXwH03-JcUJaymhYzaQUqJzXDb8uh5x0-qcUSdyg53qqBD-QyqR8zQbvUs3g1R_ZADSkV6p9oo9b_pKG945SRIWF52MomZHowiailJxWtFIwMGUsaiNFr5Yui7vZpUy4recWTh3_-89mhp845P9Ao2kYg2m7Pe2xT51uz9Qjj6X2BCLcMueVFYtNP2wMsoHAVVi1ef87LA4GIYNDGtTHxsVu0j1lrQy6Tx38QwNuhNN1WvTu_FZ_kAlfSkU7MziR9pDoSxr5bP0fnyG7hbX6Asg";
        String accessToken = generateToken("1");

        mockMvc.perform(get("/records/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{}"));
    }

}
