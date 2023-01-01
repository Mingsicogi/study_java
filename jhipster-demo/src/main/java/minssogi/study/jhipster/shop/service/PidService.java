package minssogi.study.jhipster.shop.service;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Pid;

/**
 * Service Interface for managing {@link Pid}.
 */
public interface PidService {
    /**
     * Save a pid.
     *
     * @param pid the entity to save.
     * @return the persisted entity.
     */
    Pid save(Pid pid);

    /**
     * Updates a pid.
     *
     * @param pid the entity to update.
     * @return the persisted entity.
     */
    Pid update(Pid pid);

    /**
     * Partially updates a pid.
     *
     * @param pid the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Pid> partialUpdate(Pid pid);

    /**
     * Get all the pids.
     *
     * @return the list of entities.
     */
    List<Pid> findAll();

    /**
     * Get the "id" pid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pid> findOne(Long id);

    /**
     * Delete the "id" pid.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
