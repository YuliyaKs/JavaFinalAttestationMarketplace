package com.github.onlineshop.controller;

import com.github.onlineshop.constants.ErrorMessage;
import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.onlineshop.util.TestConstants.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-perfumes-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-perfumes-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PerfumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /perfume/1 - Get Perfumes")
    public void getPerfumeById() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME + "/{perfumeId}", PERFUME_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUME))
                .andExpect(model().attribute("perfume", hasProperty("id", is(PERFUME_ID))))
                .andExpect(model().attribute("perfume", hasProperty("perfumeTitle", is(PERFUME_TITLE))))
                .andExpect(model().attribute("perfume", hasProperty("perfumer", is(PERFUMER))))
                .andExpect(model().attribute("perfume", hasProperty("year", is(YEAR))))
                .andExpect(model().attribute("perfume", hasProperty("country", is(COUNTRY))))
                .andExpect(model().attribute("perfume", hasProperty("perfumeGender", is(PERFUME_GENDER))))
                .andExpect(model().attribute("perfume", hasProperty("fragranceTopNotes", is(FRAGRANCE_TOP_NOTES))))
                .andExpect(model().attribute("perfume", hasProperty("fragranceMiddleNotes", is(FRAGRANCE_MIDDLE_NOTES))))
                .andExpect(model().attribute("perfume", hasProperty("fragranceBaseNotes", is(FRAGRANCE_BASE_NOTES))))
                .andExpect(model().attribute("perfume", hasProperty("filename", is(FILENAME))))
                .andExpect(model().attribute("perfume", hasProperty("price", is(PRICE))))
                .andExpect(model().attribute("perfume", hasProperty("volume", is(VOLUME))))
                .andExpect(model().attribute("perfume", hasProperty("type", is(TYPE))));
    }

    @Test
    @DisplayName("[404] GET /perfume/111 - Get Perfume By Id NotFound")
    public void getPerfumeById_NotFound() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME + "/{perfumeId}", 111))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(ErrorMessage.PERFUME_NOT_FOUND));
    }

    @Test
    @DisplayName("[200] GET /perfume - Get Perfumes By Filter Params")
    public void getPerfumesByFilterParams() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(12))));
    }

    @Test
    @DisplayName("[200] GET /perfume - Get Perfumes By Filter Params: perfumers")
    public void getPerfumesByFilterParams_Perfumers() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME)
                        .param("perfumers", "LAB FRAGRANCE", "MegaCosm"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(4))));
    }

    @Test
    @DisplayName("[200] GET /perfume - Get Perfumes By Filter Params: perfumers, genders")
    public void getPerfumesByFilterParams_PerfumersAndGenders() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME)
                        .param("perfumers", "СЛАВА ЗАЙЦЕВ")
                        .param("genders", "мужской"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /perfume - Get Perfumes By Filter Params: perfumers, genders, price")
    public void getPerfumesByFilterParams_PerfumersAndGendersAndPrice() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME)
                        .param("perfumers", "СЛАВА ЗАЙЦЕВ", "LAB FRAGRANCE")
                        .param("genders", "женский")
                        .param("price", "5100"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /perfume/search - Search Perfumes By Perfumer")
    public void searchPerfumes_ByPerfumer() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME + "/search")
                        .param("searchType", "perfumer")
                        .param("text", "LAB FRAGRANCE"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(3))));
    }

    @Test
    @DisplayName("[200] GET /perfume/search - Search Perfumes By Country")
    public void searchPerfumes_ByCountry() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME + "/search")
                        .param("searchType", "country")
                        .param("text", "Франция"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(4))));
    }

    @Test
    @DisplayName("[200] GET /perfume/search - Search Perfumes By Perfume Title")
    public void searchPerfumes_PerfumeTitle() throws Exception {
        mockMvc.perform(get(PathConstants.PERFUME + "/search")
                        .param("searchType", "perfumeTitle")
                        .param("text", "perpetuum"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }
}
