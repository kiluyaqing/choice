package org.kiluyaqing.choice.web.rest;

import org.kiluyaqing.choice.ChoiceApp;

import org.kiluyaqing.choice.domain.Investigation;
import org.kiluyaqing.choice.repository.InvestigationRepository;
import org.kiluyaqing.choice.service.InvestigationService;
import org.kiluyaqing.choice.service.dto.InvestigationDTO;
import org.kiluyaqing.choice.service.mapper.InvestigationMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvestigationResource REST controller.
 *
 * @see InvestigationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChoiceApp.class)
public class InvestigationResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private InvestigationRepository investigationRepository;

    @Inject
    private InvestigationMapper investigationMapper;

    @Inject
    private InvestigationService investigationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInvestigationMockMvc;

    private Investigation investigation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvestigationResource investigationResource = new InvestigationResource();
        ReflectionTestUtils.setField(investigationResource, "investigationService", investigationService);
        this.restInvestigationMockMvc = MockMvcBuilders.standaloneSetup(investigationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Investigation createEntity(EntityManager em) {
        Investigation investigation = new Investigation()
                .content(DEFAULT_CONTENT)
                .remark(DEFAULT_REMARK)
                .createdDate(DEFAULT_CREATED_DATE)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return investigation;
    }

    @Before
    public void initTest() {
        investigation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvestigation() throws Exception {
        int databaseSizeBeforeCreate = investigationRepository.findAll().size();

        // Create the Investigation
        InvestigationDTO investigationDTO = investigationMapper.investigationToInvestigationDTO(investigation);

        restInvestigationMockMvc.perform(post("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigationDTO)))
            .andExpect(status().isCreated());

        // Validate the Investigation in the database
        List<Investigation> investigations = investigationRepository.findAll();
        assertThat(investigations).hasSize(databaseSizeBeforeCreate + 1);
        Investigation testInvestigation = investigations.get(investigations.size() - 1);
        assertThat(testInvestigation.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testInvestigation.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testInvestigation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInvestigation.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testInvestigation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInvestigation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllInvestigations() throws Exception {
        // Initialize the database
        investigationRepository.saveAndFlush(investigation);

        // Get all the investigations
        restInvestigationMockMvc.perform(get("/api/investigations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investigation.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getInvestigation() throws Exception {
        // Initialize the database
        investigationRepository.saveAndFlush(investigation);

        // Get the investigation
        restInvestigationMockMvc.perform(get("/api/investigations/{id}", investigation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(investigation.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvestigation() throws Exception {
        // Get the investigation
        restInvestigationMockMvc.perform(get("/api/investigations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestigation() throws Exception {
        // Initialize the database
        investigationRepository.saveAndFlush(investigation);
        int databaseSizeBeforeUpdate = investigationRepository.findAll().size();

        // Update the investigation
        Investigation updatedInvestigation = investigationRepository.findOne(investigation.getId());
        updatedInvestigation
                .content(UPDATED_CONTENT)
                .remark(UPDATED_REMARK)
                .createdDate(UPDATED_CREATED_DATE)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        InvestigationDTO investigationDTO = investigationMapper.investigationToInvestigationDTO(updatedInvestigation);

        restInvestigationMockMvc.perform(put("/api/investigations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigationDTO)))
            .andExpect(status().isOk());

        // Validate the Investigation in the database
        List<Investigation> investigations = investigationRepository.findAll();
        assertThat(investigations).hasSize(databaseSizeBeforeUpdate);
        Investigation testInvestigation = investigations.get(investigations.size() - 1);
        assertThat(testInvestigation.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testInvestigation.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testInvestigation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInvestigation.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testInvestigation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInvestigation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void deleteInvestigation() throws Exception {
        // Initialize the database
        investigationRepository.saveAndFlush(investigation);
        int databaseSizeBeforeDelete = investigationRepository.findAll().size();

        // Get the investigation
        restInvestigationMockMvc.perform(delete("/api/investigations/{id}", investigation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Investigation> investigations = investigationRepository.findAll();
        assertThat(investigations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
