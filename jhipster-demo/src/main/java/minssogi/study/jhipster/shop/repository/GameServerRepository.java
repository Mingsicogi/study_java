package minssogi.study.jhipster.shop.repository;

import minssogi.study.jhipster.shop.domain.GameServer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GameServer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameServerRepository extends JpaRepository<GameServer, Long> {}
