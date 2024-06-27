package com.kyrylocodefirst.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.kyrylocodefirst.KyryloCodeFirstApplication;

@SpringBootTest(classes = KyryloCodeFirstApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
public class PriceControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getPriceTest_shouldReturn_200() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("http://localhost:8080/prices")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.size()").value(4));
    }

    @Test
    public void getPvpPriceTest_shouldReturn_200() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("http://localhost:8080/prices/getPvpPrice")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "2020-06-14T10:00")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.brandId").value(1))
            .andExpect(jsonPath("$.priceList").value(1))
            .andExpect(jsonPath("$.productId").value(35455))
            .andExpect(jsonPath("$.price").value(35.50))
            .andExpect(jsonPath("$.currency").value("EUR"))
            .andExpect(jsonPath("$.applicationDate").value("2020-06-14T10:00:00"));
    }

    @Test
    public void getPvpPriceTest_shouldReturn_EntityNotFoundException() throws Exception {
        mockMvc.perform(get("http://localhost:8080/prices/getPvpPrice")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "2021-01-01T01:00:00")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message")
                .value("Pvp price for these brandId: 1 and productId: 35455 and applicationDate: 2021-01-01T01:00 not found"));
    }
}
