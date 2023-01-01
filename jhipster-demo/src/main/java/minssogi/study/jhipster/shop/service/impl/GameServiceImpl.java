package minssogi.study.jhipster.shop.service.impl;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Game;
import minssogi.study.jhipster.shop.repository.GameRepository;
import minssogi.study.jhipster.shop.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Game}.
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game save(Game game) {
        log.debug("Request to save Game : {}", game);
        return gameRepository.save(game);
    }

    @Override
    public Game update(Game game) {
        log.debug("Request to save Game : {}", game);
        return gameRepository.save(game);
    }

    @Override
    public Optional<Game> partialUpdate(Game game) {
        log.debug("Request to partially update Game : {}", game);

        return gameRepository
            .findById(game.getId())
            .map(existingGame -> {
                if (game.getGameId() != null) {
                    existingGame.setGameId(game.getGameId());
                }
                if (game.getGameNm() != null) {
                    existingGame.setGameNm(game.getGameNm());
                }
                if (game.getGameCd() != null) {
                    existingGame.setGameCd(game.getGameCd());
                }

                return existingGame;
            })
            .map(gameRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> findAll() {
        log.debug("Request to get all Games");
        return gameRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Game> findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        return gameRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.deleteById(id);
    }
}
