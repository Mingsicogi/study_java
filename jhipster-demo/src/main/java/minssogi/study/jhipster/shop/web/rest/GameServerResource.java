package minssogi.study.jhipster.shop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.GameServer;
import minssogi.study.jhipster.shop.repository.GameServerRepository;
import minssogi.study.jhipster.shop.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link minssogi.study.jhipster.shop.domain.GameServer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GameServerResource {

    private final Logger log = LoggerFactory.getLogger(GameServerResource.class);

    private static final String ENTITY_NAME = "gameServer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameServerRepository gameServerRepository;

    public GameServerResource(GameServerRepository gameServerRepository) {
        this.gameServerRepository = gameServerRepository;
    }

    /**
     * {@code POST  /game-servers} : Create a new gameServer.
     *
     * @param gameServer the gameServer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameServer, or with status {@code 400 (Bad Request)} if the gameServer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-servers")
    public ResponseEntity<GameServer> createGameServer(@RequestBody GameServer gameServer) throws URISyntaxException {
        log.debug("REST request to save GameServer : {}", gameServer);
        if (gameServer.getId() != null) {
            throw new BadRequestAlertException("A new gameServer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameServer result = gameServerRepository.save(gameServer);
        return ResponseEntity
            .created(new URI("/api/game-servers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-servers/:id} : Updates an existing gameServer.
     *
     * @param id the id of the gameServer to save.
     * @param gameServer the gameServer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameServer,
     * or with status {@code 400 (Bad Request)} if the gameServer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameServer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-servers/{id}")
    public ResponseEntity<GameServer> updateGameServer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameServer gameServer
    ) throws URISyntaxException {
        log.debug("REST request to update GameServer : {}, {}", id, gameServer);
        if (gameServer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameServer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameServerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GameServer result = gameServerRepository.save(gameServer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameServer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /game-servers/:id} : Partial updates given fields of an existing gameServer, field will ignore if it is null
     *
     * @param id the id of the gameServer to save.
     * @param gameServer the gameServer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameServer,
     * or with status {@code 400 (Bad Request)} if the gameServer is not valid,
     * or with status {@code 404 (Not Found)} if the gameServer is not found,
     * or with status {@code 500 (Internal Server Error)} if the gameServer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/game-servers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GameServer> partialUpdateGameServer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameServer gameServer
    ) throws URISyntaxException {
        log.debug("REST request to partial update GameServer partially : {}, {}", id, gameServer);
        if (gameServer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameServer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameServerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GameServer> result = gameServerRepository
            .findById(gameServer.getId())
            .map(existingGameServer -> {
                if (gameServer.getGameServerId() != null) {
                    existingGameServer.setGameServerId(gameServer.getGameServerId());
                }
                if (gameServer.getRegion() != null) {
                    existingGameServer.setRegion(gameServer.getRegion());
                }

                return existingGameServer;
            })
            .map(gameServerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameServer.getId().toString())
        );
    }

    /**
     * {@code GET  /game-servers} : get all the gameServers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameServers in body.
     */
    @GetMapping("/game-servers")
    public List<GameServer> getAllGameServers() {
        log.debug("REST request to get all GameServers");
        return gameServerRepository.findAll();
    }

    /**
     * {@code GET  /game-servers/:id} : get the "id" gameServer.
     *
     * @param id the id of the gameServer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameServer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-servers/{id}")
    public ResponseEntity<GameServer> getGameServer(@PathVariable Long id) {
        log.debug("REST request to get GameServer : {}", id);
        Optional<GameServer> gameServer = gameServerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gameServer);
    }

    /**
     * {@code DELETE  /game-servers/:id} : delete the "id" gameServer.
     *
     * @param id the id of the gameServer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-servers/{id}")
    public ResponseEntity<Void> deleteGameServer(@PathVariable Long id) {
        log.debug("REST request to delete GameServer : {}", id);
        gameServerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
