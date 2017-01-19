package org.jhipster.todo.web.rest;

import org.jhipster.todo.TodolistApp;

import org.jhipster.todo.domain.Lista;
import org.jhipster.todo.repository.ListaRepository;
import org.jhipster.todo.service.ListaService;
import org.jhipster.todo.service.dto.ListaDTO;
import org.jhipster.todo.service.mapper.ListaMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ListaResource REST controller.
 *
 * @see ListaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TodolistApp.class)
public class ListaResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Inject
    private ListaRepository listaRepository;

    @Inject
    private ListaMapper listaMapper;

    @Inject
    private ListaService listaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restListaMockMvc;

    private Lista lista;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ListaResource listaResource = new ListaResource();
        ReflectionTestUtils.setField(listaResource, "listaService", listaService);
        this.restListaMockMvc = MockMvcBuilders.standaloneSetup(listaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lista createEntity(EntityManager em) {
        Lista lista = new Lista()
                .title(DEFAULT_TITLE)
                .text(DEFAULT_TEXT)
                .date(DEFAULT_DATE)
                .status(DEFAULT_STATUS);
        return lista;
    }

    @Before
    public void initTest() {
        lista = createEntity(em);
    }

    @Test
    @Transactional
    public void createLista() throws Exception {
        int databaseSizeBeforeCreate = listaRepository.findAll().size();

        // Create the Lista
        ListaDTO listaDTO = listaMapper.listaToListaDTO(lista);

        restListaMockMvc.perform(post("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaDTO)))
            .andExpect(status().isCreated());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeCreate + 1);
        Lista testLista = listaList.get(listaList.size() - 1);
        assertThat(testLista.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLista.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testLista.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLista.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createListaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listaRepository.findAll().size();

        // Create the Lista with an existing ID
        Lista existingLista = new Lista();
        existingLista.setId(1L);
        ListaDTO existingListaDTO = listaMapper.listaToListaDTO(existingLista);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListaMockMvc.perform(post("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingListaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllListas() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);

        // Get all the listaList
        restListaMockMvc.perform(get("/api/listas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lista.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);

        // Get the lista
        restListaMockMvc.perform(get("/api/listas/{id}", lista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lista.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLista() throws Exception {
        // Get the lista
        restListaMockMvc.perform(get("/api/listas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);
        int databaseSizeBeforeUpdate = listaRepository.findAll().size();

        // Update the lista
        Lista updatedLista = listaRepository.findOne(lista.getId());
        updatedLista
                .title(UPDATED_TITLE)
                .text(UPDATED_TEXT)
                .date(UPDATED_DATE)
                .status(UPDATED_STATUS);
        ListaDTO listaDTO = listaMapper.listaToListaDTO(updatedLista);

        restListaMockMvc.perform(put("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaDTO)))
            .andExpect(status().isOk());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeUpdate);
        Lista testLista = listaList.get(listaList.size() - 1);
        assertThat(testLista.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLista.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testLista.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLista.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingLista() throws Exception {
        int databaseSizeBeforeUpdate = listaRepository.findAll().size();

        // Create the Lista
        ListaDTO listaDTO = listaMapper.listaToListaDTO(lista);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restListaMockMvc.perform(put("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaDTO)))
            .andExpect(status().isCreated());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);
        int databaseSizeBeforeDelete = listaRepository.findAll().size();

        // Get the lista
        restListaMockMvc.perform(delete("/api/listas/{id}", lista.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
