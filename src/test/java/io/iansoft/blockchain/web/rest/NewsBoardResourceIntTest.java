package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.NewsBoard;
import io.iansoft.blockchain.domain.User;
import io.iansoft.blockchain.repository.NewsBoardRepository;
import io.iansoft.blockchain.service.NewsBoardService;
import io.iansoft.blockchain.repository.search.NewsBoardSearchRepository;
import io.iansoft.blockchain.service.dto.NewsBoardDTO;
import io.iansoft.blockchain.service.mapper.NewsBoardMapper;
import io.iansoft.blockchain.web.rest.errors.ExceptionTranslator;
import io.iansoft.blockchain.service.dto.NewsBoardCriteria;
import io.iansoft.blockchain.service.NewsBoardQueryService;

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

import static io.iansoft.blockchain.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NewsBoardResource REST controller.
 *
 * @see NewsBoardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class NewsBoardResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final Long DEFAULT_HIT = 1L;
    private static final Long UPDATED_HIT = 2L;

    @Autowired
    private NewsBoardRepository newsBoardRepository;

    @Autowired
    private NewsBoardMapper newsBoardMapper;

    @Autowired
    private NewsBoardService newsBoardService;

    @Autowired
    private NewsBoardSearchRepository newsBoardSearchRepository;

    @Autowired
    private NewsBoardQueryService newsBoardQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNewsBoardMockMvc;

    private NewsBoard newsBoard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NewsBoardResource newsBoardResource = new NewsBoardResource(newsBoardService, newsBoardQueryService);
        this.restNewsBoardMockMvc = MockMvcBuilders.standaloneSetup(newsBoardResource)
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
    public static NewsBoard createEntity(EntityManager em) {
        NewsBoard newsBoard = new NewsBoard()
            .title(DEFAULT_TITLE)
            .url(DEFAULT_URL)
            .context(DEFAULT_CONTEXT)
            .hit(DEFAULT_HIT);
        return newsBoard;
    }

    @Before
    public void initTest() {
        newsBoardSearchRepository.deleteAll();
        newsBoard = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewsBoard() throws Exception {
        int databaseSizeBeforeCreate = newsBoardRepository.findAll().size();

        // Create the NewsBoard
        NewsBoardDTO newsBoardDTO = newsBoardMapper.toDto(newsBoard);
        restNewsBoardMockMvc.perform(post("/api/news-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsBoardDTO)))
            .andExpect(status().isCreated());

        // Validate the NewsBoard in the database
        List<NewsBoard> newsBoardList = newsBoardRepository.findAll();
        assertThat(newsBoardList).hasSize(databaseSizeBeforeCreate + 1);
        NewsBoard testNewsBoard = newsBoardList.get(newsBoardList.size() - 1);
        assertThat(testNewsBoard.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNewsBoard.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testNewsBoard.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testNewsBoard.getHit()).isEqualTo(DEFAULT_HIT);

        // Validate the NewsBoard in Elasticsearch
        NewsBoard newsBoardEs = newsBoardSearchRepository.findOne(testNewsBoard.getId());
        assertThat(newsBoardEs).isEqualToComparingFieldByField(testNewsBoard);
    }

    @Test
    @Transactional
    public void createNewsBoardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsBoardRepository.findAll().size();

        // Create the NewsBoard with an existing ID
        newsBoard.setId(1L);
        NewsBoardDTO newsBoardDTO = newsBoardMapper.toDto(newsBoard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsBoardMockMvc.perform(post("/api/news-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsBoardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NewsBoard in the database
        List<NewsBoard> newsBoardList = newsBoardRepository.findAll();
        assertThat(newsBoardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsBoardRepository.findAll().size();
        // set the field null
        newsBoard.setTitle(null);

        // Create the NewsBoard, which fails.
        NewsBoardDTO newsBoardDTO = newsBoardMapper.toDto(newsBoard);

        restNewsBoardMockMvc.perform(post("/api/news-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsBoardDTO)))
            .andExpect(status().isBadRequest());

        List<NewsBoard> newsBoardList = newsBoardRepository.findAll();
        assertThat(newsBoardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContextIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsBoardRepository.findAll().size();
        // set the field null
        newsBoard.setContext(null);

        // Create the NewsBoard, which fails.
        NewsBoardDTO newsBoardDTO = newsBoardMapper.toDto(newsBoard);

        restNewsBoardMockMvc.perform(post("/api/news-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsBoardDTO)))
            .andExpect(status().isBadRequest());

        List<NewsBoard> newsBoardList = newsBoardRepository.findAll();
        assertThat(newsBoardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNewsBoards() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList
        restNewsBoardMockMvc.perform(get("/api/news-boards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].hit").value(hasItem(DEFAULT_HIT.intValue())));
    }

    @Test
    @Transactional
    public void getNewsBoard() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get the newsBoard
        restNewsBoardMockMvc.perform(get("/api/news-boards/{id}", newsBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(newsBoard.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.hit").value(DEFAULT_HIT.intValue()));
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where title equals to DEFAULT_TITLE
        defaultNewsBoardShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the newsBoardList where title equals to UPDATED_TITLE
        defaultNewsBoardShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNewsBoardShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the newsBoardList where title equals to UPDATED_TITLE
        defaultNewsBoardShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where title is not null
        defaultNewsBoardShouldBeFound("title.specified=true");

        // Get all the newsBoardList where title is null
        defaultNewsBoardShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where url equals to DEFAULT_URL
        defaultNewsBoardShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the newsBoardList where url equals to UPDATED_URL
        defaultNewsBoardShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where url in DEFAULT_URL or UPDATED_URL
        defaultNewsBoardShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the newsBoardList where url equals to UPDATED_URL
        defaultNewsBoardShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where url is not null
        defaultNewsBoardShouldBeFound("url.specified=true");

        // Get all the newsBoardList where url is null
        defaultNewsBoardShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByContextIsEqualToSomething() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where context equals to DEFAULT_CONTEXT
        defaultNewsBoardShouldBeFound("context.equals=" + DEFAULT_CONTEXT);

        // Get all the newsBoardList where context equals to UPDATED_CONTEXT
        defaultNewsBoardShouldNotBeFound("context.equals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByContextIsInShouldWork() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where context in DEFAULT_CONTEXT or UPDATED_CONTEXT
        defaultNewsBoardShouldBeFound("context.in=" + DEFAULT_CONTEXT + "," + UPDATED_CONTEXT);

        // Get all the newsBoardList where context equals to UPDATED_CONTEXT
        defaultNewsBoardShouldNotBeFound("context.in=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByContextIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where context is not null
        defaultNewsBoardShouldBeFound("context.specified=true");

        // Get all the newsBoardList where context is null
        defaultNewsBoardShouldNotBeFound("context.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByHitIsEqualToSomething() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where hit equals to DEFAULT_HIT
        defaultNewsBoardShouldBeFound("hit.equals=" + DEFAULT_HIT);

        // Get all the newsBoardList where hit equals to UPDATED_HIT
        defaultNewsBoardShouldNotBeFound("hit.equals=" + UPDATED_HIT);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByHitIsInShouldWork() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where hit in DEFAULT_HIT or UPDATED_HIT
        defaultNewsBoardShouldBeFound("hit.in=" + DEFAULT_HIT + "," + UPDATED_HIT);

        // Get all the newsBoardList where hit equals to UPDATED_HIT
        defaultNewsBoardShouldNotBeFound("hit.in=" + UPDATED_HIT);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByHitIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where hit is not null
        defaultNewsBoardShouldBeFound("hit.specified=true");

        // Get all the newsBoardList where hit is null
        defaultNewsBoardShouldNotBeFound("hit.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByHitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where hit greater than or equals to DEFAULT_HIT
        defaultNewsBoardShouldBeFound("hit.greaterOrEqualThan=" + DEFAULT_HIT);

        // Get all the newsBoardList where hit greater than or equals to UPDATED_HIT
        defaultNewsBoardShouldNotBeFound("hit.greaterOrEqualThan=" + UPDATED_HIT);
    }

    @Test
    @Transactional
    public void getAllNewsBoardsByHitIsLessThanSomething() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);

        // Get all the newsBoardList where hit less than or equals to DEFAULT_HIT
        defaultNewsBoardShouldNotBeFound("hit.lessThan=" + DEFAULT_HIT);

        // Get all the newsBoardList where hit less than or equals to UPDATED_HIT
        defaultNewsBoardShouldBeFound("hit.lessThan=" + UPDATED_HIT);
    }


    @Test
    @Transactional
    public void getAllNewsBoardsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        newsBoard.setUser(user);
        newsBoardRepository.saveAndFlush(newsBoard);
        Long userId = user.getId();

        // Get all the newsBoardList where user equals to userId
        defaultNewsBoardShouldBeFound("userId.equals=" + userId);

        // Get all the newsBoardList where user equals to userId + 1
        defaultNewsBoardShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNewsBoardShouldBeFound(String filter) throws Exception {
        restNewsBoardMockMvc.perform(get("/api/news-boards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].hit").value(hasItem(DEFAULT_HIT.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNewsBoardShouldNotBeFound(String filter) throws Exception {
        restNewsBoardMockMvc.perform(get("/api/news-boards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingNewsBoard() throws Exception {
        // Get the newsBoard
        restNewsBoardMockMvc.perform(get("/api/news-boards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewsBoard() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);
        newsBoardSearchRepository.save(newsBoard);
        int databaseSizeBeforeUpdate = newsBoardRepository.findAll().size();

        // Update the newsBoard
        NewsBoard updatedNewsBoard = newsBoardRepository.findOne(newsBoard.getId());
        updatedNewsBoard
            .title(UPDATED_TITLE)
            .url(UPDATED_URL)
            .context(UPDATED_CONTEXT)
            .hit(UPDATED_HIT);
        NewsBoardDTO newsBoardDTO = newsBoardMapper.toDto(updatedNewsBoard);

        restNewsBoardMockMvc.perform(put("/api/news-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsBoardDTO)))
            .andExpect(status().isOk());

        // Validate the NewsBoard in the database
        List<NewsBoard> newsBoardList = newsBoardRepository.findAll();
        assertThat(newsBoardList).hasSize(databaseSizeBeforeUpdate);
        NewsBoard testNewsBoard = newsBoardList.get(newsBoardList.size() - 1);
        assertThat(testNewsBoard.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNewsBoard.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testNewsBoard.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testNewsBoard.getHit()).isEqualTo(UPDATED_HIT);

        // Validate the NewsBoard in Elasticsearch
        NewsBoard newsBoardEs = newsBoardSearchRepository.findOne(testNewsBoard.getId());
        assertThat(newsBoardEs).isEqualToComparingFieldByField(testNewsBoard);
    }

    @Test
    @Transactional
    public void updateNonExistingNewsBoard() throws Exception {
        int databaseSizeBeforeUpdate = newsBoardRepository.findAll().size();

        // Create the NewsBoard
        NewsBoardDTO newsBoardDTO = newsBoardMapper.toDto(newsBoard);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNewsBoardMockMvc.perform(put("/api/news-boards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsBoardDTO)))
            .andExpect(status().isCreated());

        // Validate the NewsBoard in the database
        List<NewsBoard> newsBoardList = newsBoardRepository.findAll();
        assertThat(newsBoardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNewsBoard() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);
        newsBoardSearchRepository.save(newsBoard);
        int databaseSizeBeforeDelete = newsBoardRepository.findAll().size();

        // Get the newsBoard
        restNewsBoardMockMvc.perform(delete("/api/news-boards/{id}", newsBoard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean newsBoardExistsInEs = newsBoardSearchRepository.exists(newsBoard.getId());
        assertThat(newsBoardExistsInEs).isFalse();

        // Validate the database is empty
        List<NewsBoard> newsBoardList = newsBoardRepository.findAll();
        assertThat(newsBoardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNewsBoard() throws Exception {
        // Initialize the database
        newsBoardRepository.saveAndFlush(newsBoard);
        newsBoardSearchRepository.save(newsBoard);

        // Search the newsBoard
        restNewsBoardMockMvc.perform(get("/api/_search/news-boards?query=id:" + newsBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].hit").value(hasItem(DEFAULT_HIT.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsBoard.class);
        NewsBoard newsBoard1 = new NewsBoard();
        newsBoard1.setId(1L);
        NewsBoard newsBoard2 = new NewsBoard();
        newsBoard2.setId(newsBoard1.getId());
        assertThat(newsBoard1).isEqualTo(newsBoard2);
        newsBoard2.setId(2L);
        assertThat(newsBoard1).isNotEqualTo(newsBoard2);
        newsBoard1.setId(null);
        assertThat(newsBoard1).isNotEqualTo(newsBoard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsBoardDTO.class);
        NewsBoardDTO newsBoardDTO1 = new NewsBoardDTO();
        newsBoardDTO1.setId(1L);
        NewsBoardDTO newsBoardDTO2 = new NewsBoardDTO();
        assertThat(newsBoardDTO1).isNotEqualTo(newsBoardDTO2);
        newsBoardDTO2.setId(newsBoardDTO1.getId());
        assertThat(newsBoardDTO1).isEqualTo(newsBoardDTO2);
        newsBoardDTO2.setId(2L);
        assertThat(newsBoardDTO1).isNotEqualTo(newsBoardDTO2);
        newsBoardDTO1.setId(null);
        assertThat(newsBoardDTO1).isNotEqualTo(newsBoardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(newsBoardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(newsBoardMapper.fromId(null)).isNull();
    }
}
