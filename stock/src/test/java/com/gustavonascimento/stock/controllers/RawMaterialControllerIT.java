package com.gustavonascimento.stock.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavonascimento.stock.records.rawmaterial.CreateRawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.UpdateRawMaterial;
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
class RawMaterialControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldCreateRawMaterialWhenAuthorized() throws Exception {
        CreateRawMaterial input =
                new CreateRawMaterial("FG1", "Fogo", 100.0);

        mockMvc.perform(post("/api/raw-materials")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.code").value("FG1"))
                .andExpect(jsonPath("$.name").value("Fogo"))
                .andExpect(jsonPath("$.stockQuantity").value(100.0));
    }

    @Test
    void shouldListAllRawMaterials() throws Exception {
        mockMvc.perform(get("/api/raw-materials/all")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.totalElements").exists())
                .andExpect(jsonPath("$.totalPages").exists())
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.number").value(0));
    }

    @Test
    void shouldGetRawMaterialById() throws Exception {
        String response = mockMvc.perform(post("/api/raw-materials")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateRawMaterial("TR1", "Terra", 50.0)
                        )))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/raw-materials/{id}", id)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("TR1"))
                .andExpect(jsonPath("$.name").value("Terra"));
    }

    @Test
    void shouldUpdateRawMaterial() throws Exception {
        String response = mockMvc.perform(post("/api/raw-materials")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateRawMaterial("AR1", "Ar raro", 30.0)
                        )))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        UpdateRawMaterial update =
                new UpdateRawMaterial("Ar Atualizado", 60.0);

        mockMvc.perform(put("/api/raw-materials/{id}", id)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ar Atualizado"))
                .andExpect(jsonPath("$.stockQuantity").value(60.0));
    }

    @Test
    void shouldDeleteRawMaterial() throws Exception {
        String response = mockMvc.perform(post("/api/raw-materials")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateRawMaterial("lz1", "Luz", 10.0)
                        )))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/raw-materials/{id}", id)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnUnauthorizedWhenNoTokenProvided() throws Exception {
        mockMvc.perform(get("/api/raw-materials/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldListRawMaterialsToAssociate() throws Exception {
        mockMvc.perform(get("/api/raw-materials/to-associate")
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").isNotEmpty());
    }
}