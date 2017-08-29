package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.Bitfinex;
import io.iansoft.blockchain.repository.BitfinexRepository;
import io.iansoft.blockchain.service.BitfinexService;
import io.iansoft.blockchain.repository.search.BitfinexSearchRepository;
import io.iansoft.blockchain.service.dto.BitfinexDTO;
import io.iansoft.blockchain.service.mapper.BitfinexMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BitfinexResource REST controller.
 *
 * @see BitfinexResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class BitfinexResourceIntTest {

    private static final String DEFAULT_MID = "AAAAAAAAAA";
    private static final String UPDATED_MID = "BBBBBBBBBB";

    private static final String DEFAULT_BID = "AAAAAAAAAA";
    private static final String UPDATED_BID = "BBBBBBBBBB";

    private static final String DEFAULT_ASK = "AAAAAAAAAA";
    private static final String UPDATED_ASK = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_LOW = "AAAAAAAAAA";
    private static final String UPDATED_LOW = "BBBBBBBBBB";

    private static final String DEFAULT_HIGH = "AAAAAAAAAA";
    private static final String UPDATED_HIGH = "BBBBBBBBBB";

    private static final String DEFAULT_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_TIMESTAMP = "AAAAAAAAAA";
    private static final String UPDATED_TIMESTAMP = "BBBBBBBBBB";

    @Autowired
    private BitfinexRepository bitfinexRepository;

    @Autowired
    private BitfinexMapper bitfinexMapper;

    @Autowired
    private BitfinexService bitfinexService;

    @Autowired
    private BitfinexSearchRepository bitfinexSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBitfinexMockMvc;

    private Bitfinex bitfinex;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BitfinexResource bitfinexResource = new BitfinexResource(bitfinexService);
        this.restBitfinexMockMvc = MockMvcBuilders.standaloneSetup(bitfinexResource)
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
    public static Bitfinex createEntity(EntityManager em) {
        Bitfinex bitfinex = new Bitfinex()
            .mid(DEFAULT_MID)
            .bid(DEFAULT_BID)
            .ask(DEFAULT_ASK)
            .last_price(DEFAULT_LAST_PRICE)
            .low(DEFAULT_LOW)
            .high(DEFAULT_HIGH)
            .volume(DEFAULT_VOLUME)
            .timestamp(DEFAULT_TIMESTAMP);
        return bitfinex;
    }

    @Before
    public void initTest() {
        bitfinexSearchRepository.deleteAll();
        bitfinex = createEntity(em);
    }

    @Test
    @Transactional
    public void createBitfinex() throws Exception {
        int databaseSizeBeforeCreate = bitfinexRepository.findAll().size();

        // Create the Bitfinex
        BitfinexDTO bitfinexDTO = bitfinexMapper.toDto(bitfinex);
        restBitfinexMockMvc.perform(post("/api/bitfinexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitfinexDTO)))
            .andExpect(status().isCreated());

        // Validate the Bitfinex in the database
        List<Bitfinex> bitfinexList = bitfinexRepository.findAll();
        assertThat(bitfinexList).hasSize(databaseSizeBeforeCreate + 1);
        Bitfinex testBitfinex = bitfinexList.get(bitfinexList.size() - 1);
        assertThat(testBitfinex.getMid()).isEqualTo(DEFAULT_MID);
        assertThat(testBitfinex.getBid()).isEqualTo(DEFAULT_BID);
        assertThat(testBitfinex.getAsk()).isEqualTo(DEFAULT_ASK);
        assertThat(testBitfinex.getLast_price()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testBitfinex.getLow()).isEqualTo(DEFAULT_LOW);
        assertThat(testBitfinex.getHigh()).isEqualTo(DEFAULT_HIGH);
        assertThat(testBitfinex.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testBitfinex.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the Bitfinex in Elasticsearch
        Bitfinex bitfinexEs = bitfinexSearchRepository.findOne(testBitfinex.getId());
        assertThat(bitfinexEs).isEqualToComparingFieldByField(testBitfinex);
    }

    @Test
    @Transactional
    public void createBitfinexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bitfinexRepository.findAll().size();

        // Create the Bitfinex with an existing ID
        bitfinex.setId(1L);
        BitfinexDTO bitfinexDTO = bitfinexMapper.toDto(bitfinex);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBitfinexMockMvc.perform(post("/api/bitfinexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitfinexDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Bitfinex> bitfinexList = bitfinexRepository.findAll();
        assertThat(bitfinexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLast_priceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitfinexRepository.findAll().size();
        // set the field null
        bitfinex.setLast_price(null);

        // Create the Bitfinex, which fails.
        BitfinexDTO bitfinexDTO = bitfinexMapper.toDto(bitfinex);

        restBitfinexMockMvc.perform(post("/api/bitfinexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitfinexDTO)))
            .andExpect(status().isBadRequest());

        List<Bitfinex> bitfinexList = bitfinexRepository.findAll();
        assertThat(bitfinexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBitfinexes() throws Exception {
        // Initialize the database
        bitfinexRepository.saveAndFlush(bitfinex);

        // Get all the bitfinexList
        restBitfinexMockMvc.perform(get("/api/bitfinexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bitfinex.getId().intValue())))
            .andExpect(jsonPath("$.[*].mid").value(hasItem(DEFAULT_MID.toString())))
            .andExpect(jsonPath("$.[*].bid").value(hasItem(DEFAULT_BID.toString())))
            .andExpect(jsonPath("$.[*].ask").value(hasItem(DEFAULT_ASK.toString())))
            .andExpect(jsonPath("$.[*].last_price").value(hasItem(DEFAULT_LAST_PRICE.toString())))
            .andExpect(jsonPath("$.[*].low").value(hasItem(DEFAULT_LOW.toString())))
            .andExpect(jsonPath("$.[*].high").value(hasItem(DEFAULT_HIGH.toString())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }

    @Test
    @Transactional
    public void getBitfinex() throws Exception {
        // Initialize the database
        bitfinexRepository.saveAndFlush(bitfinex);

        // Get the bitfinex
        restBitfinexMockMvc.perform(get("/api/bitfinexes/{id}", bitfinex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bitfinex.getId().intValue()))
            .andExpect(jsonPath("$.mid").value(DEFAULT_MID.toString()))
            .andExpect(jsonPath("$.bid").value(DEFAULT_BID.toString()))
            .andExpect(jsonPath("$.ask").value(DEFAULT_ASK.toString()))
            .andExpect(jsonPath("$.last_price").value(DEFAULT_LAST_PRICE.toString()))
            .andExpect(jsonPath("$.low").value(DEFAULT_LOW.toString()))
            .andExpect(jsonPath("$.high").value(DEFAULT_HIGH.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBitfinex() throws Exception {
        // Get the bitfinex
        restBitfinexMockMvc.perform(get("/api/bitfinexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBitfinex() throws Exception {
        // Initialize the database
        bitfinexRepository.saveAndFlush(bitfinex);
        bitfinexSearchRepository.save(bitfinex);
        int databaseSizeBeforeUpdate = bitfinexRepository.findAll().size();

        // Update the bitfinex
        Bitfinex updatedBitfinex = bitfinexRepository.findOne(bitfinex.getId());
        updatedBitfinex
            .mid(UPDATED_MID)
            .bid(UPDATED_BID)
            .ask(UPDATED_ASK)
            .last_price(UPDATED_LAST_PRICE)
            .low(UPDATED_LOW)
            .high(UPDATED_HIGH)
            .volume(UPDATED_VOLUME)
            .timestamp(UPDATED_TIMESTAMP);
        BitfinexDTO bitfinexDTO = bitfinexMapper.toDto(updatedBitfinex);

        restBitfinexMockMvc.perform(put("/api/bitfinexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitfinexDTO)))
            .andExpect(status().isOk());

        // Validate the Bitfinex in the database
        List<Bitfinex> bitfinexList = bitfinexRepository.findAll();
        assertThat(bitfinexList).hasSize(databaseSizeBeforeUpdate);
        Bitfinex testBitfinex = bitfinexList.get(bitfinexList.size() - 1);
        assertThat(testBitfinex.getMid()).isEqualTo(UPDATED_MID);
        assertThat(testBitfinex.getBid()).isEqualTo(UPDATED_BID);
        assertThat(testBitfinex.getAsk()).isEqualTo(UPDATED_ASK);
        assertThat(testBitfinex.getLast_price()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testBitfinex.getLow()).isEqualTo(UPDATED_LOW);
        assertThat(testBitfinex.getHigh()).isEqualTo(UPDATED_HIGH);
        assertThat(testBitfinex.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testBitfinex.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the Bitfinex in Elasticsearch
        Bitfinex bitfinexEs = bitfinexSearchRepository.findOne(testBitfinex.getId());
        assertThat(bitfinexEs).isEqualToComparingFieldByField(testBitfinex);
    }

    @Test
    @Transactional
    public void updateNonExistingBitfinex() throws Exception {
        int databaseSizeBeforeUpdate = bitfinexRepository.findAll().size();

        // Create the Bitfinex
        BitfinexDTO bitfinexDTO = bitfinexMapper.toDto(bitfinex);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBitfinexMockMvc.perform(put("/api/bitfinexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitfinexDTO)))
            .andExpect(status().isCreated());

        // Validate the Bitfinex in the database
        List<Bitfinex> bitfinexList = bitfinexRepository.findAll();
        assertThat(bitfinexList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBitfinex() throws Exception {
        // Initialize the database
        bitfinexRepository.saveAndFlush(bitfinex);
        bitfinexSearchRepository.save(bitfinex);
        int databaseSizeBeforeDelete = bitfinexRepository.findAll().size();

        // Get the bitfinex
        restBitfinexMockMvc.perform(delete("/api/bitfinexes/{id}", bitfinex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bitfinexExistsInEs = bitfinexSearchRepository.exists(bitfinex.getId());
        assertThat(bitfinexExistsInEs).isFalse();

        // Validate the database is empty
        List<Bitfinex> bitfinexList = bitfinexRepository.findAll();
        assertThat(bitfinexList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBitfinex() throws Exception {
        // Initialize the database
        bitfinexRepository.saveAndFlush(bitfinex);
        bitfinexSearchRepository.save(bitfinex);

        // Search the bitfinex
        restBitfinexMockMvc.perform(get("/api/_search/bitfinexes?query=id:" + bitfinex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bitfinex.getId().intValue())))
            .andExpect(jsonPath("$.[*].mid").value(hasItem(DEFAULT_MID.toString())))
            .andExpect(jsonPath("$.[*].bid").value(hasItem(DEFAULT_BID.toString())))
            .andExpect(jsonPath("$.[*].ask").value(hasItem(DEFAULT_ASK.toString())))
            .andExpect(jsonPath("$.[*].last_price").value(hasItem(DEFAULT_LAST_PRICE.toString())))
            .andExpect(jsonPath("$.[*].low").value(hasItem(DEFAULT_LOW.toString())))
            .andExpect(jsonPath("$.[*].high").value(hasItem(DEFAULT_HIGH.toString())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bitfinex.class);
        Bitfinex bitfinex1 = new Bitfinex();
        bitfinex1.setId(1L);
        Bitfinex bitfinex2 = new Bitfinex();
        bitfinex2.setId(bitfinex1.getId());
        assertThat(bitfinex1).isEqualTo(bitfinex2);
        bitfinex2.setId(2L);
        assertThat(bitfinex1).isNotEqualTo(bitfinex2);
        bitfinex1.setId(null);
        assertThat(bitfinex1).isNotEqualTo(bitfinex2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BitfinexDTO.class);
        BitfinexDTO bitfinexDTO1 = new BitfinexDTO();
        bitfinexDTO1.setId(1L);
        BitfinexDTO bitfinexDTO2 = new BitfinexDTO();
        assertThat(bitfinexDTO1).isNotEqualTo(bitfinexDTO2);
        bitfinexDTO2.setId(bitfinexDTO1.getId());
        assertThat(bitfinexDTO1).isEqualTo(bitfinexDTO2);
        bitfinexDTO2.setId(2L);
        assertThat(bitfinexDTO1).isNotEqualTo(bitfinexDTO2);
        bitfinexDTO1.setId(null);
        assertThat(bitfinexDTO1).isNotEqualTo(bitfinexDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bitfinexMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bitfinexMapper.fromId(null)).isNull();
    }
}
