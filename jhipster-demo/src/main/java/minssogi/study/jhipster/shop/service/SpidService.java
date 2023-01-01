package minssogi.study.jhipster.shop.service;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Spid;

/**
 * Service Interface for managing {@link Spid}.
 */
public interface SpidService {
    /**
     * Save a spid.
     *
     * @param spid the entity to save.
     * @return the persisted entity.
     */
    Spid save(Spid spid);

    /**
     * Updates a spid.
     *
     * @param spid the entity to update.
     * @return the persisted entity.
     */
    Spid update(Spid spid);

    /**
     * Partially updates a spid.
     *
     * @param spid the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Spid> partialUpdate(Spid spid);

    /**
     * Get all the spids.
     *
     * @return the list of entities.
     */
    List<Spid> findAll();

    /**
     * Get the "id" spid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Spid> findOne(Long id);

    /**
     * Delete the "id" spid.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
