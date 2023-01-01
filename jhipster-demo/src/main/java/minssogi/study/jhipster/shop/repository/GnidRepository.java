package minssogi.study.jhipster.shop.repository;

import minssogi.study.jhipster.shop.domain.Gnid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Gnid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GnidRepository extends JpaRepository<Gnid, Long> {}
