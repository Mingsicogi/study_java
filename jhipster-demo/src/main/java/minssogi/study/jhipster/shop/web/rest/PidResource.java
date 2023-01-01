package minssogi.study.jhipster.shop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Pid;
import minssogi.study.jhipster.shop.repository.PidRepository;
import minssogi.study.jhipster.shop.service.PidService;
import minssogi.study.jhipster.shop.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link minssogi.study.jhipster.shop.domain.Pid}.
 */
@RestController
@RequestMapping("/api")
public class PidResource {

    private final Logger log = LoggerFactory.getLogger(PidResource.class);

    private static final String ENTITY_NAME = "pid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PidService pidService;

    private final PidRepository pidRepository;

    public PidResource(PidService pidService, PidRepository pidRepository) {
        this.pidService = pidService;
        this.pidRepository = pidRepository;
    }

    /**
     * {@code POST  /pids} : Create a new pid.
     *
     * @param pid the pid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pid, or with status {@code 400 (Bad Request)} if the pid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pids")
    public ResponseEntity<Pid> createPid(@RequestBody Pid pid) throws URISyntaxException {
        log.debug("REST request to save Pid : {}", pid);
        if (pid.getId() != null) {
            throw new BadRequestAlertException("A new pid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pid result = pidService.save(pid);
        return ResponseEntity
            .created(new URI("/api/pids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pids/:id} : Updates an existing pid.
     *
     * @param id the id of the pid to save.
     * @param pid the pid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pid,
     * or with status {@code 400 (Bad Request)} if the pid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pids/{id}")
    public ResponseEntity<Pid> updatePid(@PathVariable(value = "id", required = false) final Long id, @RequestBody Pid pid)
        throws URISyntaxException {
        log.debug("REST request to update Pid : {}, {}", id, pid);
        if (pid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pid result = pidService.update(pid);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pid.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pids/:id} : Partial updates given fields of an existing pid, field will ignore if it is null
     *
     * @param id the id of the pid to save.
     * @param pid the pid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pid,
     * or with status {@code 400 (Bad Request)} if the pid is not valid,
     * or with status {@code 404 (Not Found)} if the pid is not found,
     * or with status {@code 500 (Internal Server Error)} if the pid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pids/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pid> partialUpdatePid(@PathVariable(value = "id", required = false) final Long id, @RequestBody Pid pid)
        throws URISyntaxException {
        log.debug("REST request to partial update Pid partially : {}, {}", id, pid);
        if (pid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pid> result = pidService.partialUpdate(pid);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pid.getId().toString())
        );
    }

    /**
     * {@code GET  /pids} : get all the pids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pids in body.
     */
    @GetMapping("/pids")
    public List<Pid> getAllPids() {
        log.debug("REST request to get all Pids");
        return pidService.findAll();
    }

    /**
     * {@code GET  /pids/:id} : get the "id" pid.
     *
     * @param id the id of the pid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pids/{id}")
    public ResponseEntity<Pid> getPid(@PathVariable Long id) {
        log.debug("REST request to get Pid : {}", id);
        Optional<Pid> pid = pidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pid);
    }

    /**
     * {@code DELETE  /pids/:id} : delete the "id" pid.
     *
     * @param id the id of the pid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pids/{id}")
    public ResponseEntity<Void> deletePid(@PathVariable Long id) {
        log.debug("REST request to delete Pid : {}", id);
        pidService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
