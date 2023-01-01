package minssogi.study.jhipster.shop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Nid;
import minssogi.study.jhipster.shop.repository.NidRepository;
import minssogi.study.jhipster.shop.service.NidService;
import minssogi.study.jhipster.shop.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link minssogi.study.jhipster.shop.domain.Nid}.
 */
@RestController
@RequestMapping("/api")
public class NidResource {

    private final Logger log = LoggerFactory.getLogger(NidResource.class);

    private static final String ENTITY_NAME = "nid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NidService nidService;

    private final NidRepository nidRepository;

    public NidResource(NidService nidService, NidRepository nidRepository) {
        this.nidService = nidService;
        this.nidRepository = nidRepository;
    }

    /**
     * {@code POST  /nids} : Create a new nid.
     *
     * @param nid the nid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nid, or with status {@code 400 (Bad Request)} if the nid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nids")
    public ResponseEntity<Nid> createNid(@RequestBody Nid nid) throws URISyntaxException {
        log.debug("REST request to save Nid : {}", nid);
        if (nid.getId() != null) {
            throw new BadRequestAlertException("A new nid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nid result = nidService.save(nid);
        return ResponseEntity
            .created(new URI("/api/nids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nids/:id} : Updates an existing nid.
     *
     * @param id the id of the nid to save.
     * @param nid the nid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nid,
     * or with status {@code 400 (Bad Request)} if the nid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nids/{id}")
    public ResponseEntity<Nid> updateNid(@PathVariable(value = "id", required = false) final Long id, @RequestBody Nid nid)
        throws URISyntaxException {
        log.debug("REST request to update Nid : {}, {}", id, nid);
        if (nid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Nid result = nidService.update(nid);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nid.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nids/:id} : Partial updates given fields of an existing nid, field will ignore if it is null
     *
     * @param id the id of the nid to save.
     * @param nid the nid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nid,
     * or with status {@code 400 (Bad Request)} if the nid is not valid,
     * or with status {@code 404 (Not Found)} if the nid is not found,
     * or with status {@code 500 (Internal Server Error)} if the nid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nids/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nid> partialUpdateNid(@PathVariable(value = "id", required = false) final Long id, @RequestBody Nid nid)
        throws URISyntaxException {
        log.debug("REST request to partial update Nid partially : {}, {}", id, nid);
        if (nid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nid> result = nidService.partialUpdate(nid);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nid.getId().toString())
        );
    }

    /**
     * {@code GET  /nids} : get all the nids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nids in body.
     */
    @GetMapping("/nids")
    public List<Nid> getAllNids() {
        log.debug("REST request to get all Nids");
        return nidService.findAll();
    }

    /**
     * {@code GET  /nids/:id} : get the "id" nid.
     *
     * @param id the id of the nid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nids/{id}")
    public ResponseEntity<Nid> getNid(@PathVariable Long id) {
        log.debug("REST request to get Nid : {}", id);
        Optional<Nid> nid = nidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nid);
    }

    /**
     * {@code DELETE  /nids/:id} : delete the "id" nid.
     *
     * @param id the id of the nid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nids/{id}")
    public ResponseEntity<Void> deleteNid(@PathVariable Long id) {
        log.debug("REST request to delete Nid : {}", id);
        nidService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
