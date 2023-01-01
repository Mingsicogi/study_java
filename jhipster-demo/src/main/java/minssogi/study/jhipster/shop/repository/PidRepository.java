package minssogi.study.jhipster.shop.repository;

import minssogi.study.jhipster.shop.domain.Pid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PidRepository extends JpaRepository<Pid, Long> {}
