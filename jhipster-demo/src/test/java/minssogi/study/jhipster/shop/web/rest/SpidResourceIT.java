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
import minssogi.study.jhipster.shop.domain.Spid;
import minssogi.study.jhipster.shop.repository.SpidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SpidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpidResourceIT {

    private static final Long DEFAULT_SPID = 1L;
    private static final Long UPDATED_SPID = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/spids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpidRepository spidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpidMockMvc;

    private Spid spid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spid createEntity(EntityManager em) {
        Spid spid = new Spid().spid(DEFAULT_SPID).email(DEFAULT_EMAIL);
        return spid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spid createUpdatedEntity(EntityManager em) {
        Spid spid = new Spid().spid(UPDATED_SPID).email(UPDATED_EMAIL);
        return spid;
    }

    @BeforeEach
    public void initTest() {
        spid = createEntity(em);
    }

    @Test
    @Transactional
    void createSpid() throws Exception {
        int databaseSizeBeforeCreate = spidRepository.findAll().size();
        // Create the Spid
        restSpidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spid)))
            .andExpect(status().isCreated());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeCreate + 1);
        Spid testSpid = spidList.get(spidList.size() - 1);
        assertThat(testSpid.getSpid()).isEqualTo(DEFAULT_SPID);
        assertThat(testSpid.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createSpidWithExistingId() throws Exception {
        // Create the Spid with an existing ID
        spid.setId(1L);

        int databaseSizeBeforeCreate = spidRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spid)))
            .andExpect(status().isBadRequest());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpids() throws Exception {
        // Initialize the database
        spidRepository.saveAndFlush(spid);

        // Get all the spidList
        restSpidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spid.getId().intValue())))
            .andExpect(jsonPath("$.[*].spid").value(hasItem(DEFAULT_SPID.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getSpid() throws Exception {
        // Initialize the database
        spidRepository.saveAndFlush(spid);

        // Get the spid
        restSpidMockMvc
            .perform(get(ENTITY_API_URL_ID, spid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(spid.getId().intValue()))
            .andExpect(jsonPath("$.spid").value(DEFAULT_SPID.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingSpid() throws Exception {
        // Get the spid
        restSpidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSpid() throws Exception {
        // Initialize the database
        spidRepository.saveAndFlush(spid);

        int databaseSizeBeforeUpdate = spidRepository.findAll().size();

        // Update the spid
        Spid updatedSpid = spidRepository.findById(spid.getId()).get();
        // Disconnect from session so that the updates on updatedSpid are not directly saved in db
        em.detach(updatedSpid);
        updatedSpid.spid(UPDATED_SPID).email(UPDATED_EMAIL);

        restSpidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSpid))
            )
            .andExpect(status().isOk());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
        Spid testSpid = spidList.get(spidList.size() - 1);
        assertThat(testSpid.getSpid()).isEqualTo(UPDATED_SPID);
        assertThat(testSpid.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingSpid() throws Exception {
        int databaseSizeBeforeUpdate = spidRepository.findAll().size();
        spid.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, spid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(spid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpid() throws Exception {
        int databaseSizeBeforeUpdate = spidRepository.findAll().size();
        spid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(spid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpid() throws Exception {
        int databaseSizeBeforeUpdate = spidRepository.findAll().size();
        spid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(spid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpidWithPatch() throws Exception {
        // Initialize the database
        spidRepository.saveAndFlush(spid);

        int databaseSizeBeforeUpdate = spidRepository.findAll().size();

        // Update the spid using partial update
        Spid partialUpdatedSpid = new Spid();
        partialUpdatedSpid.setId(spid.getId());

        partialUpdatedSpid.spid(UPDATED_SPID);

        restSpidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpid))
            )
            .andExpect(status().isOk());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
        Spid testSpid = spidList.get(spidList.size() - 1);
        assertThat(testSpid.getSpid()).isEqualTo(UPDATED_SPID);
        assertThat(testSpid.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateSpidWithPatch() throws Exception {
        // Initialize the database
        spidRepository.saveAndFlush(spid);

        int databaseSizeBeforeUpdate = spidRepository.findAll().size();

        // Update the spid using partial update
        Spid partialUpdatedSpid = new Spid();
        partialUpdatedSpid.setId(spid.getId());

        partialUpdatedSpid.spid(UPDATED_SPID).email(UPDATED_EMAIL);

        restSpidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpid))
            )
            .andExpect(status().isOk());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
        Spid testSpid = spidList.get(spidList.size() - 1);
        assertThat(testSpid.getSpid()).isEqualTo(UPDATED_SPID);
        assertThat(testSpid.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingSpid() throws Exception {
        int databaseSizeBeforeUpdate = spidRepository.findAll().size();
        spid.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, spid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(spid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpid() throws Exception {
        int databaseSizeBeforeUpdate = spidRepository.findAll().size();
        spid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(spid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpid() throws Exception {
        int databaseSizeBeforeUpdate = spidRepository.findAll().size();
        spid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(spid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Spid in the database
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpid() throws Exception {
        // Initialize the database
        spidRepository.saveAndFlush(spid);

        int databaseSizeBeforeDelete = spidRepository.findAll().size();

        // Delete the spid
        restSpidMockMvc
            .perform(delete(ENTITY_API_URL_ID, spid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Spid> spidList = spidRepository.findAll();
        assertThat(spidList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
