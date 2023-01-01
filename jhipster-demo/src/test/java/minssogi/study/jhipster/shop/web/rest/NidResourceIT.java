package minssogi.study.jhipster.shop.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import minssogi.study.jhipster.shop.IntegrationTest;
import minssogi.study.jhipster.shop.domain.Nid;
import minssogi.study.jhipster.shop.repository.NidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NidResourceIT {

    private static final Long DEFAULT_NID = 1L;
    private static final Long UPDATED_NID = 2L;

    private static final String ENTITY_API_URL = "/api/nids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NidRepository nidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNidMockMvc;

    private Nid nid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nid createEntity(EntityManager em) {
        Nid nid = new Nid().nid(DEFAULT_NID);
        return nid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nid createUpdatedEntity(EntityManager em) {
        Nid nid = new Nid().nid(UPDATED_NID);
        return nid;
    }

    @BeforeEach
    public void initTest() {
        nid = createEntity(em);
    }

    @Test
    @Transactional
    void createNid() throws Exception {
        int databaseSizeBeforeCreate = nidRepository.findAll().size();
        // Create the Nid
        restNidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nid)))
            .andExpect(status().isCreated());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeCreate + 1);
        Nid testNid = nidList.get(nidList.size() - 1);
        assertThat(testNid.getNid()).isEqualTo(DEFAULT_NID);
    }

    @Test
    @Transactional
    void createNidWithExistingId() throws Exception {
        // Create the Nid with an existing ID
        nid.setId(1L);

        int databaseSizeBeforeCreate = nidRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nid)))
            .andExpect(status().isBadRequest());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNids() throws Exception {
        // Initialize the database
        nidRepository.saveAndFlush(nid);

        // Get all the nidList
        restNidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nid.getId().intValue())))
            .andExpect(jsonPath("$.[*].nid").value(hasItem(DEFAULT_NID.intValue())));
    }

    @Test
    @Transactional
    void getNid() throws Exception {
        // Initialize the database
        nidRepository.saveAndFlush(nid);

        // Get the nid
        restNidMockMvc
            .perform(get(ENTITY_API_URL_ID, nid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nid.getId().intValue()))
            .andExpect(jsonPath("$.nid").value(DEFAULT_NID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingNid() throws Exception {
        // Get the nid
        restNidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNid() throws Exception {
        // Initialize the database
        nidRepository.saveAndFlush(nid);

        int databaseSizeBeforeUpdate = nidRepository.findAll().size();

        // Update the nid
        Nid updatedNid = nidRepository.findById(nid.getId()).get();
        // Disconnect from session so that the updates on updatedNid are not directly saved in db
        em.detach(updatedNid);
        updatedNid.nid(UPDATED_NID);

        restNidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNid))
            )
            .andExpect(status().isOk());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
        Nid testNid = nidList.get(nidList.size() - 1);
        assertThat(testNid.getNid()).isEqualTo(UPDATED_NID);
    }

    @Test
    @Transactional
    void putNonExistingNid() throws Exception {
        int databaseSizeBeforeUpdate = nidRepository.findAll().size();
        nid.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nid.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNid() throws Exception {
        int databaseSizeBeforeUpdate = nidRepository.findAll().size();
        nid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNid() throws Exception {
        int databaseSizeBeforeUpdate = nidRepository.findAll().size();
        nid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNidWithPatch() throws Exception {
        // Initialize the database
        nidRepository.saveAndFlush(nid);

        int databaseSizeBeforeUpdate = nidRepository.findAll().size();

        // Update the nid using partial update
        Nid partialUpdatedNid = new Nid();
        partialUpdatedNid.setId(nid.getId());

        restNidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNid))
            )
            .andExpect(status().isOk());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
        Nid testNid = nidList.get(nidList.size() - 1);
        assertThat(testNid.getNid()).isEqualTo(DEFAULT_NID);
    }

    @Test
    @Transactional
    void fullUpdateNidWithPatch() throws Exception {
        // Initialize the database
        nidRepository.saveAndFlush(nid);

        int databaseSizeBeforeUpdate = nidRepository.findAll().size();

        // Update the nid using partial update
        Nid partialUpdatedNid = new Nid();
        partialUpdatedNid.setId(nid.getId());

        partialUpdatedNid.nid(UPDATED_NID);

        restNidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNid))
            )
            .andExpect(status().isOk());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
        Nid testNid = nidList.get(nidList.size() - 1);
        assertThat(testNid.getNid()).isEqualTo(UPDATED_NID);
    }

    @Test
    @Transactional
    void patchNonExistingNid() throws Exception {
        int databaseSizeBeforeUpdate = nidRepository.findAll().size();
        nid.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNid() throws Exception {
        int databaseSizeBeforeUpdate = nidRepository.findAll().size();
        nid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNid() throws Exception {
        int databaseSizeBeforeUpdate = nidRepository.findAll().size();
        nid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nid in the database
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNid() throws Exception {
        // Initialize the database
        nidRepository.saveAndFlush(nid);

        int databaseSizeBeforeDelete = nidRepository.findAll().size();

        // Delete the nid
        restNidMockMvc.perform(delete(ENTITY_API_URL_ID, nid.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nid> nidList = nidRepository.findAll();
        assertThat(nidList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
