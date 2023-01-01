package minssogi.study.jhipster.shop.service;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Nid;

/**
 * Service Interface for managing {@link Nid}.
 */
public interface NidService {
    /**
     * Save a nid.
     *
     * @param nid the entity to save.
     * @return the persisted entity.
     */
    Nid save(Nid nid);

    /**
     * Updates a nid.
     *
     * @param nid the entity to update.
     * @return the persisted entity.
     */
    Nid update(Nid nid);

    /**
     * Partially updates a nid.
     *
     * @param nid the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Nid> partialUpdate(Nid nid);

    /**
     * Get all the nids.
     *
     * @return the list of entities.
     */
    List<Nid> findAll();

    /**
     * Get the "id" nid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Nid> findOne(Long id);

    /**
     * Delete the "id" nid.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
