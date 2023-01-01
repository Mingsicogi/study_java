package minssogi.study.jhipster.shop.domain;

import static org.assertj.core.api.Assertions.assertThat;

import minssogi.study.jhipster.shop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pid.class);
        Pid pid1 = new Pid();
        pid1.setId(1L);
        Pid pid2 = new Pid();
        pid2.setId(pid1.getId());
        assertThat(pid1).isEqualTo(pid2);
        pid2.setId(2L);
        assertThat(pid1).isNotEqualTo(pid2);
        pid1.setId(null);
        assertThat(pid1).isNotEqualTo(pid2);
    }
}
