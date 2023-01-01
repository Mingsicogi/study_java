package minssogi.study.jhipster.shop.domain;

import static org.assertj.core.api.Assertions.assertThat;

import minssogi.study.jhipster.shop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GameServerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameServer.class);
        GameServer gameServer1 = new GameServer();
        gameServer1.setId(1L);
        GameServer gameServer2 = new GameServer();
        gameServer2.setId(gameServer1.getId());
        assertThat(gameServer1).isEqualTo(gameServer2);
        gameServer2.setId(2L);
        assertThat(gameServer1).isNotEqualTo(gameServer2);
        gameServer1.setId(null);
        assertThat(gameServer1).isNotEqualTo(gameServer2);
    }
}
