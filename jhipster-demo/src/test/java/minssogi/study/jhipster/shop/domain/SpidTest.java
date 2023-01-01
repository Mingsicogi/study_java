package minssogi.study.jhipster.shop.domain;

import static org.assertj.core.api.Assertions.assertThat;

import minssogi.study.jhipster.shop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Spid.class);
        Spid spid1 = new Spid();
        spid1.setId(1L);
        Spid spid2 = new Spid();
        spid2.setId(spid1.getId());
        assertThat(spid1).isEqualTo(spid2);
        spid2.setId(2L);
        assertThat(spid1).isNotEqualTo(spid2);
        spid1.setId(null);
        assertThat(spid1).isNotEqualTo(spid2);
    }
}
