package org.kiluyaqing.choice.web.rest;

import org.kiluyaqing.choice.ChoiceApp;

import org.kiluyaqing.choice.domain.ChoiceUser;
import org.kiluyaqing.choice.repository.ChoiceUserRepository;
import org.kiluyaqing.choice.service.ChoiceUserService;
import org.kiluyaqing.choice.service.dto.ChoiceUserDTO;
import org.kiluyaqing.choice.service.mapper.ChoiceUserMapper;

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
 * Test class for the ChoiceUserResource REST controller.
 *
 * @see ChoiceUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChoiceApp.class)
public class ChoiceUserResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Inject
    private ChoiceUserRepository choiceUserRepository;

    @Inject
    private ChoiceUserMapper choiceUserMapper;

    @Inject
    private ChoiceUserService choiceUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restChoiceUserMockMvc;

    private ChoiceUser choiceUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChoiceUserResource choiceUserResource = new ChoiceUserResource();
        ReflectionTestUtils.setField(choiceUserResource, "choiceUserService", choiceUserService);
        this.restChoiceUserMockMvc = MockMvcBuilders.standaloneSetup(choiceUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChoiceUser createEntity(EntityManager em) {
        ChoiceUser choiceUser = new ChoiceUser()
                .userName(DEFAULT_USER_NAME)
                .passwordHash(DEFAULT_PASSWORD_HASH)
                .email(DEFAULT_EMAIL)
                .createdDate(DEFAULT_CREATED_DATE)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return choiceUser;
    }

    @Before
    public void initTest() {
        choiceUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createChoiceUser() throws Exception {
        int databaseSizeBeforeCreate = choiceUserRepository.findAll().size();

        // Create the ChoiceUser
        ChoiceUserDTO choiceUserDTO = choiceUserMapper.choiceUserToChoiceUserDTO(choiceUser);

        restChoiceUserMockMvc.perform(post("/api/choice-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choiceUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ChoiceUser in the database
        List<ChoiceUser> choiceUsers = choiceUserRepository.findAll();
        assertThat(choiceUsers).hasSize(databaseSizeBeforeCreate + 1);
        ChoiceUser testChoiceUser = choiceUsers.get(choiceUsers.size() - 1);
        assertThat(testChoiceUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testChoiceUser.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testChoiceUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testChoiceUser.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testChoiceUser.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testChoiceUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testChoiceUser.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllChoiceUsers() throws Exception {
        // Initialize the database
        choiceUserRepository.saveAndFlush(choiceUser);

        // Get all the choiceUsers
        restChoiceUserMockMvc.perform(get("/api/choice-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choiceUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getChoiceUser() throws Exception {
        // Initialize the database
        choiceUserRepository.saveAndFlush(choiceUser);

        // Get the choiceUser
        restChoiceUserMockMvc.perform(get("/api/choice-users/{id}", choiceUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(choiceUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChoiceUser() throws Exception {
        // Get the choiceUser
        restChoiceUserMockMvc.perform(get("/api/choice-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChoiceUser() throws Exception {
        // Initialize the database
        choiceUserRepository.saveAndFlush(choiceUser);
        int databaseSizeBeforeUpdate = choiceUserRepository.findAll().size();

        // Update the choiceUser
        ChoiceUser updatedChoiceUser = choiceUserRepository.findOne(choiceUser.getId());
        updatedChoiceUser
                .userName(UPDATED_USER_NAME)
                .passwordHash(UPDATED_PASSWORD_HASH)
                .email(UPDATED_EMAIL)
                .createdDate(UPDATED_CREATED_DATE)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ChoiceUserDTO choiceUserDTO = choiceUserMapper.choiceUserToChoiceUserDTO(updatedChoiceUser);

        restChoiceUserMockMvc.perform(put("/api/choice-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choiceUserDTO)))
            .andExpect(status().isOk());

        // Validate the ChoiceUser in the database
        List<ChoiceUser> choiceUsers = choiceUserRepository.findAll();
        assertThat(choiceUsers).hasSize(databaseSizeBeforeUpdate);
        ChoiceUser testChoiceUser = choiceUsers.get(choiceUsers.size() - 1);
        assertThat(testChoiceUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testChoiceUser.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testChoiceUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testChoiceUser.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testChoiceUser.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testChoiceUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testChoiceUser.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void deleteChoiceUser() throws Exception {
        // Initialize the database
        choiceUserRepository.saveAndFlush(choiceUser);
        int databaseSizeBeforeDelete = choiceUserRepository.findAll().size();

        // Get the choiceUser
        restChoiceUserMockMvc.perform(delete("/api/choice-users/{id}", choiceUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChoiceUser> choiceUsers = choiceUserRepository.findAll();
        assertThat(choiceUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
