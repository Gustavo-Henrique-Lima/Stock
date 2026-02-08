package com.gustavonascimento.stock.controllers;

import com.gustavonascimento.stock.token.TokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductionSimulationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    private String adminToken;

    @BeforeEach
    void setUp() throws Exception {
        adminToken = tokenUtil.obtainAccessToken(
                mockMvc,
                "bob@gmail.com",
                "123456"
        );
    }

    @Test
    void shouldSimulateProductionUsingExistingDatabaseData() throws Exception {

        mockMvc.perform(get("/api/production/simulation")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").isNotEmpty())
                .andExpect(jsonPath("$.totalProductionValue").isNumber())

                .andExpect(jsonPath("$.items[0].productCode").value("P002"))

                .andExpect(jsonPath("$.items[0].totalValue").isNumber());
    }

    @Test
    void shouldReturnUnauthorizedWhenNoTokenProvided() throws Exception {
        mockMvc.perform(get("/api/production/simulation"))
                .andExpect(status().isUnauthorized());
    }
}
