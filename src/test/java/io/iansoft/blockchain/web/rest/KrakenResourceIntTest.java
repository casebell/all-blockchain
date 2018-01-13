package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.Kraken;
import io.iansoft.blockchain.repository.KrakenRepository;
import io.iansoft.blockchain.service.KrakenService;
import io.iansoft.blockchain.repository.search.KrakenSearchRepository;
import io.iansoft.blockchain.service.dto.KrakenDTO;
import io.iansoft.blockchain.service.mapper.KrakenMapper;
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

/**
 * Test class for the KrakenResource REST controller.
 *
 * @see KrakenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class KrakenResourceIntTest {

    private static final String DEFAULT_LAST = "AAAAAAAAAA";
    private static final String UPDATED_LAST = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private KrakenRepository krakenRepository;

    @Autowired
    private KrakenMapper krakenMapper;

    @Autowired
    private KrakenService krakenService;

    @Autowired
    private KrakenSearchRepository krakenSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKrakenMockMvc;

    private Kraken kraken;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KrakenResource krakenResource = new KrakenResource(krakenService);
        this.restKrakenMockMvc = MockMvcBuilders.standaloneSetup(krakenResource)
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
    public static Kraken createEntity(EntityManager em) {
        Kraken kraken = new Kraken()
            .last(DEFAULT_LAST)
            .createdat(DEFAULT_CREATEDAT);
        return kraken;
    }

    @Before
    public void initTest() {
        krakenSearchRepository.deleteAll();
        kraken = createEntity(em);
    }

    @Test
    @Transactional
    public void createKraken() throws Exception {
        int databaseSizeBeforeCreate = krakenRepository.findAll().size();

        // Create the Kraken
        KrakenDTO krakenDTO = krakenMapper.toDto(kraken);
        restKrakenMockMvc.perform(post("/api/krakens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(krakenDTO)))
            .andExpect(status().isCreated());

        // Validate the Kraken in the database
        List<Kraken> krakenList = krakenRepository.findAll();
        assertThat(krakenList).hasSize(databaseSizeBeforeCreate + 1);
        Kraken testKraken = krakenList.get(krakenList.size() - 1);
        assertThat(testKraken.getLast()).isEqualTo(DEFAULT_LAST);
        assertThat(testKraken.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);

        // Validate the Kraken in Elasticsearch
        Kraken krakenEs = krakenSearchRepository.findOne(testKraken.getId());
        assertThat(testKraken.getCreatedat()).isEqualTo(testKraken.getCreatedat());
        assertThat(krakenEs).isEqualToIgnoringGivenFields(testKraken, "createdat");
    }

    @Test
    @Transactional
    public void createKrakenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = krakenRepository.findAll().size();

        // Create the Kraken with an existing ID
        kraken.setId(1L);
        KrakenDTO krakenDTO = krakenMapper.toDto(kraken);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKrakenMockMvc.perform(post("/api/krakens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(krakenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kraken in the database
        List<Kraken> krakenList = krakenRepository.findAll();
        assertThat(krakenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLastIsRequired() throws Exception {
        int databaseSizeBeforeTest = krakenRepository.findAll().size();
        // set the field null
        kraken.setLast(null);

        // Create the Kraken, which fails.
        KrakenDTO krakenDTO = krakenMapper.toDto(kraken);

        restKrakenMockMvc.perform(post("/api/krakens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(krakenDTO)))
            .andExpect(status().isBadRequest());

        List<Kraken> krakenList = krakenRepository.findAll();
        assertThat(krakenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = krakenRepository.findAll().size();
        // set the field null
        kraken.setCreatedat(null);

        // Create the Kraken, which fails.
        KrakenDTO krakenDTO = krakenMapper.toDto(kraken);

        restKrakenMockMvc.perform(post("/api/krakens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(krakenDTO)))
            .andExpect(status().isBadRequest());

        List<Kraken> krakenList = krakenRepository.findAll();
        assertThat(krakenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKrakens() throws Exception {
        // Initialize the database
        krakenRepository.saveAndFlush(kraken);

        // Get all the krakenList
        restKrakenMockMvc.perform(get("/api/krakens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kraken.getId().intValue())))
            .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void getKraken() throws Exception {
        // Initialize the database
        krakenRepository.saveAndFlush(kraken);

        // Get the kraken
        restKrakenMockMvc.perform(get("/api/krakens/{id}", kraken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kraken.getId().intValue()))
            .andExpect(jsonPath("$.last").value(DEFAULT_LAST.toString()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingKraken() throws Exception {
        // Get the kraken
        restKrakenMockMvc.perform(get("/api/krakens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKraken() throws Exception {
        // Initialize the database
        krakenRepository.saveAndFlush(kraken);
        krakenSearchRepository.save(kraken);
        int databaseSizeBeforeUpdate = krakenRepository.findAll().size();

        // Update the kraken
        Kraken updatedKraken = krakenRepository.findOne(kraken.getId());
        // Disconnect from session so that the updates on updatedKraken are not directly saved in db
        em.detach(updatedKraken);
        updatedKraken
            .last(UPDATED_LAST)
            .createdat(UPDATED_CREATEDAT);
        KrakenDTO krakenDTO = krakenMapper.toDto(updatedKraken);

        restKrakenMockMvc.perform(put("/api/krakens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(krakenDTO)))
            .andExpect(status().isOk());

        // Validate the Kraken in the database
        List<Kraken> krakenList = krakenRepository.findAll();
        assertThat(krakenList).hasSize(databaseSizeBeforeUpdate);
        Kraken testKraken = krakenList.get(krakenList.size() - 1);
        assertThat(testKraken.getLast()).isEqualTo(UPDATED_LAST);
        assertThat(testKraken.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);

        // Validate the Kraken in Elasticsearch
        Kraken krakenEs = krakenSearchRepository.findOne(testKraken.getId());
        assertThat(testKraken.getCreatedat()).isEqualTo(testKraken.getCreatedat());
        assertThat(krakenEs).isEqualToIgnoringGivenFields(testKraken, "createdat");
    }

    @Test
    @Transactional
    public void updateNonExistingKraken() throws Exception {
        int databaseSizeBeforeUpdate = krakenRepository.findAll().size();

        // Create the Kraken
        KrakenDTO krakenDTO = krakenMapper.toDto(kraken);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKrakenMockMvc.perform(put("/api/krakens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(krakenDTO)))
            .andExpect(status().isCreated());

        // Validate the Kraken in the database
        List<Kraken> krakenList = krakenRepository.findAll();
        assertThat(krakenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKraken() throws Exception {
        // Initialize the database
        krakenRepository.saveAndFlush(kraken);
        krakenSearchRepository.save(kraken);
        int databaseSizeBeforeDelete = krakenRepository.findAll().size();

        // Get the kraken
        restKrakenMockMvc.perform(delete("/api/krakens/{id}", kraken.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean krakenExistsInEs = krakenSearchRepository.exists(kraken.getId());
        assertThat(krakenExistsInEs).isFalse();

        // Validate the database is empty
        List<Kraken> krakenList = krakenRepository.findAll();
        assertThat(krakenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchKraken() throws Exception {
        // Initialize the database
        krakenRepository.saveAndFlush(kraken);
        krakenSearchRepository.save(kraken);

        // Search the kraken
        restKrakenMockMvc.perform(get("/api/_search/krakens?query=id:" + kraken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kraken.getId().intValue())))
            .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kraken.class);
        Kraken kraken1 = new Kraken();
        kraken1.setId(1L);
        Kraken kraken2 = new Kraken();
        kraken2.setId(kraken1.getId());
        assertThat(kraken1).isEqualTo(kraken2);
        kraken2.setId(2L);
        assertThat(kraken1).isNotEqualTo(kraken2);
        kraken1.setId(null);
        assertThat(kraken1).isNotEqualTo(kraken2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KrakenDTO.class);
        KrakenDTO krakenDTO1 = new KrakenDTO();
        krakenDTO1.setId(1L);
        KrakenDTO krakenDTO2 = new KrakenDTO();
        assertThat(krakenDTO1).isNotEqualTo(krakenDTO2);
        krakenDTO2.setId(krakenDTO1.getId());
        assertThat(krakenDTO1).isEqualTo(krakenDTO2);
        krakenDTO2.setId(2L);
        assertThat(krakenDTO1).isNotEqualTo(krakenDTO2);
        krakenDTO1.setId(null);
        assertThat(krakenDTO1).isNotEqualTo(krakenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(krakenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(krakenMapper.fromId(null)).isNull();
    }
}
