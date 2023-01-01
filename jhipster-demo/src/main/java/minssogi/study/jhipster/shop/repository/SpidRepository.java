package minssogi.study.jhipster.shop.repository;

import minssogi.study.jhipster.shop.domain.Spid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Spid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpidRepository extends JpaRepository<Spid, Long> {}
