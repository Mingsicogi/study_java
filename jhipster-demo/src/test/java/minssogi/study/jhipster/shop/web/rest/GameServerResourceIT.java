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
import minssogi.study.jhipster.shop.domain.GameServer;
import minssogi.study.jhipster.shop.repository.GameServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GameServerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GameServerResourceIT {

    private static final String DEFAULT_GAME_SERVER_ID = "AAAAAAAAAA";
    private static final String UPDATED_GAME_SERVER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/game-servers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GameServerRepository gameServerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameServerMockMvc;

    private GameServer gameServer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameServer createEntity(EntityManager em) {
        GameServer gameServer = new GameServer().gameServerId(DEFAULT_GAME_SERVER_ID).region(DEFAULT_REGION);
        return gameServer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameServer createUpdatedEntity(EntityManager em) {
        GameServer gameServer = new GameServer().gameServerId(UPDATED_GAME_SERVER_ID).region(UPDATED_REGION);
        return gameServer;
    }

    @BeforeEach
    public void initTest() {
        gameServer = createEntity(em);
    }

    @Test
    @Transactional
    void createGameServer() throws Exception {
        int databaseSizeBeforeCreate = gameServerRepository.findAll().size();
        // Create the GameServer
        restGameServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameServer)))
            .andExpect(status().isCreated());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeCreate + 1);
        GameServer testGameServer = gameServerList.get(gameServerList.size() - 1);
        assertThat(testGameServer.getGameServerId()).isEqualTo(DEFAULT_GAME_SERVER_ID);
        assertThat(testGameServer.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    void createGameServerWithExistingId() throws Exception {
        // Create the GameServer with an existing ID
        gameServer.setId(1L);

        int databaseSizeBeforeCreate = gameServerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameServer)))
            .andExpect(status().isBadRequest());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGameServers() throws Exception {
        // Initialize the database
        gameServerRepository.saveAndFlush(gameServer);

        // Get all the gameServerList
        restGameServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameServer.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameServerId").value(hasItem(DEFAULT_GAME_SERVER_ID)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)));
    }

    @Test
    @Transactional
    void getGameServer() throws Exception {
        // Initialize the database
        gameServerRepository.saveAndFlush(gameServer);

        // Get the gameServer
        restGameServerMockMvc
            .perform(get(ENTITY_API_URL_ID, gameServer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameServer.getId().intValue()))
            .andExpect(jsonPath("$.gameServerId").value(DEFAULT_GAME_SERVER_ID))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION));
    }

    @Test
    @Transactional
    void getNonExistingGameServer() throws Exception {
        // Get the gameServer
        restGameServerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGameServer() throws Exception {
        // Initialize the database
        gameServerRepository.saveAndFlush(gameServer);

        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();

        // Update the gameServer
        GameServer updatedGameServer = gameServerRepository.findById(gameServer.getId()).get();
        // Disconnect from session so that the updates on updatedGameServer are not directly saved in db
        em.detach(updatedGameServer);
        updatedGameServer.gameServerId(UPDATED_GAME_SERVER_ID).region(UPDATED_REGION);

        restGameServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGameServer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGameServer))
            )
            .andExpect(status().isOk());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
        GameServer testGameServer = gameServerList.get(gameServerList.size() - 1);
        assertThat(testGameServer.getGameServerId()).isEqualTo(UPDATED_GAME_SERVER_ID);
        assertThat(testGameServer.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    void putNonExistingGameServer() throws Exception {
        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();
        gameServer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameServer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameServer))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGameServer() throws Exception {
        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();
        gameServer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameServer))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGameServer() throws Exception {
        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();
        gameServer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameServerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameServer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameServerWithPatch() throws Exception {
        // Initialize the database
        gameServerRepository.saveAndFlush(gameServer);

        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();

        // Update the gameServer using partial update
        GameServer partialUpdatedGameServer = new GameServer();
        partialUpdatedGameServer.setId(gameServer.getId());

        restGameServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameServer))
            )
            .andExpect(status().isOk());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
        GameServer testGameServer = gameServerList.get(gameServerList.size() - 1);
        assertThat(testGameServer.getGameServerId()).isEqualTo(DEFAULT_GAME_SERVER_ID);
        assertThat(testGameServer.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    void fullUpdateGameServerWithPatch() throws Exception {
        // Initialize the database
        gameServerRepository.saveAndFlush(gameServer);

        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();

        // Update the gameServer using partial update
        GameServer partialUpdatedGameServer = new GameServer();
        partialUpdatedGameServer.setId(gameServer.getId());

        partialUpdatedGameServer.gameServerId(UPDATED_GAME_SERVER_ID).region(UPDATED_REGION);

        restGameServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameServer))
            )
            .andExpect(status().isOk());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
        GameServer testGameServer = gameServerList.get(gameServerList.size() - 1);
        assertThat(testGameServer.getGameServerId()).isEqualTo(UPDATED_GAME_SERVER_ID);
        assertThat(testGameServer.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    void patchNonExistingGameServer() throws Exception {
        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();
        gameServer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gameServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameServer))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGameServer() throws Exception {
        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();
        gameServer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameServer))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGameServer() throws Exception {
        int databaseSizeBeforeUpdate = gameServerRepository.findAll().size();
        gameServer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameServerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gameServer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameServer in the database
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGameServer() throws Exception {
        // Initialize the database
        gameServerRepository.saveAndFlush(gameServer);

        int databaseSizeBeforeDelete = gameServerRepository.findAll().size();

        // Delete the gameServer
        restGameServerMockMvc
            .perform(delete(ENTITY_API_URL_ID, gameServer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameServer> gameServerList = gameServerRepository.findAll();
        assertThat(gameServerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
