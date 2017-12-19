package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.Coin;
import io.iansoft.blockchain.repository.CoinRepository;
import io.iansoft.blockchain.service.CoinService;
import io.iansoft.blockchain.repository.search.CoinSearchRepository;
import io.iansoft.blockchain.service.dto.CoinDTO;
import io.iansoft.blockchain.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
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

import io.iansoft.blockchain.domain.enumeration.ConsensusAlgorithms;
/**
 * Test class for the CoinResource REST controller.
 *
 * @see CoinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class CoinResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FOUNDER = "AAAAAAAAAA";
    private static final String UPDATED_FOUNDER = "BBBBBBBBBB";

    private static final ConsensusAlgorithms DEFAULT_CONSENSUS_ALGORITHMS = ConsensusAlgorithms.POS;
    private static final ConsensusAlgorithms UPDATED_CONSENSUS_ALGORITHMS = ConsensusAlgorithms.POW;

    private static final String DEFAULT_HOMEPAGE = "AAAAAAAAAA";
    private static final String UPDATED_HOMEPAGE = "BBBBBBBBBB";

    private static final String DEFAULT_WHITE_PAPER = "AAAAAAAAAA";
    private static final String UPDATED_WHITE_PAPER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RELEASEAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RELEASEAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CoinRepository coinRepository;

//    @Autowired
//    private CoinMapper coinMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CoinService coinService;

    @Autowired
    private CoinSearchRepository coinSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoinMockMvc;

    private Coin coin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CoinResource coinResource = new CoinResource(coinService);
        this.restCoinMockMvc = MockMvcBuilders.standaloneSetup(coinResource)
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
    public static Coin createEntity(EntityManager em) {
        Coin coin = new Coin()
            .name(DEFAULT_NAME)
            .founder(DEFAULT_FOUNDER)
            .consensusAlgorithms(DEFAULT_CONSENSUS_ALGORITHMS)
            .homepage(DEFAULT_HOMEPAGE)
            .whitePaper(DEFAULT_WHITE_PAPER)
            .context(DEFAULT_CONTEXT)
            .releaseat(DEFAULT_RELEASEAT);
            //.(DEFAULT_CREATEDAT)
            //.updatedat(DEFAULT_UPDATEDAT);
        return coin;
    }

    @Before
    public void initTest() {
        coinSearchRepository.deleteAll();
        coin = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoin() throws Exception {
        int databaseSizeBeforeCreate = coinRepository.findAll().size();

        // Create the Coin
        CoinDTO coinDTO = modelMapper.map(coin,CoinDTO.class);
        restCoinMockMvc.perform(post("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinDTO)))
            .andExpect(status().isCreated());

        // Validate the Coin in the database
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeCreate + 1);
        Coin testCoin = coinList.get(coinList.size() - 1);
        assertThat(testCoin.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoin.getFounder()).isEqualTo(DEFAULT_FOUNDER);
        assertThat(testCoin.getConsensusAlgorithms()).isEqualTo(DEFAULT_CONSENSUS_ALGORITHMS);
        assertThat(testCoin.getHomepage()).isEqualTo(DEFAULT_HOMEPAGE);
        assertThat(testCoin.getWhitePaper()).isEqualTo(DEFAULT_WHITE_PAPER);
        assertThat(testCoin.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testCoin.getReleaseat()).isEqualTo(DEFAULT_RELEASEAT);
//        assertThat(testCoin.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
//        assertThat(testCoin.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);

        // Validate the Coin in Elasticsearch
        Coin coinEs = coinSearchRepository.findOne(testCoin.getId());
        assertThat(coinEs).isEqualToComparingFieldByField(testCoin);
    }

    @Test
    @Transactional
    public void createCoinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coinRepository.findAll().size();

        // Create the Coin with an existing ID
        coin.setId(1L);
        CoinDTO coinDTO = modelMapper.map(coin,CoinDTO.class);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoinMockMvc.perform(post("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = coinRepository.findAll().size();
        // set the field null
        coin.setName(null);

        // Create the Coin, which fails.
        CoinDTO coinDTO = modelMapper.map(coin,CoinDTO.class);

        restCoinMockMvc.perform(post("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinDTO)))
            .andExpect(status().isBadRequest());

        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoins() throws Exception {
        // Initialize the database
        coinRepository.saveAndFlush(coin);

        // Get all the coinList
        restCoinMockMvc.perform(get("/api/coins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].founder").value(hasItem(DEFAULT_FOUNDER.toString())))
            .andExpect(jsonPath("$.[*].consensusAlgorithms").value(hasItem(DEFAULT_CONSENSUS_ALGORITHMS.toString())))
            .andExpect(jsonPath("$.[*].homepage").value(hasItem(DEFAULT_HOMEPAGE.toString())))
            .andExpect(jsonPath("$.[*].whitePaper").value(hasItem(DEFAULT_WHITE_PAPER.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].releaseat").value(hasItem(sameInstant(DEFAULT_RELEASEAT))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(sameInstant(DEFAULT_UPDATEDAT))));
    }

    @Test
    @Transactional
    public void getCoin() throws Exception {
        // Initialize the database
        coinRepository.saveAndFlush(coin);

        // Get the coin
        restCoinMockMvc.perform(get("/api/coins/{id}", coin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.founder").value(DEFAULT_FOUNDER.toString()))
            .andExpect(jsonPath("$.consensusAlgorithms").value(DEFAULT_CONSENSUS_ALGORITHMS.toString()))
            .andExpect(jsonPath("$.homepage").value(DEFAULT_HOMEPAGE.toString()))
            .andExpect(jsonPath("$.whitePaper").value(DEFAULT_WHITE_PAPER.toString()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.releaseat").value(sameInstant(DEFAULT_RELEASEAT)))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)))
            .andExpect(jsonPath("$.updatedat").value(sameInstant(DEFAULT_UPDATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingCoin() throws Exception {
        // Get the coin
        restCoinMockMvc.perform(get("/api/coins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoin() throws Exception {
        // Initialize the database
        coinRepository.saveAndFlush(coin);
        coinSearchRepository.save(coin);
        int databaseSizeBeforeUpdate = coinRepository.findAll().size();

        // Update the coin
        Coin updatedCoin = coinRepository.findOne(coin.getId());
        updatedCoin
            .name(UPDATED_NAME)
            .founder(UPDATED_FOUNDER)
            .consensusAlgorithms(UPDATED_CONSENSUS_ALGORITHMS)
            .homepage(UPDATED_HOMEPAGE)
            .whitePaper(UPDATED_WHITE_PAPER)
            .context(UPDATED_CONTEXT)
            .releaseat(UPDATED_RELEASEAT);
//            .createdat(UPDATED_CREATEDAT)
//            .updatedat(UPDATED_UPDATEDAT);
        CoinDTO coinDTO = modelMapper.map(coin,CoinDTO.class);

        restCoinMockMvc.perform(put("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinDTO)))
            .andExpect(status().isOk());

        // Validate the Coin in the database
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeUpdate);
        Coin testCoin = coinList.get(coinList.size() - 1);
        assertThat(testCoin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoin.getFounder()).isEqualTo(UPDATED_FOUNDER);
        assertThat(testCoin.getConsensusAlgorithms()).isEqualTo(UPDATED_CONSENSUS_ALGORITHMS);
        assertThat(testCoin.getHomepage()).isEqualTo(UPDATED_HOMEPAGE);
        assertThat(testCoin.getWhitePaper()).isEqualTo(UPDATED_WHITE_PAPER);
        assertThat(testCoin.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testCoin.getReleaseat()).isEqualTo(UPDATED_RELEASEAT);
//        assertThat(testCoin.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
//        assertThat(testCoin.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);

        // Validate the Coin in Elasticsearch
        Coin coinEs = coinSearchRepository.findOne(testCoin.getId());
        assertThat(coinEs).isEqualToComparingFieldByField(testCoin);
    }

    @Test
    @Transactional
    public void updateNonExistingCoin() throws Exception {
        int databaseSizeBeforeUpdate = coinRepository.findAll().size();

        // Create the Coin
        CoinDTO coinDTO = modelMapper.map(coin,CoinDTO.class);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoinMockMvc.perform(put("/api/coins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinDTO)))
            .andExpect(status().isCreated());

        // Validate the Coin in the database
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoin() throws Exception {
        // Initialize the database
        coinRepository.saveAndFlush(coin);
        coinSearchRepository.save(coin);
        int databaseSizeBeforeDelete = coinRepository.findAll().size();

        // Get the coin
        restCoinMockMvc.perform(delete("/api/coins/{id}", coin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean coinExistsInEs = coinSearchRepository.exists(coin.getId());
        assertThat(coinExistsInEs).isFalse();

        // Validate the database is empty
        List<Coin> coinList = coinRepository.findAll();
        assertThat(coinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCoin() throws Exception {
        // Initialize the database
        coinRepository.saveAndFlush(coin);
        coinSearchRepository.save(coin);

        // Search the coin
        restCoinMockMvc.perform(get("/api/_search/coins?query=id:" + coin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].founder").value(hasItem(DEFAULT_FOUNDER.toString())))
            .andExpect(jsonPath("$.[*].consensusAlgorithms").value(hasItem(DEFAULT_CONSENSUS_ALGORITHMS.toString())))
            .andExpect(jsonPath("$.[*].homepage").value(hasItem(DEFAULT_HOMEPAGE.toString())))
            .andExpect(jsonPath("$.[*].whitePaper").value(hasItem(DEFAULT_WHITE_PAPER.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].releaseat").value(hasItem(sameInstant(DEFAULT_RELEASEAT))))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(sameInstant(DEFAULT_UPDATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coin.class);
        Coin coin1 = new Coin();
        coin1.setId(1L);
        Coin coin2 = new Coin();
        coin2.setId(coin1.getId());
        assertThat(coin1).isEqualTo(coin2);
        coin2.setId(2L);
        assertThat(coin1).isNotEqualTo(coin2);
        coin1.setId(null);
        assertThat(coin1).isNotEqualTo(coin2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoinDTO.class);
        CoinDTO coinDTO1 = new CoinDTO();
        coinDTO1.setId(1L);
        CoinDTO coinDTO2 = new CoinDTO();
        assertThat(coinDTO1).isNotEqualTo(coinDTO2);
        coinDTO2.setId(coinDTO1.getId());
        assertThat(coinDTO1).isEqualTo(coinDTO2);
        coinDTO2.setId(2L);
        assertThat(coinDTO1).isNotEqualTo(coinDTO2);
        coinDTO1.setId(null);
        assertThat(coinDTO1).isNotEqualTo(coinDTO2);
    }

//    @Test
//    @Transactional
//    public void testEntityFromId() {
//        assertThat(coinMapper.fromId(42L).getId()).isEqualTo(42);
//        assertThat(coinMapper.fromId(null)).isNull();
//    }
}
