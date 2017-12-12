package io.iansoft.blockchain.web.rest;

import io.iansoft.blockchain.BlockchainApp;

import io.iansoft.blockchain.domain.NewsLike;
import io.iansoft.blockchain.domain.User;
import io.iansoft.blockchain.domain.NewsBoard;
import io.iansoft.blockchain.repository.NewsLikeRepository;
import io.iansoft.blockchain.service.NewsLikeService;
import io.iansoft.blockchain.repository.search.NewsLikeSearchRepository;
import io.iansoft.blockchain.service.dto.NewsLikeDTO;
import io.iansoft.blockchain.service.mapper.NewsLikeMapper;
import io.iansoft.blockchain.web.rest.errors.ExceptionTranslator;
import io.iansoft.blockchain.service.dto.NewsLikeCriteria;
import io.iansoft.blockchain.service.NewsLikeQueryService;

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
 * Test class for the NewsLikeResource REST controller.
 *
 * @see NewsLikeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlockchainApp.class)
public class NewsLikeResourceIntTest {

    @Autowired
    private NewsLikeRepository newsLikeRepository;

    @Autowired
    private NewsLikeMapper newsLikeMapper;

    @Autowired
    private NewsLikeService newsLikeService;

    @Autowired
    private NewsLikeSearchRepository newsLikeSearchRepository;

    @Autowired
    private NewsLikeQueryService newsLikeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNewsLikeMockMvc;

    private NewsLike newsLike;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NewsLikeResource newsLikeResource = new NewsLikeResource(newsLikeService, newsLikeQueryService);
        this.restNewsLikeMockMvc = MockMvcBuilders.standaloneSetup(newsLikeResource)
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
    public static NewsLike createEntity(EntityManager em) {
        NewsLike newsLike = new NewsLike();
        return newsLike;
    }

