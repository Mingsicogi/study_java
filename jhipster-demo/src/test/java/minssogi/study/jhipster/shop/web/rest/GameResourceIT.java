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
import minssogi.study.jhipster.shop.domain.Game;
import minssogi.study.jhipster.shop.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GameResourceIT {

    private static final Long DEFAULT_GAME_ID = 1L;
    private static final Long UPDATED_GAME_ID = 2L;

    private static final String DEFAULT_GAME_NM = "AAAAAAAAAA";
    private static final String UPDATED_GAME_NM = "BBBBBBBBBB";

    private static final String DEFAULT_GAME_CD = "AAAAAAAAAA";
    private static final String UPDATED_GAME_CD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/games";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameMockMvc;

    private Game game;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createEntity(EntityManager em) {
        Game game = new Game().gameId(DEFAULT_GAME_ID).gameNm(DEFAULT_GAME_NM).gameCd(DEFAULT_GAME_CD);
        return game;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createUpdatedEntity(EntityManager em) {
        Game game = new Game().gameId(UPDATED_GAME_ID).gameNm(UPDATED_GAME_NM).gameCd(UPDATED_GAME_CD);
        return game;
    }

    @BeforeEach
    public void initTest() {
        game = createEntity(em);
    }

    @Test
    @Transactional
    void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();
        // Create the Game
        restGameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getGameId()).isEqualTo(DEFAULT_GAME_ID);
        assertThat(testGame.getGameNm()).isEqualTo(DEFAULT_GAME_NM);
        assertThat(testGame.getGameCd()).isEqualTo(DEFAULT_GAME_CD);
    }

    @Test
    @Transactional
    void createGameWithExistingId() throws Exception {
        // Create the Game with an existing ID
        game.setId(1L);

        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get all the gameList
        restGameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameId").value(hasItem(DEFAULT_GAME_ID.intValue())))
            .andExpect(jsonPath("$.[*].gameNm").value(hasItem(DEFAULT_GAME_NM)))
            .andExpect(jsonPath("$.[*].gameCd").value(hasItem(DEFAULT_GAME_CD)));
    }

    @Test
    @Transactional
    void getGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get the game
        restGameMockMvc
            .perform(get(ENTITY_API_URL_ID, game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(game.getId().intValue()))
            .andExpect(jsonPath("$.gameId").value(DEFAULT_GAME_ID.intValue()))
            .andExpect(jsonPath("$.gameNm").value(DEFAULT_GAME_NM))
            .andExpect(jsonPath("$.gameCd").value(DEFAULT_GAME_CD));
    }

    @Test
    @Transactional
    void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game
        Game updatedGame = gameRepository.findById(game.getId()).get();
        // Disconnect from session so that the updates on updatedGame are not directly saved in db
        em.detach(updatedGame);
        updatedGame.gameId(UPDATED_GAME_ID).gameNm(UPDATED_GAME_NM).gameCd(UPDATED_GAME_CD);

        restGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGame.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGame))
            )
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getGameId()).isEqualTo(UPDATED_GAME_ID);
        assertThat(testGame.getGameNm()).isEqualTo(UPDATED_GAME_NM);
        assertThat(testGame.getGameCd()).isEqualTo(UPDATED_GAME_CD);
    }

    @Test
    @Transactional
    void putNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();
        game.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, game.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(game))
            )
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();
        game.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(game))
            )
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();
        game.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameWithPatch() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game using partial update
        Game partialUpdatedGame = new Game();
        partialUpdatedGame.setId(game.getId());

        restGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGame.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGame))
            )
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getGameId()).isEqualTo(DEFAULT_GAME_ID);
        assertThat(testGame.getGameNm()).isEqualTo(DEFAULT_GAME_NM);
        assertThat(testGame.getGameCd()).isEqualTo(DEFAULT_GAME_CD);
    }

    @Test
    @Transactional
    void fullUpdateGameWithPatch() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game using partial update
        Game partialUpdatedGame = new Game();
        partialUpdatedGame.setId(game.getId());

        partialUpdatedGame.gameId(UPDATED_GAME_ID).gameNm(UPDATED_GAME_NM).gameCd(UPDATED_GAME_CD);

        restGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGame.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGame))
            )
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getGameId()).isEqualTo(UPDATED_GAME_ID);
        assertThat(testGame.getGameNm()).isEqualTo(UPDATED_GAME_NM);
        assertThat(testGame.getGameCd()).isEqualTo(UPDATED_GAME_CD);
    }

    @Test
    @Transactional
    void patchNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();
        game.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, game.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(game))
            )
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();
        game.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(game))
            )
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();
        game.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        int databaseSizeBeforeDelete = gameRepository.findAll().size();

        // Delete the game
        restGameMockMvc
            .perform(delete(ENTITY_API_URL_ID, game.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
