package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.CoinBoard;
import io.iansoft.blockchain.repository.CoinBoardRepository;
import io.iansoft.blockchain.service.CoinBoardService;
import io.iansoft.blockchain.repository.search.CoinBoardSearchRepository;
import io.iansoft.blockchain.service.dto.CoinBoardDTO;
import io.iansoft.blockchain.service.mapper.CoinBoardMapper;
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
import static io.iansoft.blockchain.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.iansoft.blockchain.domain.enumeration.CoinBoardType;
/**
 * Test class for the CoinBoardResource REST controller.
 *
 * @see CoinBoardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class CoinBoardResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final CoinBoardType DEFAULT_CONIN_BOARD_TYPE = CoinBoardType.FREE;
    private static final CoinBoardType UPDATED_CONIN_BOARD_TYPE = CoinBoardType.INQUIRY;

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CoinBoardRepository coinBoardRepository;

    @Autowired
    private CoinBoardMapper coinBoardMapper;

    @Autowired
    private CoinBoardService coinBoardService;

    @Autowired
    private CoinBoardSearchRepository coinBoardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoinBoardMockMvc;

    private CoinBoard coinBoard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoinBoardResource coinBoardResource = new CoinBoardResource(coinBoardService);
        this.restCoinBoardMockMvc = MockMvcBuilders.standaloneSetup(coinBoardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoinBoard createEntity(EntityManager em) {
        CoinBoard coinBoard = new CoinBoard()
            .title(DEFAULT_TITLE)
            .context(DEFAULT_CONTEXT)
            .coninBoardType(DEFAULT_CONIN_BOARD_TYPE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT);
        return coinBoard;
    }

    @Before
    public void initTest() {
        coinBoardSearchRepository.deleteAll();
        coinBoard = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoinBoard() throws Exception {
        int databaseSizeBeforeCreate = coinBoardRepository.findAll().size();

        // Create the CoinBoard
        CoinBoardDTO coinBoardDTO = coinBoardMapper.toDto(coinBoard);
        restCoinBoardMockMvc.perform(post("/api/coin-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinBoardDTO)))
            .andExpect(status().isCreated());

        // Validate the CoinBoard in the database
        List<CoinBoard> coinBoardList = coinBoardRepository.findAll();
        assertThat(coinBoardList).hasSize(databaseSizeBeforeCreate + 1);
        CoinBoard testCoinBoard = coinBoardList.get(coinBoardList.size() - 1);
        assertThat(testCoinBoard.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCoinBoard.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testCoinBoard.getConinBoardType()).isEqualTo(DEFAULT_CONIN_BOARD_TYPE);
        assertThat(testCoinBoard.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testCoinBoard.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);

        // Validate the CoinBoard in Elasticsearch
        CoinBoard coinBoardEs = coinBoardSearchRepository.findOne(testCoinBoard.getId());
        assertThat(testCoinBoard.getCreatedat()).isEqualTo(testCoinBoard.getCreatedat());
        assertThat(testCoinBoard.getUpdatedat()).isEqualTo(testCoinBoard.getUpdatedat());
        assertThat(coinBoardEs).isEqualToIgnoringGivenFields(testCoinBoard, "createdat", "updatedat");
    }

    @Test
    @Transactional
    public void createCoinBoardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coinBoardRepository.findAll().size();

        // Create the CoinBoard with an existing ID
        coinBoard.setId(1L);
        CoinBoardDTO coinBoardDTO = coinBoardMapper.toDto(coinBoard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoinBoardMockMvc.perform(post("/api/coin-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinBoardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoinBoard in the database
        List<CoinBoard> coinBoardList = coinBoardRepository.findAll();
        assertThat(coinBoardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoinBoards() throws Exception {
        // Initialize the database
        coinBoardRepository.saveAndFlush(coinBoard);

        // Get all the coinBoardList
        restCoinBoardMockMvc.perform(get("/api/coin-boards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coinBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].coninBoardType").value(hasItem(DEFAULT_CONIN_BOARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(sameInstant(DEFAULT_UPDATEDAT))));
    }

    @Test
    @Transactional
    public void getCoinBoard() throws Exception {
        // Initialize the database
        coinBoardRepository.saveAndFlush(coinBoard);

        // Get the coinBoard
        restCoinBoardMockMvc.perform(get("/api/coin-boards/{id}", coinBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coinBoard.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.coninBoardType").value(DEFAULT_CONIN_BOARD_TYPE.toString()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)))
            .andExpect(jsonPath("$.updatedat").value(sameInstant(DEFAULT_UPDATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingCoinBoard() throws Exception {
        // Get the coinBoard
        restCoinBoardMockMvc.perform(get("/api/coin-boards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoinBoard() throws Exception {
        // Initialize the database
        coinBoardRepository.saveAndFlush(coinBoard);
        coinBoardSearchRepository.save(coinBoard);
        int databaseSizeBeforeUpdate = coinBoardRepository.findAll().size();

        // Update the coinBoard
        CoinBoard updatedCoinBoard = coinBoardRepository.findOne(coinBoard.getId());
        // Disconnect from session so that the updates on updatedCoinBoard are not directly saved in db
        em.detach(updatedCoinBoard);
        updatedCoinBoard
            .title(UPDATED_TITLE)
            .context(UPDATED_CONTEXT)
            .coninBoardType(UPDATED_CONIN_BOARD_TYPE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT);
        CoinBoardDTO coinBoardDTO = coinBoardMapper.toDto(updatedCoinBoard);

        restCoinBoardMockMvc.perform(put("/api/coin-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinBoardDTO)))
            .andExpect(status().isOk());

        // Validate the CoinBoard in the database
        List<CoinBoard> coinBoardList = coinBoardRepository.findAll();
        assertThat(coinBoardList).hasSize(databaseSizeBeforeUpdate);
        CoinBoard testCoinBoard = coinBoardList.get(coinBoardList.size() - 1);
        assertThat(testCoinBoard.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCoinBoard.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testCoinBoard.getConinBoardType()).isEqualTo(UPDATED_CONIN_BOARD_TYPE);
        assertThat(testCoinBoard.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testCoinBoard.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);

        // Validate the CoinBoard in Elasticsearch
        CoinBoard coinBoardEs = coinBoardSearchRepository.findOne(testCoinBoard.getId());
        assertThat(testCoinBoard.getCreatedat()).isEqualTo(testCoinBoard.getCreatedat());
        assertThat(testCoinBoard.getUpdatedat()).isEqualTo(testCoinBoard.getUpdatedat());
        assertThat(coinBoardEs).isEqualToIgnoringGivenFields(testCoinBoard, "createdat", "updatedat");
    }

    @Test
    @Transactional
    public void updateNonExistingCoinBoard() throws Exception {
        int databaseSizeBeforeUpdate = coinBoardRepository.findAll().size();

        // Create the CoinBoard
        CoinBoardDTO coinBoardDTO = coinBoardMapper.toDto(coinBoard);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoinBoardMockMvc.perform(put("/api/coin-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinBoardDTO)))
            .andExpect(status().isCreated());

        // Validate the CoinBoard in the database
        List<CoinBoard> coinBoardList = coinBoardRepository.findAll();
        assertThat(coinBoardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoinBoard() throws Exception {
        // Initialize the database
        coinBoardRepository.saveAndFlush(coinBoard);
        coinBoardSearchRepository.save(coinBoard);
        int databaseSizeBeforeDelete = coinBoardRepository.findAll().size();

        // Get the coinBoard
        restCoinBoardMockMvc.perform(delete("/api/coin-boards/{id}", coinBoard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean coinBoardExistsInEs = coinBoardSearchRepository.exists(coinBoard.getId());
        assertThat(coinBoardExistsInEs).isFalse();

        // Validate the database is empty
        List<CoinBoard> coinBoardList = coinBoardRepository.findAll();
        assertThat(coinBoardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCoinBoard() throws Exception {
        // Initialize the database
        coinBoardRepository.saveAndFlush(coinBoard);
        coinBoardSearchRepository.save(coinBoard);

        // Search the coinBoard
        restCoinBoardMockMvc.perform(get("/api/_search/coin-boards?query=id:" + coinBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coinBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].coninBoardType").value(hasItem(DEFAULT_CONIN_BOARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(sameInstant(DEFAULT_UPDATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoinBoard.class);
        CoinBoard coinBoard1 = new CoinBoard();
        coinBoard1.setId(1L);
        CoinBoard coinBoard2 = new CoinBoard();
        coinBoard2.setId(coinBoard1.getId());
        assertThat(coinBoard1).isEqualTo(coinBoard2);
        coinBoard2.setId(2L);
        assertThat(coinBoard1).isNotEqualTo(coinBoard2);
        coinBoard1.setId(null);
        assertThat(coinBoard1).isNotEqualTo(coinBoard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoinBoardDTO.class);
        CoinBoardDTO coinBoardDTO1 = new CoinBoardDTO();
        coinBoardDTO1.setId(1L);
        CoinBoardDTO coinBoardDTO2 = new CoinBoardDTO();
        assertThat(coinBoardDTO1).isNotEqualTo(coinBoardDTO2);
        coinBoardDTO2.setId(coinBoardDTO1.getId());
        assertThat(coinBoardDTO1).isEqualTo(coinBoardDTO2);
        coinBoardDTO2.setId(2L);
        assertThat(coinBoardDTO1).isNotEqualTo(coinBoardDTO2);
        coinBoardDTO1.setId(null);
        assertThat(coinBoardDTO1).isNotEqualTo(coinBoardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coinBoardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coinBoardMapper.fromId(null)).isNull();
    }
}
