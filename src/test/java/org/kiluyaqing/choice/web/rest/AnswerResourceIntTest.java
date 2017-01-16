package org.kiluyaqing.choice.web.rest;

import org.kiluyaqing.choice.ChoiceApp;

import org.kiluyaqing.choice.domain.Answer;
import org.kiluyaqing.choice.repository.AnswerRepository;
import org.kiluyaqing.choice.service.AnswerService;
import org.kiluyaqing.choice.service.dto.AnswerDTO;
import org.kiluyaqing.choice.service.mapper.AnswerMapper;

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
 * Test class for the AnswerResource REST controller.
 *
 * @see AnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChoiceApp.class)
public class AnswerResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RESULT = false;
    private static final Boolean UPDATED_RESULT = true;

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
    private AnswerRepository answerRepository;

    @Inject
    private AnswerMapper answerMapper;

    @Inject
    private AnswerService answerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAnswerMockMvc;

    private Answer answer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnswerResource answerResource = new AnswerResource();
        ReflectionTestUtils.setField(answerResource, "answerService", answerService);
        this.restAnswerMockMvc = MockMvcBuilders.standaloneSetup(answerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createEntity(EntityManager em) {
        Answer answer = new Answer()
                .content(DEFAULT_CONTENT)
                .result(DEFAULT_RESULT)
                .remark(DEFAULT_REMARK)
                .createdDate(DEFAULT_CREATED_DATE)
                .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
                .createdBy(DEFAULT_CREATED_BY)
                .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return answer;
    }

    @Before
    public void initTest() {
        answer = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnswer() throws Exception {
        int databaseSizeBeforeCreate = answerRepository.findAll().size();

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.answerToAnswerDTO(answer);

        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isCreated());

        // Validate the Answer in the database
        List<Answer> answers = answerRepository.findAll();
        assertThat(answers).hasSize(databaseSizeBeforeCreate + 1);
        Answer testAnswer = answers.get(answers.size() - 1);
        assertThat(testAnswer.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAnswer.isResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testAnswer.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testAnswer.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAnswer.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testAnswer.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAnswer.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAnswers() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answers
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answer.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get the answer
        restAnswerMockMvc.perform(get("/api/answers/{id}", answer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(answer.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnswer() throws Exception {
        // Get the answer
        restAnswerMockMvc.perform(get("/api/answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Update the answer
        Answer updatedAnswer = answerRepository.findOne(answer.getId());
        updatedAnswer
                .content(UPDATED_CONTENT)
                .result(UPDATED_RESULT)
                .remark(UPDATED_REMARK)
                .createdDate(UPDATED_CREATED_DATE)
                .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
                .createdBy(UPDATED_CREATED_BY)
                .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AnswerDTO answerDTO = answerMapper.answerToAnswerDTO(updatedAnswer);

        restAnswerMockMvc.perform(put("/api/answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isOk());

        // Validate the Answer in the database
        List<Answer> answers = answerRepository.findAll();
        assertThat(answers).hasSize(databaseSizeBeforeUpdate);
        Answer testAnswer = answers.get(answers.size() - 1);
        assertThat(testAnswer.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAnswer.isResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testAnswer.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testAnswer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAnswer.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testAnswer.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAnswer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void deleteAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);
        int databaseSizeBeforeDelete = answerRepository.findAll().size();

        // Get the answer
        restAnswerMockMvc.perform(delete("/api/answers/{id}", answer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Answer> answers = answerRepository.findAll();
        assertThat(answers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
