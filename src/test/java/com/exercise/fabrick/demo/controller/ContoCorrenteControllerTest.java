package com.exercise.fabrick.demo.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.exercise.fabrick.demo.model.request.Account;
import com.exercise.fabrick.demo.model.request.Creditor;
import com.exercise.fabrick.demo.model.request.InviaBonificoRequest;

public class ContoCorrenteControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getSaldoContoCorrente() throws Exception {
        String uri = "/getSaldoContoCorrente/14537780";
        MvcResult mvcResult400 = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult400.getResponse().getStatus();
        assertEquals(400, status);

        MvcResult mvcResult200 = mvc.perform(MockMvcRequestBuilders.get(uri)
                .header("Api-Key", "prova")
                .header("Auth-Schema", "apikeyvalue")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        status = mvcResult200.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void getListaTransazioni() throws Exception {
        String uri = "/getListaTransazioni/14537780";
        MvcResult mvcResult400 = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult400.getResponse().getStatus();
        assertEquals(400, status);

        MvcResult mvcResult40 = mvc.perform(MockMvcRequestBuilders.get(uri)
                .header("Api-Key", "prova")
                .header("Auth-Schema", "apikeyvalue")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        status = mvcResult40.getResponse().getStatus();
        assertEquals(400, status);

        MvcResult mvcResult200date = mvc.perform(MockMvcRequestBuilders.get(uri)
                .header("Api-Key", "prova")
                .header("Auth-Schema", "apikeyvalue")
                .queryParam("fromAccountingDate", "2024-04-10")
                .queryParam("toAccountingDate", "2024-04-10")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        status = mvcResult200date.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void invioBonifico() throws Exception {
        String uri = "/inviaBonifico/14537780";
        InviaBonificoRequest request= new InviaBonificoRequest();
        request.setAmount("1234");
        Creditor creditor= new Creditor();
        Account account= new Account();
        account.setAccountCode("accountCode");
        creditor.setAccount(account);
        creditor.setName("name");
        request.setCreditor(creditor);
        request.setCurrency("currency");
        request.setDescription("description");
        request.setExecutionDate("2024-04-10");
        String requestJson = mapToJson(request);


        MvcResult mvcResult400 = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)).andReturn();
        int status = mvcResult400.getResponse().getStatus();
        assertEquals(400, status);

        MvcResult mvcResult200 = mvc.perform(MockMvcRequestBuilders.post(uri)
                .header("Api-Key", "prova")
                .header("Auth-Schema", "apikeyvalue")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)).andReturn();
        status = mvcResult200.getResponse().getStatus();
        assertEquals(415, status);
    }
}