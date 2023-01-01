package minssogi.study.jhipster.shop.service.impl;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Gnid;
import minssogi.study.jhipster.shop.repository.GnidRepository;
import minssogi.study.jhipster.shop.service.GnidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Gnid}.
 */
@Service
@Transactional
public class GnidServiceImpl implements GnidService {

    private final Logger log = LoggerFactory.getLogger(GnidServiceImpl.class);

    private final GnidRepository gnidRepository;

    public GnidServiceImpl(GnidRepository gnidRepository) {
        this.gnidRepository = gnidRepository;
    }

    @Override
    public Gnid save(Gnid gnid) {
        log.debug("Request to save Gnid : {}", gnid);
        return gnidRepository.save(gnid);
    }

    @Override
    public Gnid update(Gnid gnid) {
        log.debug("Request to save Gnid : {}", gnid);
        return gnidRepository.save(gnid);
    }

    @Override
    public Optional<Gnid> partialUpdate(Gnid gnid) {
        log.debug("Request to partially update Gnid : {}", gnid);

        return gnidRepository
            .findById(gnid.getId())
            .map(existingGnid -> {
                if (gnid.getGnid() != null) {
                    existingGnid.setGnid(gnid.getGnid());
                }
                if (gnid.getGameCd() != null) {
                    existingGnid.setGameCd(gnid.getGameCd());
                }

                return existingGnid;
            })
            .map(gnidRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Gnid> findAll() {
        log.debug("Request to get all Gnids");
        return gnidRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Gnid> findOne(Long id) {
        log.debug("Request to get Gnid : {}", id);
        return gnidRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gnid : {}", id);
        gnidRepository.deleteById(id);
    }
}
