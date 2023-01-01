package minssogi.study.jhipster.shop.service.impl;

import java.util.List;
import java.util.Optional;
import minssogi.study.jhipster.shop.domain.Pid;
import minssogi.study.jhipster.shop.repository.PidRepository;
import minssogi.study.jhipster.shop.service.PidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pid}.
 */
@Service
@Transactional
public class PidServiceImpl implements PidService {

    private final Logger log = LoggerFactory.getLogger(PidServiceImpl.class);

    private final PidRepository pidRepository;

    public PidServiceImpl(PidRepository pidRepository) {
        this.pidRepository = pidRepository;
    }

    @Override
    public Pid save(Pid pid) {
        log.debug("Request to save Pid : {}", pid);
        return pidRepository.save(pid);
    }

    @Override
    public Pid update(Pid pid) {
        log.debug("Request to save Pid : {}", pid);
        return pidRepository.save(pid);
    }

    @Override
    public Optional<Pid> partialUpdate(Pid pid) {
        log.debug("Request to partially update Pid : {}", pid);

        return pidRepository
            .findById(pid.getId())
            .map(existingPid -> {
                if (pid.getPid() != null) {
                    existingPid.setPid(pid.getPid());
                }
                if (pid.getPhone() != null) {
                    existingPid.setPhone(pid.getPhone());
                }
                if (pid.getCi() != null) {
                    existingPid.setCi(pid.getCi());
                }
                if (pid.getDi() != null) {
                    existingPid.setDi(pid.getDi());
                }

                return existingPid;
            })
            .map(pidRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pid> findAll() {
        log.debug("Request to get all Pids");
        return pidRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pid> findOne(Long id) {
        log.debug("Request to get Pid : {}", id);
        return pidRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pid : {}", id);
        pidRepository.deleteById(id);
    }
}
