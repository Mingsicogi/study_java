package minssogi.study.jhipster.shop.service;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Game;

/**
 * Service Interface for managing {@link Game}.
 */
public interface GameService {
    /**
     * Save a game.
     *
     * @param game the entity to save.
     * @return the persisted entity.
     */
    Game save(Game game);

    /**
     * Updates a game.
     *
     * @param game the entity to update.
     * @return the persisted entity.
     */
    Game update(Game game);

    /**
     * Partially updates a game.
     *
     * @param game the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Game> partialUpdate(Game game);

    /**
     * Get all the games.
     *
     * @return the list of entities.
     */
    List<Game> findAll();

    /**
     * Get the "id" game.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Game> findOne(Long id);

    /**
     * Delete the "id" game.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
