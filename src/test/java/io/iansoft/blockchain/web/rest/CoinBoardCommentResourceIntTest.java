package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.CoinBoardComment;
import io.iansoft.blockchain.repository.CoinBoardCommentRepository;
import io.iansoft.blockchain.service.CoinBoardCommentService;
import io.iansoft.blockchain.repository.search.CoinBoardCommentSearchRepository;
import io.iansoft.blockchain.service.dto.CoinBoardCommentDTO;
import io.iansoft.blockchain.service.mapper.CoinBoardCommentMapper;
import io.iansoft.blockchain.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.iansoft.blockchain.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CoinBoardCommentResource REST controller.
 *
 * @see CoinBoardCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class CoinBoardCommentResourceIntTest {

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final Long DEFAULT_GROUP_NO = 1L;
    private static final Long UPDATED_GROUP_NO = 2L;

    private static final Long DEFAULT_PARENT = 1L;
    private static final Long UPDATED_PARENT = 2L;

    private static final Long DEFAULT_DEPTH = 1L;
    private static final Long UPDATED_DEPTH = 2L;

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CoinBoardCommentRepository coinBoardCommentRepository;

    @Autowired
    private CoinBoardCommentMapper coinBoardCommentMapper;

    @Autowired
    private CoinBoardCommentService coinBoardCommentService;

    @Autowired
    private CoinBoardCommentSearchRepository coinBoardCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoinBoardCommentMockMvc;

    private CoinBoardComment coinBoardComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CoinBoardCommentResource coinBoardCommentResource = new CoinBoardCommentResource(coinBoardCommentService);
        this.restCoinBoardCommentMockMvc = MockMvcBuilders.standaloneSetup(coinBoardCommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoinBoardComment createEntity(EntityManager em) {
        CoinBoardComment coinBoardComment = new CoinBoardComment()
            .context(DEFAULT_CONTEXT)
            .groupNo(DEFAULT_GROUP_NO)
            .parent(DEFAULT_PARENT)
            .depth(DEFAULT_DEPTH)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return coinBoardComment;
    }

    @Before
    public void initTest() {
        coinBoardCommentSearchRepository.deleteAll();
        coinBoardComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoinBoardComment() throws Exception {
        int databaseSizeBeforeCreate = coinBoardCommentRepository.findAll().size();

        // Create the CoinBoardComment
        CoinBoardCommentDTO coinBoardCommentDTO = coinBoardCommentMapper.toDto(coinBoardComment);
        restCoinBoardCommentMockMvc.perform(post("/api/coin-board-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinBoardCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the CoinBoardComment in the database
        List<CoinBoardComment> coinBoardCommentList = coinBoardCommentRepository.findAll();
        assertThat(coinBoardCommentList).hasSize(databaseSizeBeforeCreate + 1);
        CoinBoardComment testCoinBoardComment = coinBoardCommentList.get(coinBoardCommentList.size() - 1);
        assertThat(testCoinBoardComment.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testCoinBoardComment.getGroupNo()).isEqualTo(DEFAULT_GROUP_NO);
        assertThat(testCoinBoardComment.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testCoinBoardComment.getDepth()).isEqualTo(DEFAULT_DEPTH);
        assertThat(testCoinBoardComment.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testCoinBoardComment.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);

        // Validate the CoinBoardComment in Elasticsearch
        CoinBoardComment coinBoardCommentEs = coinBoardCommentSearchRepository.findOne(testCoinBoardComment.getId());
        assertThat(coinBoardCommentEs).isEqualToComparingFieldByField(testCoinBoardComment);
    }

    @Test
    @Transactional
    public void createCoinBoardCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coinBoardCommentRepository.findAll().size();

        // Create the CoinBoardComment with an existing ID
        coinBoardComment.setId(1L);
        CoinBoardCommentDTO coinBoardCommentDTO = coinBoardCommentMapper.toDto(coinBoardComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoinBoardCommentMockMvc.perform(post("/api/coin-board-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinBoardCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CoinBoardComment> coinBoardCommentList = coinBoardCommentRepository.findAll();
        assertThat(coinBoardCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoinBoardComments() throws Exception {
        // Initialize the database
        coinBoardCommentRepository.saveAndFlush(coinBoardComment);

        // Get all the coinBoardCommentList
        restCoinBoardCommentMockMvc.perform(get("/api/coin-board-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coinBoardComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].groupNo").value(hasItem(DEFAULT_GROUP_NO.intValue())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.intValue())))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH.intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(sameInstant(DEFAULT_UPDATEDAT))));
    }

    @Test
    @Transactional
    public void getCoinBoardComment() throws Exception {
        // Initialize the database
        coinBoardCommentRepository.saveAndFlush(coinBoardComment);

        // Get the coinBoardComment
        restCoinBoardCommentMockMvc.perform(get("/api/coin-board-comments/{id}", coinBoardComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coinBoardComment.getId().intValue()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.groupNo").value(DEFAULT_GROUP_NO.intValue()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT.intValue()))
            .andExpect(jsonPath("$.depth").value(DEFAULT_DEPTH.intValue()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)))
            .andExpect(jsonPath("$.updatedat").value(sameInstant(DEFAULT_UPDATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingCoinBoardComment() throws Exception {
        // Get the coinBoardComment
        restCoinBoardCommentMockMvc.perform(get("/api/coin-board-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoinBoardComment() throws Exception {
        // Initialize the database
        coinBoardCommentRepository.saveAndFlush(coinBoardComment);
        coinBoardCommentSearchRepository.save(coinBoardComment);
        int databaseSizeBeforeUpdate = coinBoardCommentRepository.findAll().size();

        // Update the coinBoardComment
        CoinBoardComment updatedCoinBoardComment = coinBoardCommentRepository.findOne(coinBoardComment.getId());
        updatedCoinBoardComment
            .context(UPDATED_CONTEXT)
            .groupNo(UPDATED_GROUP_NO)
            .parent(UPDATED_PARENT)
            .depth(UPDATED_DEPTH)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        CoinBoardCommentDTO coinBoardCommentDTO = coinBoardCommentMapper.toDto(updatedCoinBoardComment);

        restCoinBoardCommentMockMvc.perform(put("/api/coin-board-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinBoardCommentDTO)))
            .andExpect(status().isOk());

        // Validate the CoinBoardComment in the database
        List<CoinBoardComment> coinBoardCommentList = coinBoardCommentRepository.findAll();
        assertThat(coinBoardCommentList).hasSize(databaseSizeBeforeUpdate);
        CoinBoardComment testCoinBoardComment = coinBoardCommentList.get(coinBoardCommentList.size() - 1);
        assertThat(testCoinBoardComment.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testCoinBoardComment.getGroupNo()).isEqualTo(UPDATED_GROUP_NO);
        assertThat(testCoinBoardComment.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testCoinBoardComment.getDepth()).isEqualTo(UPDATED_DEPTH);
        assertThat(testCoinBoardComment.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testCoinBoardComment.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);

        // Validate the CoinBoardComment in Elasticsearch
        CoinBoardComment coinBoardCommentEs = coinBoardCommentSearchRepository.findOne(testCoinBoardComment.getId());
        assertThat(coinBoardCommentEs).isEqualToComparingFieldByField(testCoinBoardComment);
    }

    @Test
    @Transactional
    public void updateNonExistingCoinBoardComment() throws Exception {
        int databaseSizeBeforeUpdate = coinBoardCommentRepository.findAll().size();

        // Create the CoinBoardComment
        CoinBoardCommentDTO coinBoardCommentDTO = coinBoardCommentMapper.toDto(coinBoardComment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoinBoardCommentMockMvc.perform(put("/api/coin-board-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinBoardCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the CoinBoardComment in the database
        List<CoinBoardComment> coinBoardCommentList = coinBoardCommentRepository.findAll();
        assertThat(coinBoardCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoinBoardComment() throws Exception {
        // Initialize the database
        coinBoardCommentRepository.saveAndFlush(coinBoardComment);
        coinBoardCommentSearchRepository.save(coinBoardComment);
        int databaseSizeBeforeDelete = coinBoardCommentRepository.findAll().size();

        // Get the coinBoardComment
        restCoinBoardCommentMockMvc.perform(delete("/api/coin-board-comments/{id}", coinBoardComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean coinBoardCommentExistsInEs = coinBoardCommentSearchRepository.exists(coinBoardComment.getId());
        assertThat(coinBoardCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<CoinBoardComment> coinBoardCommentList = coinBoardCommentRepository.findAll();
        assertThat(coinBoardCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCoinBoardComment() throws Exception {
        // Initialize the database
        coinBoardCommentRepository.saveAndFlush(coinBoardComment);
        coinBoardCommentSearchRepository.save(coinBoardComment);

        // Search the coinBoardComment
        restCoinBoardCommentMockMvc.perform(get("/api/_search/coin-board-comments?query=id:" + coinBoardComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coinBoardComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].groupNo").value(hasItem(DEFAULT_GROUP_NO.intValue())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.intValue())))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH.intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(sameInstant(DEFAULT_UPDATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoinBoardComment.class);
        CoinBoardComment coinBoardComment1 = new CoinBoardComment();
        coinBoardComment1.setId(1L);
        CoinBoardComment coinBoardComment2 = new CoinBoardComment();
        coinBoardComment2.setId(coinBoardComment1.getId());
        assertThat(coinBoardComment1).isEqualTo(coinBoardComment2);
        coinBoardComment2.setId(2L);
        assertThat(coinBoardComment1).isNotEqualTo(coinBoardComment2);
        coinBoardComment1.setId(null);
        assertThat(coinBoardComment1).isNotEqualTo(coinBoardComment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoinBoardCommentDTO.class);
        CoinBoardCommentDTO coinBoardCommentDTO1 = new CoinBoardCommentDTO();
        coinBoardCommentDTO1.setId(1L);
        CoinBoardCommentDTO coinBoardCommentDTO2 = new CoinBoardCommentDTO();
        assertThat(coinBoardCommentDTO1).isNotEqualTo(coinBoardCommentDTO2);
        coinBoardCommentDTO2.setId(coinBoardCommentDTO1.getId());
        assertThat(coinBoardCommentDTO1).isEqualTo(coinBoardCommentDTO2);
        coinBoardCommentDTO2.setId(2L);
        assertThat(coinBoardCommentDTO1).isNotEqualTo(coinBoardCommentDTO2);
        coinBoardCommentDTO1.setId(null);
        assertThat(coinBoardCommentDTO1).isNotEqualTo(coinBoardCommentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coinBoardCommentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coinBoardCommentMapper.fromId(null)).isNull();
    }
}
