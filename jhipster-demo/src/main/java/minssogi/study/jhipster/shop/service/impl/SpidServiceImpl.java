package minssogi.study.jhipster.shop.service.impl;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Spid;
import minssogi.study.jhipster.shop.repository.SpidRepository;
import minssogi.study.jhipster.shop.service.SpidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Spid}.
 */
@Service
@Transactional
public class SpidServiceImpl implements SpidService {

    private final Logger log = LoggerFactory.getLogger(SpidServiceImpl.class);

    private final SpidRepository spidRepository;

    public SpidServiceImpl(SpidRepository spidRepository) {
        this.spidRepository = spidRepository;
    }

    @Override
    public Spid save(Spid spid) {
        log.debug("Request to save Spid : {}", spid);
        return spidRepository.save(spid);
    }

    @Override
    public Spid update(Spid spid) {
        log.debug("Request to save Spid : {}", spid);
        return spidRepository.save(spid);
    }

    @Override
    public Optional<Spid> partialUpdate(Spid spid) {
        log.debug("Request to partially update Spid : {}", spid);

        return spidRepository
            .findById(spid.getId())
            .map(existingSpid -> {
                if (spid.getSpid() != null) {
                    existingSpid.setSpid(spid.getSpid());
                }
                if (spid.getEmail() != null) {
                    existingSpid.setEmail(spid.getEmail());
                }

                return existingSpid;
            })
            .map(spidRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Spid> findAll() {
        log.debug("Request to get all Spids");
        return spidRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Spid> findOne(Long id) {
        log.debug("Request to get Spid : {}", id);
        return spidRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Spid : {}", id);
        spidRepository.deleteById(id);
    }
}
