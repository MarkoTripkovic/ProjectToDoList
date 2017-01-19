package org.jhipster.todo.web.rest;

import org.jhipster.todo.TodolistApp;

import org.jhipster.todo.domain.Korisnik;
import org.jhipster.todo.repository.KorisnikRepository;
import org.jhipster.todo.service.KorisnikService;
import org.jhipster.todo.service.dto.KorisnikDTO;
import org.jhipster.todo.service.mapper.KorisnikMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KorisnikResource REST controller.
 *
 * @see KorisnikResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TodolistApp.class)
public class KorisnikResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Inject
    private KorisnikRepository korisnikRepository;

    @Inject
    private KorisnikMapper korisnikMapper;

    @Inject
    private KorisnikService korisnikService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restKorisnikMockMvc;

    private Korisnik korisnik;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KorisnikResource korisnikResource = new KorisnikResource();
        ReflectionTestUtils.setField(korisnikResource, "korisnikService", korisnikService);
        this.restKorisnikMockMvc = MockMvcBuilders.standaloneSetup(korisnikResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Korisnik createEntity(EntityManager em) {
        Korisnik korisnik = new Korisnik()
                .username(DEFAULT_USERNAME)
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD);
        return korisnik;
    }

    @Before
    public void initTest() {
        korisnik = createEntity(em);
    }

    @Test
    @Transactional
    public void createKorisnik() throws Exception {
        int databaseSizeBeforeCreate = korisnikRepository.findAll().size();

        // Create the Korisnik
        KorisnikDTO korisnikDTO = korisnikMapper.korisnikToKorisnikDTO(korisnik);

        restKorisnikMockMvc.perform(post("/api/korisniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(korisnikDTO)))
            .andExpect(status().isCreated());

        // Validate the Korisnik in the database
        List<Korisnik> korisnikList = korisnikRepository.findAll();
        assertThat(korisnikList).hasSize(databaseSizeBeforeCreate + 1);
        Korisnik testKorisnik = korisnikList.get(korisnikList.size() - 1);
        assertThat(testKorisnik.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testKorisnik.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testKorisnik.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createKorisnikWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = korisnikRepository.findAll().size();

        // Create the Korisnik with an existing ID
        Korisnik existingKorisnik = new Korisnik();
        existingKorisnik.setId(1L);
        KorisnikDTO existingKorisnikDTO = korisnikMapper.korisnikToKorisnikDTO(existingKorisnik);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKorisnikMockMvc.perform(post("/api/korisniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingKorisnikDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Korisnik> korisnikList = korisnikRepository.findAll();
        assertThat(korisnikList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKorisniks() throws Exception {
        // Initialize the database
        korisnikRepository.saveAndFlush(korisnik);

        // Get all the korisnikList
        restKorisnikMockMvc.perform(get("/api/korisniks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(korisnik.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getKorisnik() throws Exception {
        // Initialize the database
        korisnikRepository.saveAndFlush(korisnik);

        // Get the korisnik
        restKorisnikMockMvc.perform(get("/api/korisniks/{id}", korisnik.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(korisnik.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKorisnik() throws Exception {
        // Get the korisnik
        restKorisnikMockMvc.perform(get("/api/korisniks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKorisnik() throws Exception {
        // Initialize the database
        korisnikRepository.saveAndFlush(korisnik);
        int databaseSizeBeforeUpdate = korisnikRepository.findAll().size();

        // Update the korisnik
        Korisnik updatedKorisnik = korisnikRepository.findOne(korisnik.getId());
        updatedKorisnik
                .username(UPDATED_USERNAME)
                .email(UPDATED_EMAIL)
                .password(UPDATED_PASSWORD);
        KorisnikDTO korisnikDTO = korisnikMapper.korisnikToKorisnikDTO(updatedKorisnik);

        restKorisnikMockMvc.perform(put("/api/korisniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(korisnikDTO)))
            .andExpect(status().isOk());

        // Validate the Korisnik in the database
        List<Korisnik> korisnikList = korisnikRepository.findAll();
        assertThat(korisnikList).hasSize(databaseSizeBeforeUpdate);
        Korisnik testKorisnik = korisnikList.get(korisnikList.size() - 1);
        assertThat(testKorisnik.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testKorisnik.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKorisnik.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingKorisnik() throws Exception {
        int databaseSizeBeforeUpdate = korisnikRepository.findAll().size();

        // Create the Korisnik
        KorisnikDTO korisnikDTO = korisnikMapper.korisnikToKorisnikDTO(korisnik);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKorisnikMockMvc.perform(put("/api/korisniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(korisnikDTO)))
            .andExpect(status().isCreated());

        // Validate the Korisnik in the database
        List<Korisnik> korisnikList = korisnikRepository.findAll();
        assertThat(korisnikList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKorisnik() throws Exception {
        // Initialize the database
        korisnikRepository.saveAndFlush(korisnik);
        int databaseSizeBeforeDelete = korisnikRepository.findAll().size();

        // Get the korisnik
        restKorisnikMockMvc.perform(delete("/api/korisniks/{id}", korisnik.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Korisnik> korisnikList = korisnikRepository.findAll();
        assertThat(korisnikList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
