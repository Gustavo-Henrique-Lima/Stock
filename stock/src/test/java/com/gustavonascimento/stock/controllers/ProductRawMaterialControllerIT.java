package com.gustavonascimento.stock.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.gustavonascimento.stock.records.productrawmaterial.AddRawMaterialToProduct;
import com.gustavonascimento.stock.records.productrawmaterial.UpdateProductRawMaterial;
import com.gustavonascimento.stock.token.TokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductRawMaterialControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    private String adminToken;

    private static final Long PRODUCT_ID = 2L;
    private static final Long RAW_MATERIAL_ID = 2L;

    @BeforeEach
    void setUp() throws Exception {
        adminToken = tokenUtil.obtainAccessToken(
                mockMvc,
                "bob@gmail.com",
                "123456"
        );
    }

    @Test
    void shouldAddRawMaterialToProduct() throws Exception {

        AddRawMaterialToProduct command =
                new AddRawMaterialToProduct(3L, 7.5);

        mockMvc.perform(post("/api/products/raw-materials/{productId}", PRODUCT_ID)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldListRawMaterialsFromProduct() throws Exception {

        mockMvc.perform(get("/api/products/raw-materials/{productId}", PRODUCT_ID)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].rawMaterialId").isNumber())
                .andExpect(jsonPath("$[0].rawMaterialCode").exists())
                .andExpect(jsonPath("$[0].requiredQuantity").isNumber());
    }

    @Test
    void shouldUpdateRequiredQuantity() throws Exception {

        UpdateProductRawMaterial update =
                new UpdateProductRawMaterial(9.0);

        mockMvc.perform(put("/api/products/raw-materials/{productId}/{rawMaterialId}",
                        PRODUCT_ID, RAW_MATERIAL_ID)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRemoveRawMaterialFromProduct() throws Exception {

        mockMvc.perform(delete("/api/products/raw-materials/{productId}/{rawMaterialId}",
                        PRODUCT_ID, RAW_MATERIAL_ID)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnUnauthorizedWhenNoTokenProvided() throws Exception {
        mockMvc.perform(get("/api/products/raw-materials/{productId}", PRODUCT_ID))
                .andExpect(status().isUnauthorized());
    }
}