package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.Coinis;
import io.iansoft.blockchain.repository.CoinisRepository;
import io.iansoft.blockchain.service.CoinisService;
import io.iansoft.blockchain.repository.search.CoinisSearchRepository;
import io.iansoft.blockchain.service.dto.CoinisDTO;
import io.iansoft.blockchain.service.mapper.CoinisMapper;
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
 * Test class for the CoinisResource REST controller.
 *
 * @see CoinisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class CoinisResourceIntTest {

    private static final String DEFAULT_CLOSEPRICE = "AAAAAAAAAA";
    private static final String UPDATED_CLOSEPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHPRICE = "AAAAAAAAAA";
    private static final String UPDATED_HIGHPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEMCODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEMCODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOWPRICE = "AAAAAAAAAA";
    private static final String UPDATED_LOWPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_OPENPRICE = "AAAAAAAAAA";
    private static final String UPDATED_OPENPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_PREVCLOSEPRICE = "AAAAAAAAAA";
    private static final String UPDATED_PREVCLOSEPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_TRADEAMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_TRADEAMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_TRADEDAEBI = "AAAAAAAAAA";
    private static final String UPDATED_TRADEDAEBI = "BBBBBBBBBB";

    private static final String DEFAULT_TRADEDAEBIRATE = "AAAAAAAAAA";
    private static final String UPDATED_TRADEDAEBIRATE = "BBBBBBBBBB";

    private static final String DEFAULT_TRADEDATE = "AAAAAAAAAA";
    private static final String UPDATED_TRADEDATE = "BBBBBBBBBB";

    private static final String DEFAULT_TRADEVOLUMN = "AAAAAAAAAA";
    private static final String UPDATED_TRADEVOLUMN = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CoinisRepository coinisRepository;

    @Autowired
    private CoinisMapper coinisMapper;

    @Autowired
    private CoinisService coinisService;

    @Autowired
    private CoinisSearchRepository coinisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoinisMockMvc;

    private Coinis coinis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CoinisResource coinisResource = new CoinisResource(coinisService);
        this.restCoinisMockMvc = MockMvcBuilders.standaloneSetup(coinisResource)
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
    public static Coinis createEntity(EntityManager em) {
        Coinis coinis = new Coinis()
            .closeprice(DEFAULT_CLOSEPRICE)
            .highprice(DEFAULT_HIGHPRICE)
            .itemcode(DEFAULT_ITEMCODE)
            .lowprice(DEFAULT_LOWPRICE)
            .openprice(DEFAULT_OPENPRICE)
            .prevcloseprice(DEFAULT_PREVCLOSEPRICE)
            .tradeamount(DEFAULT_TRADEAMOUNT)
            .tradedaebi(DEFAULT_TRADEDAEBI)
            .tradedaebirate(DEFAULT_TRADEDAEBIRATE)
            .tradedate(DEFAULT_TRADEDATE)
            .tradevolumn(DEFAULT_TRADEVOLUMN)
            .symbol(DEFAULT_SYMBOL)
            .createdat(DEFAULT_CREATEDAT);
        return coinis;
    }

    @Before
    public void initTest() {
        coinisSearchRepository.deleteAll();
        coinis = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoinis() throws Exception {
        int databaseSizeBeforeCreate = coinisRepository.findAll().size();

        // Create the Coinis
        CoinisDTO coinisDTO = coinisMapper.toDto(coinis);
        restCoinisMockMvc.perform(post("/api/coinis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinisDTO)))
            .andExpect(status().isCreated());

        // Validate the Coinis in the database
        List<Coinis> coinisList = coinisRepository.findAll();
        assertThat(coinisList).hasSize(databaseSizeBeforeCreate + 1);
        Coinis testCoinis = coinisList.get(coinisList.size() - 1);
        assertThat(testCoinis.getCloseprice()).isEqualTo(DEFAULT_CLOSEPRICE);
        assertThat(testCoinis.getHighprice()).isEqualTo(DEFAULT_HIGHPRICE);
        assertThat(testCoinis.getItemcode()).isEqualTo(DEFAULT_ITEMCODE);
        assertThat(testCoinis.getLowprice()).isEqualTo(DEFAULT_LOWPRICE);
        assertThat(testCoinis.getOpenprice()).isEqualTo(DEFAULT_OPENPRICE);
        assertThat(testCoinis.getPrevcloseprice()).isEqualTo(DEFAULT_PREVCLOSEPRICE);
        assertThat(testCoinis.getTradeamount()).isEqualTo(DEFAULT_TRADEAMOUNT);
        assertThat(testCoinis.getTradedaebi()).isEqualTo(DEFAULT_TRADEDAEBI);
        assertThat(testCoinis.getTradedaebirate()).isEqualTo(DEFAULT_TRADEDAEBIRATE);
        assertThat(testCoinis.getTradedate()).isEqualTo(DEFAULT_TRADEDATE);
        assertThat(testCoinis.getTradevolumn()).isEqualTo(DEFAULT_TRADEVOLUMN);
        assertThat(testCoinis.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testCoinis.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);

        // Validate the Coinis in Elasticsearch
        Coinis coinisEs = coinisSearchRepository.findOne(testCoinis.getId());
        assertThat(coinisEs).isEqualToComparingFieldByField(testCoinis);
    }

    @Test
    @Transactional
    public void createCoinisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coinisRepository.findAll().size();

        // Create the Coinis with an existing ID
        coinis.setId(1L);
        CoinisDTO coinisDTO = coinisMapper.toDto(coinis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoinisMockMvc.perform(post("/api/coinis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Coinis> coinisList = coinisRepository.findAll();
        assertThat(coinisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClosepriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = coinisRepository.findAll().size();
        // set the field null
        coinis.setCloseprice(null);

        // Create the Coinis, which fails.
        CoinisDTO coinisDTO = coinisMapper.toDto(coinis);

        restCoinisMockMvc.perform(post("/api/coinis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinisDTO)))
            .andExpect(status().isBadRequest());

        List<Coinis> coinisList = coinisRepository.findAll();
        assertThat(coinisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = coinisRepository.findAll().size();
        // set the field null
        coinis.setSymbol(null);

        // Create the Coinis, which fails.
        CoinisDTO coinisDTO = coinisMapper.toDto(coinis);

        restCoinisMockMvc.perform(post("/api/coinis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinisDTO)))
            .andExpect(status().isBadRequest());

        List<Coinis> coinisList = coinisRepository.findAll();
        assertThat(coinisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = coinisRepository.findAll().size();
        // set the field null
        coinis.setCreatedat(null);

        // Create the Coinis, which fails.
        CoinisDTO coinisDTO = coinisMapper.toDto(coinis);

        restCoinisMockMvc.perform(post("/api/coinis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinisDTO)))
            .andExpect(status().isBadRequest());

        List<Coinis> coinisList = coinisRepository.findAll();
        assertThat(coinisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoinis() throws Exception {
        // Initialize the database
        coinisRepository.saveAndFlush(coinis);

        // Get all the coinisList
        restCoinisMockMvc.perform(get("/api/coinis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coinis.getId().intValue())))
            .andExpect(jsonPath("$.[*].closeprice").value(hasItem(DEFAULT_CLOSEPRICE.toString())))
            .andExpect(jsonPath("$.[*].highprice").value(hasItem(DEFAULT_HIGHPRICE.toString())))
            .andExpect(jsonPath("$.[*].itemcode").value(hasItem(DEFAULT_ITEMCODE.toString())))
            .andExpect(jsonPath("$.[*].lowprice").value(hasItem(DEFAULT_LOWPRICE.toString())))
            .andExpect(jsonPath("$.[*].openprice").value(hasItem(DEFAULT_OPENPRICE.toString())))
            .andExpect(jsonPath("$.[*].prevcloseprice").value(hasItem(DEFAULT_PREVCLOSEPRICE.toString())))
            .andExpect(jsonPath("$.[*].tradeamount").value(hasItem(DEFAULT_TRADEAMOUNT.toString())))
            .andExpect(jsonPath("$.[*].tradedaebi").value(hasItem(DEFAULT_TRADEDAEBI.toString())))
            .andExpect(jsonPath("$.[*].tradedaebirate").value(hasItem(DEFAULT_TRADEDAEBIRATE.toString())))
            .andExpect(jsonPath("$.[*].tradedate").value(hasItem(DEFAULT_TRADEDATE.toString())))
            .andExpect(jsonPath("$.[*].tradevolumn").value(hasItem(DEFAULT_TRADEVOLUMN.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void getCoinis() throws Exception {
        // Initialize the database
        coinisRepository.saveAndFlush(coinis);

        // Get the coinis
        restCoinisMockMvc.perform(get("/api/coinis/{id}", coinis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coinis.getId().intValue()))
            .andExpect(jsonPath("$.closeprice").value(DEFAULT_CLOSEPRICE.toString()))
            .andExpect(jsonPath("$.highprice").value(DEFAULT_HIGHPRICE.toString()))
            .andExpect(jsonPath("$.itemcode").value(DEFAULT_ITEMCODE.toString()))
            .andExpect(jsonPath("$.lowprice").value(DEFAULT_LOWPRICE.toString()))
            .andExpect(jsonPath("$.openprice").value(DEFAULT_OPENPRICE.toString()))
            .andExpect(jsonPath("$.prevcloseprice").value(DEFAULT_PREVCLOSEPRICE.toString()))
            .andExpect(jsonPath("$.tradeamount").value(DEFAULT_TRADEAMOUNT.toString()))
            .andExpect(jsonPath("$.tradedaebi").value(DEFAULT_TRADEDAEBI.toString()))
            .andExpect(jsonPath("$.tradedaebirate").value(DEFAULT_TRADEDAEBIRATE.toString()))
            .andExpect(jsonPath("$.tradedate").value(DEFAULT_TRADEDATE.toString()))
            .andExpect(jsonPath("$.tradevolumn").value(DEFAULT_TRADEVOLUMN.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingCoinis() throws Exception {
        // Get the coinis
        restCoinisMockMvc.perform(get("/api/coinis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoinis() throws Exception {
        // Initialize the database
        coinisRepository.saveAndFlush(coinis);
        coinisSearchRepository.save(coinis);
        int databaseSizeBeforeUpdate = coinisRepository.findAll().size();

        // Update the coinis
        Coinis updatedCoinis = coinisRepository.findOne(coinis.getId());
        updatedCoinis
            .closeprice(UPDATED_CLOSEPRICE)
            .highprice(UPDATED_HIGHPRICE)
            .itemcode(UPDATED_ITEMCODE)
            .lowprice(UPDATED_LOWPRICE)
            .openprice(UPDATED_OPENPRICE)
            .prevcloseprice(UPDATED_PREVCLOSEPRICE)
            .tradeamount(UPDATED_TRADEAMOUNT)
            .tradedaebi(UPDATED_TRADEDAEBI)
            .tradedaebirate(UPDATED_TRADEDAEBIRATE)
            .tradedate(UPDATED_TRADEDATE)
            .tradevolumn(UPDATED_TRADEVOLUMN)
            .symbol(UPDATED_SYMBOL)
            .createdat(UPDATED_CREATEDAT);
        CoinisDTO coinisDTO = coinisMapper.toDto(updatedCoinis);

        restCoinisMockMvc.perform(put("/api/coinis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinisDTO)))
            .andExpect(status().isOk());

        // Validate the Coinis in the database
        List<Coinis> coinisList = coinisRepository.findAll();
        assertThat(coinisList).hasSize(databaseSizeBeforeUpdate);
        Coinis testCoinis = coinisList.get(coinisList.size() - 1);
        assertThat(testCoinis.getCloseprice()).isEqualTo(UPDATED_CLOSEPRICE);
        assertThat(testCoinis.getHighprice()).isEqualTo(UPDATED_HIGHPRICE);
        assertThat(testCoinis.getItemcode()).isEqualTo(UPDATED_ITEMCODE);
        assertThat(testCoinis.getLowprice()).isEqualTo(UPDATED_LOWPRICE);
        assertThat(testCoinis.getOpenprice()).isEqualTo(UPDATED_OPENPRICE);
        assertThat(testCoinis.getPrevcloseprice()).isEqualTo(UPDATED_PREVCLOSEPRICE);
        assertThat(testCoinis.getTradeamount()).isEqualTo(UPDATED_TRADEAMOUNT);
        assertThat(testCoinis.getTradedaebi()).isEqualTo(UPDATED_TRADEDAEBI);
        assertThat(testCoinis.getTradedaebirate()).isEqualTo(UPDATED_TRADEDAEBIRATE);
        assertThat(testCoinis.getTradedate()).isEqualTo(UPDATED_TRADEDATE);
        assertThat(testCoinis.getTradevolumn()).isEqualTo(UPDATED_TRADEVOLUMN);
        assertThat(testCoinis.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testCoinis.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);

        // Validate the Coinis in Elasticsearch
        Coinis coinisEs = coinisSearchRepository.findOne(testCoinis.getId());
        assertThat(coinisEs).isEqualToComparingFieldByField(testCoinis);
    }

    @Test
    @Transactional
    public void updateNonExistingCoinis() throws Exception {
        int databaseSizeBeforeUpdate = coinisRepository.findAll().size();

        // Create the Coinis
        CoinisDTO coinisDTO = coinisMapper.toDto(coinis);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoinisMockMvc.perform(put("/api/coinis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coinisDTO)))
            .andExpect(status().isCreated());

        // Validate the Coinis in the database
        List<Coinis> coinisList = coinisRepository.findAll();
        assertThat(coinisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoinis() throws Exception {
        // Initialize the database
        coinisRepository.saveAndFlush(coinis);
        coinisSearchRepository.save(coinis);
        int databaseSizeBeforeDelete = coinisRepository.findAll().size();

        // Get the coinis
        restCoinisMockMvc.perform(delete("/api/coinis/{id}", coinis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean coinisExistsInEs = coinisSearchRepository.exists(coinis.getId());
        assertThat(coinisExistsInEs).isFalse();

        // Validate the database is empty
        List<Coinis> coinisList = coinisRepository.findAll();
        assertThat(coinisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCoinis() throws Exception {
        // Initialize the database
        coinisRepository.saveAndFlush(coinis);
        coinisSearchRepository.save(coinis);

        // Search the coinis
        restCoinisMockMvc.perform(get("/api/_search/coinis?query=id:" + coinis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coinis.getId().intValue())))
            .andExpect(jsonPath("$.[*].closeprice").value(hasItem(DEFAULT_CLOSEPRICE.toString())))
            .andExpect(jsonPath("$.[*].highprice").value(hasItem(DEFAULT_HIGHPRICE.toString())))
            .andExpect(jsonPath("$.[*].itemcode").value(hasItem(DEFAULT_ITEMCODE.toString())))
            .andExpect(jsonPath("$.[*].lowprice").value(hasItem(DEFAULT_LOWPRICE.toString())))
            .andExpect(jsonPath("$.[*].openprice").value(hasItem(DEFAULT_OPENPRICE.toString())))
            .andExpect(jsonPath("$.[*].prevcloseprice").value(hasItem(DEFAULT_PREVCLOSEPRICE.toString())))
            .andExpect(jsonPath("$.[*].tradeamount").value(hasItem(DEFAULT_TRADEAMOUNT.toString())))
            .andExpect(jsonPath("$.[*].tradedaebi").value(hasItem(DEFAULT_TRADEDAEBI.toString())))
            .andExpect(jsonPath("$.[*].tradedaebirate").value(hasItem(DEFAULT_TRADEDAEBIRATE.toString())))
            .andExpect(jsonPath("$.[*].tradedate").value(hasItem(DEFAULT_TRADEDATE.toString())))
            .andExpect(jsonPath("$.[*].tradevolumn").value(hasItem(DEFAULT_TRADEVOLUMN.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coinis.class);
        Coinis coinis1 = new Coinis();
        coinis1.setId(1L);
        Coinis coinis2 = new Coinis();
        coinis2.setId(coinis1.getId());
        assertThat(coinis1).isEqualTo(coinis2);
        coinis2.setId(2L);
        assertThat(coinis1).isNotEqualTo(coinis2);
        coinis1.setId(null);
        assertThat(coinis1).isNotEqualTo(coinis2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoinisDTO.class);
        CoinisDTO coinisDTO1 = new CoinisDTO();
        coinisDTO1.setId(1L);
        CoinisDTO coinisDTO2 = new CoinisDTO();
        assertThat(coinisDTO1).isNotEqualTo(coinisDTO2);
        coinisDTO2.setId(coinisDTO1.getId());
        assertThat(coinisDTO1).isEqualTo(coinisDTO2);
        coinisDTO2.setId(2L);
        assertThat(coinisDTO1).isNotEqualTo(coinisDTO2);
        coinisDTO1.setId(null);
        assertThat(coinisDTO1).isNotEqualTo(coinisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coinisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coinisMapper.fromId(null)).isNull();
    }
}
