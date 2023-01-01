package minssogi.study.jhipster.shop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Gnid;
import minssogi.study.jhipster.shop.repository.GnidRepository;
import minssogi.study.jhipster.shop.service.GnidService;
import minssogi.study.jhipster.shop.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link minssogi.study.jhipster.shop.domain.Gnid}.
 */
@RestController
@RequestMapping("/api")
public class GnidResource {

    private final Logger log = LoggerFactory.getLogger(GnidResource.class);

    private static final String ENTITY_NAME = "gnid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GnidService gnidService;

    private final GnidRepository gnidRepository;

    public GnidResource(GnidService gnidService, GnidRepository gnidRepository) {
        this.gnidService = gnidService;
        this.gnidRepository = gnidRepository;
    }

    /**
     * {@code POST  /gnids} : Create a new gnid.
     *
     * @param gnid the gnid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gnid, or with status {@code 400 (Bad Request)} if the gnid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gnids")
    public ResponseEntity<Gnid> createGnid(@RequestBody Gnid gnid) throws URISyntaxException {
        log.debug("REST request to save Gnid : {}", gnid);
        if (gnid.getId() != null) {
            throw new BadRequestAlertException("A new gnid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gnid result = gnidService.save(gnid);
        return ResponseEntity
            .created(new URI("/api/gnids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gnids/:id} : Updates an existing gnid.
     *
     * @param id the id of the gnid to save.
     * @param gnid the gnid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gnid,
     * or with status {@code 400 (Bad Request)} if the gnid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gnid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gnids/{id}")
    public ResponseEntity<Gnid> updateGnid(@PathVariable(value = "id", required = false) final Long id, @RequestBody Gnid gnid)
        throws URISyntaxException {
        log.debug("REST request to update Gnid : {}, {}", id, gnid);
        if (gnid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gnid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gnidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Gnid result = gnidService.update(gnid);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gnid.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gnids/:id} : Partial updates given fields of an existing gnid, field will ignore if it is null
     *
     * @param id the id of the gnid to save.
     * @param gnid the gnid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gnid,
     * or with status {@code 400 (Bad Request)} if the gnid is not valid,
     * or with status {@code 404 (Not Found)} if the gnid is not found,
     * or with status {@code 500 (Internal Server Error)} if the gnid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gnids/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gnid> partialUpdateGnid(@PathVariable(value = "id", required = false) final Long id, @RequestBody Gnid gnid)
        throws URISyntaxException {
        log.debug("REST request to partial update Gnid partially : {}, {}", id, gnid);
        if (gnid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gnid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gnidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gnid> result = gnidService.partialUpdate(gnid);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gnid.getId().toString())
        );
    }

    /**
     * {@code GET  /gnids} : get all the gnids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gnids in body.
     */
    @GetMapping("/gnids")
    public List<Gnid> getAllGnids() {
        log.debug("REST request to get all Gnids");
        return gnidService.findAll();
    }

    /**
     * {@code GET  /gnids/:id} : get the "id" gnid.
     *
     * @param id the id of the gnid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gnid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gnids/{id}")
    public ResponseEntity<Gnid> getGnid(@PathVariable Long id) {
        log.debug("REST request to get Gnid : {}", id);
        Optional<Gnid> gnid = gnidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gnid);
    }

    /**
     * {@code DELETE  /gnids/:id} : delete the "id" gnid.
     *
     * @param id the id of the gnid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gnids/{id}")
    public ResponseEntity<Void> deleteGnid(@PathVariable Long id) {
        log.debug("REST request to delete Gnid : {}", id);
        gnidService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
