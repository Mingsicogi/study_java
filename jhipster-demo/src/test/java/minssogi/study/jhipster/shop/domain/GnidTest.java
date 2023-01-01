package minssogi.study.jhipster.shop.domain;

import static org.assertj.core.api.Assertions.assertThat;

import minssogi.study.jhipster.shop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GnidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gnid.class);
        Gnid gnid1 = new Gnid();
        gnid1.setId(1L);
        Gnid gnid2 = new Gnid();
        gnid2.setId(gnid1.getId());
        assertThat(gnid1).isEqualTo(gnid2);
        gnid2.setId(2L);
        assertThat(gnid1).isNotEqualTo(gnid2);
        gnid1.setId(null);
        assertThat(gnid1).isNotEqualTo(gnid2);
    }
}
