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
import minssogi.study.jhipster.shop.domain.Gnid;
import minssogi.study.jhipster.shop.repository.GnidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GnidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GnidResourceIT {

    private static final Long DEFAULT_GNID = 1L;
    private static final Long UPDATED_GNID = 2L;

    private static final String DEFAULT_GAME_CD = "AAAAAAAAAA";
    private static final String UPDATED_GAME_CD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gnids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GnidRepository gnidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGnidMockMvc;

    private Gnid gnid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gnid createEntity(EntityManager em) {
        Gnid gnid = new Gnid().gnid(DEFAULT_GNID).gameCd(DEFAULT_GAME_CD);
        return gnid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gnid createUpdatedEntity(EntityManager em) {
        Gnid gnid = new Gnid().gnid(UPDATED_GNID).gameCd(UPDATED_GAME_CD);
        return gnid;
    }

    @BeforeEach
    public void initTest() {
        gnid = createEntity(em);
    }

    @Test
    @Transactional
    void createGnid() throws Exception {
        int databaseSizeBeforeCreate = gnidRepository.findAll().size();
        // Create the Gnid
        restGnidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gnid)))
            .andExpect(status().isCreated());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeCreate + 1);
        Gnid testGnid = gnidList.get(gnidList.size() - 1);
        assertThat(testGnid.getGnid()).isEqualTo(DEFAULT_GNID);
        assertThat(testGnid.getGameCd()).isEqualTo(DEFAULT_GAME_CD);
    }

    @Test
    @Transactional
    void createGnidWithExistingId() throws Exception {
        // Create the Gnid with an existing ID
        gnid.setId(1L);

        int databaseSizeBeforeCreate = gnidRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGnidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gnid)))
            .andExpect(status().isBadRequest());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGnids() throws Exception {
        // Initialize the database
        gnidRepository.saveAndFlush(gnid);

        // Get all the gnidList
        restGnidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gnid.getId().intValue())))
            .andExpect(jsonPath("$.[*].gnid").value(hasItem(DEFAULT_GNID.intValue())))
            .andExpect(jsonPath("$.[*].gameCd").value(hasItem(DEFAULT_GAME_CD)));
    }

    @Test
    @Transactional
    void getGnid() throws Exception {
        // Initialize the database
        gnidRepository.saveAndFlush(gnid);

        // Get the gnid
        restGnidMockMvc
            .perform(get(ENTITY_API_URL_ID, gnid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gnid.getId().intValue()))
            .andExpect(jsonPath("$.gnid").value(DEFAULT_GNID.intValue()))
            .andExpect(jsonPath("$.gameCd").value(DEFAULT_GAME_CD));
    }

    @Test
    @Transactional
    void getNonExistingGnid() throws Exception {
        // Get the gnid
        restGnidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGnid() throws Exception {
        // Initialize the database
        gnidRepository.saveAndFlush(gnid);

        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();

        // Update the gnid
        Gnid updatedGnid = gnidRepository.findById(gnid.getId()).get();
        // Disconnect from session so that the updates on updatedGnid are not directly saved in db
        em.detach(updatedGnid);
        updatedGnid.gnid(UPDATED_GNID).gameCd(UPDATED_GAME_CD);

        restGnidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGnid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGnid))
            )
            .andExpect(status().isOk());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
        Gnid testGnid = gnidList.get(gnidList.size() - 1);
        assertThat(testGnid.getGnid()).isEqualTo(UPDATED_GNID);
        assertThat(testGnid.getGameCd()).isEqualTo(UPDATED_GAME_CD);
    }

    @Test
    @Transactional
    void putNonExistingGnid() throws Exception {
        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();
        gnid.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGnidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gnid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gnid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGnid() throws Exception {
        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();
        gnid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGnidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gnid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGnid() throws Exception {
        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();
        gnid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGnidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gnid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGnidWithPatch() throws Exception {
        // Initialize the database
        gnidRepository.saveAndFlush(gnid);

        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();

        // Update the gnid using partial update
        Gnid partialUpdatedGnid = new Gnid();
        partialUpdatedGnid.setId(gnid.getId());

        partialUpdatedGnid.gameCd(UPDATED_GAME_CD);

        restGnidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGnid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGnid))
            )
            .andExpect(status().isOk());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
        Gnid testGnid = gnidList.get(gnidList.size() - 1);
        assertThat(testGnid.getGnid()).isEqualTo(DEFAULT_GNID);
        assertThat(testGnid.getGameCd()).isEqualTo(UPDATED_GAME_CD);
    }

    @Test
    @Transactional
    void fullUpdateGnidWithPatch() throws Exception {
        // Initialize the database
        gnidRepository.saveAndFlush(gnid);

        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();

        // Update the gnid using partial update
        Gnid partialUpdatedGnid = new Gnid();
        partialUpdatedGnid.setId(gnid.getId());

        partialUpdatedGnid.gnid(UPDATED_GNID).gameCd(UPDATED_GAME_CD);

        restGnidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGnid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGnid))
            )
            .andExpect(status().isOk());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
        Gnid testGnid = gnidList.get(gnidList.size() - 1);
        assertThat(testGnid.getGnid()).isEqualTo(UPDATED_GNID);
        assertThat(testGnid.getGameCd()).isEqualTo(UPDATED_GAME_CD);
    }

    @Test
    @Transactional
    void patchNonExistingGnid() throws Exception {
        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();
        gnid.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGnidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gnid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gnid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGnid() throws Exception {
        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();
        gnid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGnidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gnid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGnid() throws Exception {
        int databaseSizeBeforeUpdate = gnidRepository.findAll().size();
        gnid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGnidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gnid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gnid in the database
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGnid() throws Exception {
        // Initialize the database
        gnidRepository.saveAndFlush(gnid);

        int databaseSizeBeforeDelete = gnidRepository.findAll().size();

        // Delete the gnid
        restGnidMockMvc
            .perform(delete(ENTITY_API_URL_ID, gnid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gnid> gnidList = gnidRepository.findAll();
        assertThat(gnidList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