    @Before
    public void initTest() {
        newsLikeSearchRepository.deleteAll();
        newsLike = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewsLike() throws Exception {
        int databaseSizeBeforeCreate = newsLikeRepository.findAll().size();

        // Create the NewsLike
        NewsLikeDTO newsLikeDTO = newsLikeMapper.toDto(newsLike);
        restNewsLikeMockMvc.perform(post("/api/news-likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsLikeDTO)))
            .andExpect(status().isCreated());

        // Validate the NewsLike in the database
        List<NewsLike> newsLikeList = newsLikeRepository.findAll();
        assertThat(newsLikeList).hasSize(databaseSizeBeforeCreate + 1);
        NewsLike testNewsLike = newsLikeList.get(newsLikeList.size() - 1);

        // Validate the NewsLike in Elasticsearch
        NewsLike newsLikeEs = newsLikeSearchRepository.findOne(testNewsLike.getId());
        assertThat(newsLikeEs).isEqualToComparingFieldByField(testNewsLike);
    }

    @Test
    @Transactional
    public void createNewsLikeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsLikeRepository.findAll().size();

        // Create the NewsLike with an existing ID
        newsLike.setId(1L);
        NewsLikeDTO newsLikeDTO = newsLikeMapper.toDto(newsLike);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsLikeMockMvc.perform(post("/api/news-likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsLikeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NewsLike in the database
        List<NewsLike> newsLikeList = newsLikeRepository.findAll();
        assertThat(newsLikeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNewsLikes() throws Exception {
        // Initialize the database
        newsLikeRepository.saveAndFlush(newsLike);

        // Get all the newsLikeList
        restNewsLikeMockMvc.perform(get("/api/news-likes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsLike.getId().intValue())));
    }

    @Test
    @Transactional
    public void getNewsLike() throws Exception {
        // Initialize the database
        newsLikeRepository.saveAndFlush(newsLike);

        // Get the newsLike
        restNewsLikeMockMvc.perform(get("/api/news-likes/{id}", newsLike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(newsLike.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllNewsLikesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        newsLike.setUser(user);
        newsLikeRepository.saveAndFlush(newsLike);
        Long userId = user.getId();

        // Get all the newsLikeList where user equals to userId
        defaultNewsLikeShouldBeFound("userId.equals=" + userId);

        // Get all the newsLikeList where user equals to userId + 1
        defaultNewsLikeShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllNewsLikesByNewsBoardIsEqualToSomething() throws Exception {
        // Initialize the database
        NewsBoard newsBoard = NewsBoardResourceIntTest.createEntity(em);
        em.persist(newsBoard);
        em.flush();
        newsLike.setNewsBoard(newsBoard);
        newsLikeRepository.saveAndFlush(newsLike);
        Long newsBoardId = newsBoard.getId();

        // Get all the newsLikeList where newsBoard equals to newsBoardId
        defaultNewsLikeShouldBeFound("newsBoardId.equals=" + newsBoardId);

        // Get all the newsLikeList where newsBoard equals to newsBoardId + 1
        defaultNewsLikeShouldNotBeFound("newsBoardId.equals=" + (newsBoardId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNewsLikeShouldBeFound(String filter) throws Exception {
        restNewsLikeMockMvc.perform(get("/api/news-likes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsLike.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNewsLikeShouldNotBeFound(String filter) throws Exception {
        restNewsLikeMockMvc.perform(get("/api/news-likes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingNewsLike() throws Exception {
        // Get the newsLike
        restNewsLikeMockMvc.perform(get("/api/news-likes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewsLike() throws Exception {
        // Initialize the database
        newsLikeRepository.saveAndFlush(newsLike);
        newsLikeSearchRepository.save(newsLike);
        int databaseSizeBeforeUpdate = newsLikeRepository.findAll().size();

        // Update the newsLike
        NewsLike updatedNewsLike = newsLikeRepository.findOne(newsLike.getId());
        NewsLikeDTO newsLikeDTO = newsLikeMapper.toDto(updatedNewsLike);

        restNewsLikeMockMvc.perform(put("/api/news-likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsLikeDTO)))
            .andExpect(status().isOk());

        // Validate the NewsLike in the database
        List<NewsLike> newsLikeList = newsLikeRepository.findAll();
        assertThat(newsLikeList).hasSize(databaseSizeBeforeUpdate);
        NewsLike testNewsLike = newsLikeList.get(newsLikeList.size() - 1);

        // Validate the NewsLike in Elasticsearch
        NewsLike newsLikeEs = newsLikeSearchRepository.findOne(testNewsLike.getId());
        assertThat(newsLikeEs).isEqualToComparingFieldByField(testNewsLike);
    }

    @Test
    @Transactional
    public void updateNonExistingNewsLike() throws Exception {
        int databaseSizeBeforeUpdate = newsLikeRepository.findAll().size();

        // Create the NewsLike
        NewsLikeDTO newsLikeDTO = newsLikeMapper.toDto(newsLike);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNewsLikeMockMvc.perform(put("/api/news-likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsLikeDTO)))
            .andExpect(status().isCreated());

        // Validate the NewsLike in the database
        List<NewsLike> newsLikeList = newsLikeRepository.findAll();
        assertThat(newsLikeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNewsLike() throws Exception {
        // Initialize the database
        newsLikeRepository.saveAndFlush(newsLike);
        newsLikeSearchRepository.save(newsLike);
        int databaseSizeBeforeDelete = newsLikeRepository.findAll().size();

        // Get the newsLike
        restNewsLikeMockMvc.perform(delete("/api/news-likes/{id}", newsLike.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean newsLikeExistsInEs = newsLikeSearchRepository.exists(newsLike.getId());
        assertThat(newsLikeExistsInEs).isFalse();

        // Validate the database is empty
        List<NewsLike> newsLikeList = newsLikeRepository.findAll();
        assertThat(newsLikeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNewsLike() throws Exception {
        // Initialize the database
        newsLikeRepository.saveAndFlush(newsLike);
        newsLikeSearchRepository.save(newsLike);

        // Search the newsLike
        restNewsLikeMockMvc.perform(get("/api/_search/news-likes?query=id:" + newsLike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsLike.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsLike.class);
        NewsLike newsLike1 = new NewsLike();
        newsLike1.setId(1L);
        NewsLike newsLike2 = new NewsLike();
        newsLike2.setId(newsLike1.getId());
        assertThat(newsLike1).isEqualTo(newsLike2);
        newsLike2.setId(2L);
        assertThat(newsLike1).isNotEqualTo(newsLike2);
        newsLike1.setId(null);
        assertThat(newsLike1).isNotEqualTo(newsLike2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsLikeDTO.class);
        NewsLikeDTO newsLikeDTO1 = new NewsLikeDTO();
        newsLikeDTO1.setId(1L);
        NewsLikeDTO newsLikeDTO2 = new NewsLikeDTO();
        assertThat(newsLikeDTO1).isNotEqualTo(newsLikeDTO2);
        newsLikeDTO2.setId(newsLikeDTO1.getId());
        assertThat(newsLikeDTO1).isEqualTo(newsLikeDTO2);
        newsLikeDTO2.setId(2L);
        assertThat(newsLikeDTO1).isNotEqualTo(newsLikeDTO2);
        newsLikeDTO1.setId(null);
        assertThat(newsLikeDTO1).isNotEqualTo(newsLikeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(newsLikeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(newsLikeMapper.fromId(null)).isNull();
    }
}
