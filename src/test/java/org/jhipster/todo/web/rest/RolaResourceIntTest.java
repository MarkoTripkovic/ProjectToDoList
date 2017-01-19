package org.jhipster.todo.web.rest;

import org.jhipster.todo.TodolistApp;

import org.jhipster.todo.domain.Rola;
import org.jhipster.todo.repository.RolaRepository;
import org.jhipster.todo.service.RolaService;
import org.jhipster.todo.service.dto.RolaDTO;
import org.jhipster.todo.service.mapper.RolaMapper;

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
 * Test class for the RolaResource REST controller.
 *
 * @see RolaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TodolistApp.class)
public class RolaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private RolaRepository rolaRepository;

    @Inject
    private RolaMapper rolaMapper;

    @Inject
    private RolaService rolaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRolaMockMvc;

    private Rola rola;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RolaResource rolaResource = new RolaResource();
        ReflectionTestUtils.setField(rolaResource, "rolaService", rolaService);
        this.restRolaMockMvc = MockMvcBuilders.standaloneSetup(rolaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rola createEntity(EntityManager em) {
        Rola rola = new Rola()
                .name(DEFAULT_NAME);
        return rola;
    }

    @Before
    public void initTest() {
        rola = createEntity(em);
    }

    @Test
    @Transactional
    public void createRola() throws Exception {
        int databaseSizeBeforeCreate = rolaRepository.findAll().size();

        // Create the Rola
        RolaDTO rolaDTO = rolaMapper.rolaToRolaDTO(rola);

        restRolaMockMvc.perform(post("/api/rolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolaDTO)))
            .andExpect(status().isCreated());

        // Validate the Rola in the database
        List<Rola> rolaList = rolaRepository.findAll();
        assertThat(rolaList).hasSize(databaseSizeBeforeCreate + 1);
        Rola testRola = rolaList.get(rolaList.size() - 1);
        assertThat(testRola.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRolaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rolaRepository.findAll().size();

        // Create the Rola with an existing ID
        Rola existingRola = new Rola();
        existingRola.setId(1L);
        RolaDTO existingRolaDTO = rolaMapper.rolaToRolaDTO(existingRola);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolaMockMvc.perform(post("/api/rolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRolaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Rola> rolaList = rolaRepository.findAll();
        assertThat(rolaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rolaRepository.findAll().size();
        // set the field null
        rola.setName(null);

        // Create the Rola, which fails.
        RolaDTO rolaDTO = rolaMapper.rolaToRolaDTO(rola);

        restRolaMockMvc.perform(post("/api/rolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolaDTO)))
            .andExpect(status().isBadRequest());

        List<Rola> rolaList = rolaRepository.findAll();
        assertThat(rolaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRolas() throws Exception {
        // Initialize the database
        rolaRepository.saveAndFlush(rola);

        // Get all the rolaList
        restRolaMockMvc.perform(get("/api/rolas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rola.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRola() throws Exception {
        // Initialize the database
        rolaRepository.saveAndFlush(rola);

        // Get the rola
        restRolaMockMvc.perform(get("/api/rolas/{id}", rola.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rola.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRola() throws Exception {
        // Get the rola
        restRolaMockMvc.perform(get("/api/rolas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRola() throws Exception {
        // Initialize the database
        rolaRepository.saveAndFlush(rola);
        int databaseSizeBeforeUpdate = rolaRepository.findAll().size();

        // Update the rola
        Rola updatedRola = rolaRepository.findOne(rola.getId());
        updatedRola
                .name(UPDATED_NAME);
        RolaDTO rolaDTO = rolaMapper.rolaToRolaDTO(updatedRola);

        restRolaMockMvc.perform(put("/api/rolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolaDTO)))
            .andExpect(status().isOk());

        // Validate the Rola in the database
        List<Rola> rolaList = rolaRepository.findAll();
        assertThat(rolaList).hasSize(databaseSizeBeforeUpdate);
        Rola testRola = rolaList.get(rolaList.size() - 1);
        assertThat(testRola.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRola() throws Exception {
        int databaseSizeBeforeUpdate = rolaRepository.findAll().size();

        // Create the Rola
        RolaDTO rolaDTO = rolaMapper.rolaToRolaDTO(rola);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRolaMockMvc.perform(put("/api/rolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolaDTO)))
            .andExpect(status().isCreated());

        // Validate the Rola in the database
        List<Rola> rolaList = rolaRepository.findAll();
        assertThat(rolaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRola() throws Exception {
        // Initialize the database
        rolaRepository.saveAndFlush(rola);
        int databaseSizeBeforeDelete = rolaRepository.findAll().size();

        // Get the rola
        restRolaMockMvc.perform(delete("/api/rolas/{id}", rola.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rola> rolaList = rolaRepository.findAll();
        assertThat(rolaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
