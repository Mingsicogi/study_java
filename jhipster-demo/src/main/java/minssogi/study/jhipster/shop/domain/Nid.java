package minssogi.study.jhipster.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Nid.
 */
@Entity
@Table(name = "nid")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nid")
    private Long nid;

    @ManyToOne
    @JsonIgnoreProperties(value = { "game", "spidLists", "spid" }, allowSetters = true)
    private Gnid gnid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNid() {
        return this.nid;
    }

    public Nid nid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Gnid getGnid() {
        return this.gnid;
    }

    public void setGnid(Gnid gnid) {
        this.gnid = gnid;
    }

    public Nid gnid(Gnid gnid) {
        this.setGnid(gnid);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nid)) {
            return false;
        }
        return id != null && id.equals(((Nid) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nid{" +
            "id=" + getId() +
            ", nid=" + getNid() +
            "}";
    }
}
