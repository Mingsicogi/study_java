package minssogi.study.jhipster.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Spid.
 */
@Entity
@Table(name = "spid")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Spid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "spid")
    private Long spid;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "spid")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "game", "spidLists", "spid" }, allowSetters = true)
    private Set<Gnid> spidLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "spidLists" }, allowSetters = true)
    private Pid pid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Spid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpid() {
        return this.spid;
    }

    public Spid spid(Long spid) {
        this.setSpid(spid);
        return this;
    }

    public void setSpid(Long spid) {
        this.spid = spid;
    }

    public String getEmail() {
        return this.email;
    }

    public Spid email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Gnid> getSpidLists() {
        return this.spidLists;
    }

    public void setSpidLists(Set<Gnid> gnids) {
        if (this.spidLists != null) {
            this.spidLists.forEach(i -> i.setSpid(null));
        }
        if (gnids != null) {
            gnids.forEach(i -> i.setSpid(this));
        }
        this.spidLists = gnids;
    }

    public Spid spidLists(Set<Gnid> gnids) {
        this.setSpidLists(gnids);
        return this;
    }

    public Spid addSpidList(Gnid gnid) {
        this.spidLists.add(gnid);
        gnid.setSpid(this);
        return this;
    }

    public Spid removeSpidList(Gnid gnid) {
        this.spidLists.remove(gnid);
        gnid.setSpid(null);
        return this;
    }

    public Pid getPid() {
        return this.pid;
    }

    public void setPid(Pid pid) {
        this.pid = pid;
    }

    public Spid pid(Pid pid) {
        this.setPid(pid);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Spid)) {
            return false;
        }
        return id != null && id.equals(((Spid) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Spid{" +
            "id=" + getId() +
            ", spid=" + getSpid() +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
