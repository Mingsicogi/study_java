package minssogi.study.jhipster.shop.service;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Gnid;

/**
 * Service Interface for managing {@link Gnid}.
 */
public interface GnidService {
    /**
     * Save a gnid.
     *
     * @param gnid the entity to save.
     * @return the persisted entity.
     */
    Gnid save(Gnid gnid);

    /**
     * Updates a gnid.
     *
     * @param gnid the entity to update.
     * @return the persisted entity.
     */
    Gnid update(Gnid gnid);

    /**
     * Partially updates a gnid.
     *
     * @param gnid the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Gnid> partialUpdate(Gnid gnid);

    /**
     * Get all the gnids.
     *
     * @return the list of entities.
     */
    List<Gnid> findAll();

    /**
     * Get the "id" gnid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Gnid> findOne(Long id);

    /**
     * Delete the "id" gnid.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
