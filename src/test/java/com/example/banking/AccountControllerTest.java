package com.example.banking;

import com.example.banking.model.Account;
import com.example.banking.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        // document why this method is empty
    }

    @Test
    void testReset() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/reset"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testWithdrawFromExistingAccount() throws Exception {
        when(accountService.withdraw(anyString(), anyInt())).thenReturn(new Account("100", 95));

        mockMvc.perform(MockMvcRequestBuilders.post("/event")
                        .contentType("application/json")
                        .content("{\"type\":\"withdraw\",\"origin\":\"100\",\"amount\":5}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.origin.balance").value(95));
    }

    @Test
    void testTransferFromExistingAccount() throws Exception {
        when(accountService.transfer(anyString(), anyString(), anyInt())).thenReturn(true);
        when(accountService.findAccountById("100")).thenReturn(new Account("100", 80));
        when(accountService.findAccountById("300")).thenReturn(new Account("300", 305));

        mockMvc.perform(MockMvcRequestBuilders.post("/event")
                        .contentType("application/json")
                        .content("{\"type\":\"transfer\",\"origin\":\"100\",\"destination\":\"300\",\"amount\":15}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.origin.balance").value(80))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination.balance").value(305));
    }

    @Test
    void testGetBalanceExistingAccount() throws Exception {
        when(accountService.findAccountById("100")).thenReturn(new Account("100", 20));

        mockMvc.perform(MockMvcRequestBuilders.get("/balance")
                        .param("account_id", "100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("20"));
    }

    @Test
    void testWithdrawFromNonExistingAccount() throws Exception {
        when(accountService.withdraw(anyString(), anyInt())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/event")
                        .contentType("application/json")
                        .content("{\"type\":\"withdraw\",\"origin\":\"200\",\"amount\":10}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Origin account not found or insufficient funds"));
    }

    @Test
    void testTransferFromNonExistingAccount() throws Exception {
        when(accountService.transfer(anyString(), anyString(), anyInt())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/event")
                        .contentType("application/json")
                        .content("{\"type\":\"transfer\",\"origin\":\"200\",\"destination\":\"300\",\"amount\":15}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Transfer failed"));
    }
}
