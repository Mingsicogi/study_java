package minssogi.study.jhipster.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pid.
 */
@Entity
@Table(name = "pid")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pid")
    private Long pid;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ci")
    private String ci;

    @Column(name = "di")
    private String di;

    @OneToMany(mappedBy = "pid")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "spidLists", "pid" }, allowSetters = true)
    private Set<Spid> spidLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return this.pid;
    }

    public Pid pid(Long pid) {
        this.setPid(pid);
        return this;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPhone() {
        return this.phone;
    }

    public Pid phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCi() {
        return this.ci;
    }

    public Pid ci(String ci) {
        this.setCi(ci);
        return this;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDi() {
        return this.di;
    }

    public Pid di(String di) {
        this.setDi(di);
        return this;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public Set<Spid> getSpidLists() {
        return this.spidLists;
    }

    public void setSpidLists(Set<Spid> spids) {
        if (this.spidLists != null) {
            this.spidLists.forEach(i -> i.setPid(null));
        }
        if (spids != null) {
            spids.forEach(i -> i.setPid(this));
        }
        this.spidLists = spids;
    }

    public Pid spidLists(Set<Spid> spids) {
        this.setSpidLists(spids);
        return this;
    }

    public Pid addSpidList(Spid spid) {
        this.spidLists.add(spid);
        spid.setPid(this);
        return this;
    }

    public Pid removeSpidList(Spid spid) {
        this.spidLists.remove(spid);
        spid.setPid(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pid)) {
            return false;
        }
        return id != null && id.equals(((Pid) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pid{" +
            "id=" + getId() +
            ", pid=" + getPid() +
            ", phone='" + getPhone() + "'" +
            ", ci='" + getCi() + "'" +
            ", di='" + getDi() + "'" +
            "}";
    }
}
