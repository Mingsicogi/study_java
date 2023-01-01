package minssogi.study.jhipster.shop.domain;

import static org.assertj.core.api.Assertions.assertThat;

import minssogi.study.jhipster.shop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nid.class);
        Nid nid1 = new Nid();
        nid1.setId(1L);
        Nid nid2 = new Nid();
        nid2.setId(nid1.getId());
        assertThat(nid1).isEqualTo(nid2);
        nid2.setId(2L);
        assertThat(nid1).isNotEqualTo(nid2);
        nid1.setId(null);
        assertThat(nid1).isNotEqualTo(nid2);
    }
}
