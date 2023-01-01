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
import minssogi.study.jhipster.shop.domain.Pid;
import minssogi.study.jhipster.shop.repository.PidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PidResourceIT {

    private static final Long DEFAULT_PID = 1L;
    private static final Long UPDATED_PID = 2L;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CI = "AAAAAAAAAA";
    private static final String UPDATED_CI = "BBBBBBBBBB";

    private static final String DEFAULT_DI = "AAAAAAAAAA";
    private static final String UPDATED_DI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PidRepository pidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPidMockMvc;

    private Pid pid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pid createEntity(EntityManager em) {
        Pid pid = new Pid().pid(DEFAULT_PID).phone(DEFAULT_PHONE).ci(DEFAULT_CI).di(DEFAULT_DI);
        return pid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pid createUpdatedEntity(EntityManager em) {
        Pid pid = new Pid().pid(UPDATED_PID).phone(UPDATED_PHONE).ci(UPDATED_CI).di(UPDATED_DI);
        return pid;
    }

    @BeforeEach
    public void initTest() {
        pid = createEntity(em);
    }

    @Test
    @Transactional
    void createPid() throws Exception {
        int databaseSizeBeforeCreate = pidRepository.findAll().size();
        // Create the Pid
        restPidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pid)))
            .andExpect(status().isCreated());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeCreate + 1);
        Pid testPid = pidList.get(pidList.size() - 1);
        assertThat(testPid.getPid()).isEqualTo(DEFAULT_PID);
        assertThat(testPid.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPid.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testPid.getDi()).isEqualTo(DEFAULT_DI);
    }

    @Test
    @Transactional
    void createPidWithExistingId() throws Exception {
        // Create the Pid with an existing ID
        pid.setId(1L);

        int databaseSizeBeforeCreate = pidRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pid)))
            .andExpect(status().isBadRequest());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPids() throws Exception {
        // Initialize the database
        pidRepository.saveAndFlush(pid);

        // Get all the pidList
        restPidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pid.getId().intValue())))
            .andExpect(jsonPath("$.[*].pid").value(hasItem(DEFAULT_PID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].di").value(hasItem(DEFAULT_DI)));
    }

    @Test
    @Transactional
    void getPid() throws Exception {
        // Initialize the database
        pidRepository.saveAndFlush(pid);

        // Get the pid
        restPidMockMvc
            .perform(get(ENTITY_API_URL_ID, pid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pid.getId().intValue()))
            .andExpect(jsonPath("$.pid").value(DEFAULT_PID.intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.di").value(DEFAULT_DI));
    }

    @Test
    @Transactional
    void getNonExistingPid() throws Exception {
        // Get the pid
        restPidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPid() throws Exception {
        // Initialize the database
        pidRepository.saveAndFlush(pid);

        int databaseSizeBeforeUpdate = pidRepository.findAll().size();

        // Update the pid
        Pid updatedPid = pidRepository.findById(pid.getId()).get();
        // Disconnect from session so that the updates on updatedPid are not directly saved in db
        em.detach(updatedPid);
        updatedPid.pid(UPDATED_PID).phone(UPDATED_PHONE).ci(UPDATED_CI).di(UPDATED_DI);

        restPidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPid))
            )
            .andExpect(status().isOk());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
        Pid testPid = pidList.get(pidList.size() - 1);
        assertThat(testPid.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testPid.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPid.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testPid.getDi()).isEqualTo(UPDATED_DI);
    }

    @Test
    @Transactional
    void putNonExistingPid() throws Exception {
        int databaseSizeBeforeUpdate = pidRepository.findAll().size();
        pid.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pid.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPid() throws Exception {
        int databaseSizeBeforeUpdate = pidRepository.findAll().size();
        pid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPid() throws Exception {
        int databaseSizeBeforeUpdate = pidRepository.findAll().size();
        pid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePidWithPatch() throws Exception {
        // Initialize the database
        pidRepository.saveAndFlush(pid);

        int databaseSizeBeforeUpdate = pidRepository.findAll().size();

        // Update the pid using partial update
        Pid partialUpdatedPid = new Pid();
        partialUpdatedPid.setId(pid.getId());

        partialUpdatedPid.pid(UPDATED_PID).ci(UPDATED_CI);

        restPidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPid))
            )
            .andExpect(status().isOk());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
        Pid testPid = pidList.get(pidList.size() - 1);
        assertThat(testPid.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testPid.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPid.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testPid.getDi()).isEqualTo(DEFAULT_DI);
    }

    @Test
    @Transactional
    void fullUpdatePidWithPatch() throws Exception {
        // Initialize the database
        pidRepository.saveAndFlush(pid);

        int databaseSizeBeforeUpdate = pidRepository.findAll().size();

        // Update the pid using partial update
        Pid partialUpdatedPid = new Pid();
        partialUpdatedPid.setId(pid.getId());

        partialUpdatedPid.pid(UPDATED_PID).phone(UPDATED_PHONE).ci(UPDATED_CI).di(UPDATED_DI);

        restPidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPid))
            )
            .andExpect(status().isOk());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
        Pid testPid = pidList.get(pidList.size() - 1);
        assertThat(testPid.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testPid.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPid.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testPid.getDi()).isEqualTo(UPDATED_DI);
    }

    @Test
    @Transactional
    void patchNonExistingPid() throws Exception {
        int databaseSizeBeforeUpdate = pidRepository.findAll().size();
        pid.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPid() throws Exception {
        int databaseSizeBeforeUpdate = pidRepository.findAll().size();
        pid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPid() throws Exception {
        int databaseSizeBeforeUpdate = pidRepository.findAll().size();
        pid.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pid in the database
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePid() throws Exception {
        // Initialize the database
        pidRepository.saveAndFlush(pid);

        int databaseSizeBeforeDelete = pidRepository.findAll().size();

        // Delete the pid
        restPidMockMvc.perform(delete(ENTITY_API_URL_ID, pid.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pid> pidList = pidRepository.findAll();
        assertThat(pidList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
