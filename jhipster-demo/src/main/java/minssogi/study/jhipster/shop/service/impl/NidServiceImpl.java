package minssogi.study.jhipster.shop.service.impl;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Nid;
import minssogi.study.jhipster.shop.repository.NidRepository;
import minssogi.study.jhipster.shop.service.NidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nid}.
 */
@Service
@Transactional
public class NidServiceImpl implements NidService {

    private final Logger log = LoggerFactory.getLogger(NidServiceImpl.class);

    private final NidRepository nidRepository;

    public NidServiceImpl(NidRepository nidRepository) {
        this.nidRepository = nidRepository;
    }

    @Override
    public Nid save(Nid nid) {
        log.debug("Request to save Nid : {}", nid);
        return nidRepository.save(nid);
    }

    @Override
    public Nid update(Nid nid) {
        log.debug("Request to save Nid : {}", nid);
        return nidRepository.save(nid);
    }

    @Override
    public Optional<Nid> partialUpdate(Nid nid) {
        log.debug("Request to partially update Nid : {}", nid);

        return nidRepository
            .findById(nid.getId())
            .map(existingNid -> {
                if (nid.getNid() != null) {
                    existingNid.setNid(nid.getNid());
                }

                return existingNid;
            })
            .map(nidRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nid> findAll() {
        log.debug("Request to get all Nids");
        return nidRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Nid> findOne(Long id) {
        log.debug("Request to get Nid : {}", id);
        return nidRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nid : {}", id);
        nidRepository.deleteById(id);
    }
}
