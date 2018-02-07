package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.AirDrop;
import io.iansoft.blockchain.repository.AirDropRepository;
import io.iansoft.blockchain.repository.search.AirDropSearchRepository;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.iansoft.blockchain.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AirDropResource REST controller.
 *
 * @see AirDropResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class AirDropResourceIntTest {

    private static final String DEFAULT_COIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COIN_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FINISH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    private static final String DEFAULT_TELEGRAM = "AAAAAAAAAA";
    private static final String UPDATED_TELEGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_REDDIT = "AAAAAAAAAA";
    private static final String UPDATED_REDDIT = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_AIRDROP_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_AIRDROP_PAGE = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_TALK = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_TALK = "BBBBBBBBBB";

    @Autowired
    private AirDropRepository airDropRepository;

    @Autowired
    private AirDropSearchRepository airDropSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAirDropMockMvc;

    private AirDrop airDrop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AirDropResource airDropResource = new AirDropResource(airDropRepository, airDropSearchRepository);
        this.restAirDropMockMvc = MockMvcBuilders.standaloneSetup(airDropResource)
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
    public static AirDrop createEntity(EntityManager em) {
        AirDrop airDrop = new AirDrop()
            .coinName(DEFAULT_COIN_NAME)
            .startDate(DEFAULT_START_DATE)
            .finishDate(DEFAULT_FINISH_DATE)
            .context(DEFAULT_CONTEXT)
            .value(DEFAULT_VALUE)
            .telegram(DEFAULT_TELEGRAM)
            .twitter(DEFAULT_TWITTER)
            .facebook(DEFAULT_FACEBOOK)
            .reddit(DEFAULT_REDDIT)
            .website(DEFAULT_WEBSITE)
            .airdropPage(DEFAULT_AIRDROP_PAGE)
            .bitcoinTalk(DEFAULT_BITCOIN_TALK);
        return airDrop;
    }

    @Before
    public void initTest() {
        airDropSearchRepository.deleteAll();
        airDrop = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirDrop() throws Exception {
        int databaseSizeBeforeCreate = airDropRepository.findAll().size();

        // Create the AirDrop
        restAirDropMockMvc.perform(post("/api/air-drops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airDrop)))
            .andExpect(status().isCreated());

        // Validate the AirDrop in the database
        List<AirDrop> airDropList = airDropRepository.findAll();
        assertThat(airDropList).hasSize(databaseSizeBeforeCreate + 1);
        AirDrop testAirDrop = airDropList.get(airDropList.size() - 1);
        assertThat(testAirDrop.getCoinName()).isEqualTo(DEFAULT_COIN_NAME);
        assertThat(testAirDrop.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAirDrop.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testAirDrop.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testAirDrop.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAirDrop.getTelegram()).isEqualTo(DEFAULT_TELEGRAM);
        assertThat(testAirDrop.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testAirDrop.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testAirDrop.getReddit()).isEqualTo(DEFAULT_REDDIT);
        assertThat(testAirDrop.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testAirDrop.getAirdropPage()).isEqualTo(DEFAULT_AIRDROP_PAGE);
        assertThat(testAirDrop.getBitcoinTalk()).isEqualTo(DEFAULT_BITCOIN_TALK);

        // Validate the AirDrop in Elasticsearch
        AirDrop airDropEs = airDropSearchRepository.findOne(testAirDrop.getId());
        assertThat(airDropEs).isEqualToIgnoringGivenFields(testAirDrop);
    }

    @Test
    @Transactional
    public void createAirDropWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airDropRepository.findAll().size();

        // Create the AirDrop with an existing ID
        airDrop.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirDropMockMvc.perform(post("/api/air-drops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airDrop)))
            .andExpect(status().isBadRequest());

        // Validate the AirDrop in the database
        List<AirDrop> airDropList = airDropRepository.findAll();
        assertThat(airDropList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCoinNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = airDropRepository.findAll().size();
        // set the field null
        airDrop.setCoinName(null);

        // Create the AirDrop, which fails.

        restAirDropMockMvc.perform(post("/api/air-drops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airDrop)))
            .andExpect(status().isBadRequest());

        List<AirDrop> airDropList = airDropRepository.findAll();
        assertThat(airDropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = airDropRepository.findAll().size();
        // set the field null
        airDrop.setStartDate(null);

        // Create the AirDrop, which fails.

        restAirDropMockMvc.perform(post("/api/air-drops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airDrop)))
            .andExpect(status().isBadRequest());

        List<AirDrop> airDropList = airDropRepository.findAll();
        assertThat(airDropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinishDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = airDropRepository.findAll().size();
        // set the field null
        airDrop.setFinishDate(null);

        // Create the AirDrop, which fails.

        restAirDropMockMvc.perform(post("/api/air-drops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airDrop)))
            .andExpect(status().isBadRequest());

        List<AirDrop> airDropList = airDropRepository.findAll();
        assertThat(airDropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAirDrops() throws Exception {
        // Initialize the database
        airDropRepository.saveAndFlush(airDrop);

        // Get all the airDropList
        restAirDropMockMvc.perform(get("/api/air-drops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airDrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].coinName").value(hasItem(DEFAULT_COIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].telegram").value(hasItem(DEFAULT_TELEGRAM.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].reddit").value(hasItem(DEFAULT_REDDIT.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].airdropPage").value(hasItem(DEFAULT_AIRDROP_PAGE.toString())))
            .andExpect(jsonPath("$.[*].bitcoinTalk").value(hasItem(DEFAULT_BITCOIN_TALK.toString())));
    }

    @Test
    @Transactional
    public void getAirDrop() throws Exception {
        // Initialize the database
        airDropRepository.saveAndFlush(airDrop);

        // Get the airDrop
        restAirDropMockMvc.perform(get("/api/air-drops/{id}", airDrop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airDrop.getId().intValue()))
            .andExpect(jsonPath("$.coinName").value(DEFAULT_COIN_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.finishDate").value(DEFAULT_FINISH_DATE.toString()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.telegram").value(DEFAULT_TELEGRAM.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.reddit").value(DEFAULT_REDDIT.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.airdropPage").value(DEFAULT_AIRDROP_PAGE.toString()))
            .andExpect(jsonPath("$.bitcoinTalk").value(DEFAULT_BITCOIN_TALK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAirDrop() throws Exception {
        // Get the airDrop
        restAirDropMockMvc.perform(get("/api/air-drops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirDrop() throws Exception {
        // Initialize the database
        airDropRepository.saveAndFlush(airDrop);
        airDropSearchRepository.save(airDrop);
        int databaseSizeBeforeUpdate = airDropRepository.findAll().size();

        // Update the airDrop
        AirDrop updatedAirDrop = airDropRepository.findOne(airDrop.getId());
        // Disconnect from session so that the updates on updatedAirDrop are not directly saved in db
        em.detach(updatedAirDrop);
        updatedAirDrop
            .coinName(UPDATED_COIN_NAME)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .context(UPDATED_CONTEXT)
            .value(UPDATED_VALUE)
            .telegram(UPDATED_TELEGRAM)
            .twitter(UPDATED_TWITTER)
            .facebook(UPDATED_FACEBOOK)
            .reddit(UPDATED_REDDIT)
            .website(UPDATED_WEBSITE)
            .airdropPage(UPDATED_AIRDROP_PAGE)
            .bitcoinTalk(UPDATED_BITCOIN_TALK);

        restAirDropMockMvc.perform(put("/api/air-drops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirDrop)))
            .andExpect(status().isOk());

        // Validate the AirDrop in the database
        List<AirDrop> airDropList = airDropRepository.findAll();
        assertThat(airDropList).hasSize(databaseSizeBeforeUpdate);
        AirDrop testAirDrop = airDropList.get(airDropList.size() - 1);
        assertThat(testAirDrop.getCoinName()).isEqualTo(UPDATED_COIN_NAME);
        assertThat(testAirDrop.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAirDrop.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testAirDrop.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testAirDrop.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAirDrop.getTelegram()).isEqualTo(UPDATED_TELEGRAM);
        assertThat(testAirDrop.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testAirDrop.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testAirDrop.getReddit()).isEqualTo(UPDATED_REDDIT);
        assertThat(testAirDrop.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testAirDrop.getAirdropPage()).isEqualTo(UPDATED_AIRDROP_PAGE);
        assertThat(testAirDrop.getBitcoinTalk()).isEqualTo(UPDATED_BITCOIN_TALK);

        // Validate the AirDrop in Elasticsearch
        AirDrop airDropEs = airDropSearchRepository.findOne(testAirDrop.getId());
        assertThat(airDropEs).isEqualToIgnoringGivenFields(testAirDrop);
    }

    @Test
    @Transactional
    public void updateNonExistingAirDrop() throws Exception {
        int databaseSizeBeforeUpdate = airDropRepository.findAll().size();

        // Create the AirDrop

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAirDropMockMvc.perform(put("/api/air-drops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airDrop)))
            .andExpect(status().isCreated());

        // Validate the AirDrop in the database
        List<AirDrop> airDropList = airDropRepository.findAll();
        assertThat(airDropList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAirDrop() throws Exception {
        // Initialize the database
        airDropRepository.saveAndFlush(airDrop);
        airDropSearchRepository.save(airDrop);
        int databaseSizeBeforeDelete = airDropRepository.findAll().size();

        // Get the airDrop
        restAirDropMockMvc.perform(delete("/api/air-drops/{id}", airDrop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean airDropExistsInEs = airDropSearchRepository.exists(airDrop.getId());
        assertThat(airDropExistsInEs).isFalse();

        // Validate the database is empty
        List<AirDrop> airDropList = airDropRepository.findAll();
        assertThat(airDropList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAirDrop() throws Exception {
        // Initialize the database
        airDropRepository.saveAndFlush(airDrop);
        airDropSearchRepository.save(airDrop);

        // Search the airDrop
        restAirDropMockMvc.perform(get("/api/_search/air-drops?query=id:" + airDrop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airDrop.getId().intValue())))
            .andExpect(jsonPath("$.[*].coinName").value(hasItem(DEFAULT_COIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].telegram").value(hasItem(DEFAULT_TELEGRAM.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].reddit").value(hasItem(DEFAULT_REDDIT.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].airdropPage").value(hasItem(DEFAULT_AIRDROP_PAGE.toString())))
            .andExpect(jsonPath("$.[*].bitcoinTalk").value(hasItem(DEFAULT_BITCOIN_TALK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AirDrop.class);
        AirDrop airDrop1 = new AirDrop();
        airDrop1.setId(1L);
        AirDrop airDrop2 = new AirDrop();
        airDrop2.setId(airDrop1.getId());
        assertThat(airDrop1).isEqualTo(airDrop2);
        airDrop2.setId(2L);
        assertThat(airDrop1).isNotEqualTo(airDrop2);
        airDrop1.setId(null);
        assertThat(airDrop1).isNotEqualTo(airDrop2);
    }
}
