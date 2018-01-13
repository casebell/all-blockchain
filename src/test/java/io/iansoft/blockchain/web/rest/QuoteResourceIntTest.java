package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.Quote;
import io.iansoft.blockchain.repository.QuoteRepository;
import io.iansoft.blockchain.repository.search.QuoteSearchRepository;
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
 * Test class for the QuoteResource REST controller.
 *
 * @see QuoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class QuoteResourceIntTest {

    private static final BigDecimal DEFAULT_LAST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VOLUME = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOLUME = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LOW_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LOW_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HIGH_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HIGH_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AVG_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVG_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BUY_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUY_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SELL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SELL_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OPENING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OPENING_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CLOSING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CLOSING_PRICE = new BigDecimal(2);

    private static final Instant DEFAULT_QUOTE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_QUOTE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private QuoteSearchRepository quoteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuoteMockMvc;

    private Quote quote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuoteResource quoteResource = new QuoteResource(quoteRepository, quoteSearchRepository);
        this.restQuoteMockMvc = MockMvcBuilders.standaloneSetup(quoteResource)
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
    public static Quote createEntity(EntityManager em) {
        Quote quote = new Quote()
            .lastPrice(DEFAULT_LAST_PRICE)
            .volume(DEFAULT_VOLUME)
            .lowPrice(DEFAULT_LOW_PRICE)
            .highPrice(DEFAULT_HIGH_PRICE)
            .avgPrice(DEFAULT_AVG_PRICE)
            .buyPrice(DEFAULT_BUY_PRICE)
            .sellPrice(DEFAULT_SELL_PRICE)
            .openingPrice(DEFAULT_OPENING_PRICE)
            .closingPrice(DEFAULT_CLOSING_PRICE)
            .quoteTime(DEFAULT_QUOTE_TIME);
        return quote;
    }

    @Before
    public void initTest() {
        quoteSearchRepository.deleteAll();
        quote = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuote() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();

        // Create the Quote
        restQuoteMockMvc.perform(post("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quote)))
            .andExpect(status().isCreated());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeCreate + 1);
        Quote testQuote = quoteList.get(quoteList.size() - 1);
        assertThat(testQuote.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testQuote.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testQuote.getLowPrice()).isEqualTo(DEFAULT_LOW_PRICE);
        assertThat(testQuote.getHighPrice()).isEqualTo(DEFAULT_HIGH_PRICE);
        assertThat(testQuote.getAvgPrice()).isEqualTo(DEFAULT_AVG_PRICE);
        assertThat(testQuote.getBuyPrice()).isEqualTo(DEFAULT_BUY_PRICE);
        assertThat(testQuote.getSellPrice()).isEqualTo(DEFAULT_SELL_PRICE);
        assertThat(testQuote.getOpeningPrice()).isEqualTo(DEFAULT_OPENING_PRICE);
        assertThat(testQuote.getClosingPrice()).isEqualTo(DEFAULT_CLOSING_PRICE);
        assertThat(testQuote.getQuoteTime()).isEqualTo(DEFAULT_QUOTE_TIME);

        // Validate the Quote in Elasticsearch
        Quote quoteEs = quoteSearchRepository.findOne(testQuote.getId());
        assertThat(quoteEs).isEqualToIgnoringGivenFields(testQuote);
    }

    @Test
    @Transactional
    public void createQuoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();

        // Create the Quote with an existing ID
        quote.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuoteMockMvc.perform(post("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quote)))
            .andExpect(status().isBadRequest());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLastPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setLastPrice(null);

        // Create the Quote, which fails.

        restQuoteMockMvc.perform(post("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quote)))
            .andExpect(status().isBadRequest());

        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotes() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList
        restQuoteMockMvc.perform(get("/api/quotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.intValue())))
            .andExpect(jsonPath("$.[*].lowPrice").value(hasItem(DEFAULT_LOW_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].highPrice").value(hasItem(DEFAULT_HIGH_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].avgPrice").value(hasItem(DEFAULT_AVG_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyPrice").value(hasItem(DEFAULT_BUY_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].sellPrice").value(hasItem(DEFAULT_SELL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].openingPrice").value(hasItem(DEFAULT_OPENING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].closingPrice").value(hasItem(DEFAULT_CLOSING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quoteTime").value(hasItem(DEFAULT_QUOTE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", quote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quote.getId().intValue()))
            .andExpect(jsonPath("$.lastPrice").value(DEFAULT_LAST_PRICE.intValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.intValue()))
            .andExpect(jsonPath("$.lowPrice").value(DEFAULT_LOW_PRICE.intValue()))
            .andExpect(jsonPath("$.highPrice").value(DEFAULT_HIGH_PRICE.intValue()))
            .andExpect(jsonPath("$.avgPrice").value(DEFAULT_AVG_PRICE.intValue()))
            .andExpect(jsonPath("$.buyPrice").value(DEFAULT_BUY_PRICE.intValue()))
            .andExpect(jsonPath("$.sellPrice").value(DEFAULT_SELL_PRICE.intValue()))
            .andExpect(jsonPath("$.openingPrice").value(DEFAULT_OPENING_PRICE.intValue()))
            .andExpect(jsonPath("$.closingPrice").value(DEFAULT_CLOSING_PRICE.intValue()))
            .andExpect(jsonPath("$.quoteTime").value(DEFAULT_QUOTE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuote() throws Exception {
        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);
        quoteSearchRepository.save(quote);
        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Update the quote
        Quote updatedQuote = quoteRepository.findOne(quote.getId());
        // Disconnect from session so that the updates on updatedQuote are not directly saved in db
        em.detach(updatedQuote);
        updatedQuote
            .lastPrice(UPDATED_LAST_PRICE)
            .volume(UPDATED_VOLUME)
            .lowPrice(UPDATED_LOW_PRICE)
            .highPrice(UPDATED_HIGH_PRICE)
            .avgPrice(UPDATED_AVG_PRICE)
            .buyPrice(UPDATED_BUY_PRICE)
            .sellPrice(UPDATED_SELL_PRICE)
            .openingPrice(UPDATED_OPENING_PRICE)
            .closingPrice(UPDATED_CLOSING_PRICE)
            .quoteTime(UPDATED_QUOTE_TIME);

        restQuoteMockMvc.perform(put("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuote)))
            .andExpect(status().isOk());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate);
        Quote testQuote = quoteList.get(quoteList.size() - 1);
        assertThat(testQuote.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testQuote.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testQuote.getLowPrice()).isEqualTo(UPDATED_LOW_PRICE);
        assertThat(testQuote.getHighPrice()).isEqualTo(UPDATED_HIGH_PRICE);
        assertThat(testQuote.getAvgPrice()).isEqualTo(UPDATED_AVG_PRICE);
        assertThat(testQuote.getBuyPrice()).isEqualTo(UPDATED_BUY_PRICE);
        assertThat(testQuote.getSellPrice()).isEqualTo(UPDATED_SELL_PRICE);
        assertThat(testQuote.getOpeningPrice()).isEqualTo(UPDATED_OPENING_PRICE);
        assertThat(testQuote.getClosingPrice()).isEqualTo(UPDATED_CLOSING_PRICE);
        assertThat(testQuote.getQuoteTime()).isEqualTo(UPDATED_QUOTE_TIME);

        // Validate the Quote in Elasticsearch
        Quote quoteEs = quoteSearchRepository.findOne(testQuote.getId());
        assertThat(quoteEs).isEqualToIgnoringGivenFields(testQuote);
    }

    @Test
    @Transactional
    public void updateNonExistingQuote() throws Exception {
        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Create the Quote

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuoteMockMvc.perform(put("/api/quotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quote)))
            .andExpect(status().isCreated());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);
        quoteSearchRepository.save(quote);
        int databaseSizeBeforeDelete = quoteRepository.findAll().size();

        // Get the quote
        restQuoteMockMvc.perform(delete("/api/quotes/{id}", quote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean quoteExistsInEs = quoteSearchRepository.exists(quote.getId());
        assertThat(quoteExistsInEs).isFalse();

        // Validate the database is empty
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);
        quoteSearchRepository.save(quote);

        // Search the quote
        restQuoteMockMvc.perform(get("/api/_search/quotes?query=id:" + quote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.intValue())))
            .andExpect(jsonPath("$.[*].lowPrice").value(hasItem(DEFAULT_LOW_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].highPrice").value(hasItem(DEFAULT_HIGH_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].avgPrice").value(hasItem(DEFAULT_AVG_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyPrice").value(hasItem(DEFAULT_BUY_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].sellPrice").value(hasItem(DEFAULT_SELL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].openingPrice").value(hasItem(DEFAULT_OPENING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].closingPrice").value(hasItem(DEFAULT_CLOSING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quoteTime").value(hasItem(DEFAULT_QUOTE_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quote.class);
        Quote quote1 = new Quote();
        quote1.setId(1L);
        Quote quote2 = new Quote();
        quote2.setId(quote1.getId());
        assertThat(quote1).isEqualTo(quote2);
        quote2.setId(2L);
        assertThat(quote1).isNotEqualTo(quote2);
        quote1.setId(null);
        assertThat(quote1).isNotEqualTo(quote2);
    }
}
