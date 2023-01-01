package minssogi.study.jhipster.shop.repository;

import minssogi.study.jhipster.shop.domain.Nid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NidRepository extends JpaRepository<Nid, Long> {}
