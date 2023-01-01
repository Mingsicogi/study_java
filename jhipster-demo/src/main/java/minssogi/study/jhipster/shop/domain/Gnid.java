package minssogi.study.jhipster.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Gnid.
 */
@Entity
@Table(name = "gnid")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gnid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gnid")
    private Long gnid;

    @Column(name = "game_cd")
    private String gameCd;

    @JsonIgnoreProperties(value = { "gameServerLists" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Game game;

    @OneToMany(mappedBy = "gnid")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "gnid" }, allowSetters = true)
    private Set<Nid> spidLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "spidLists", "pid" }, allowSetters = true)
    private Spid spid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gnid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGnid() {
        return this.gnid;
    }

    public Gnid gnid(Long gnid) {
        this.setGnid(gnid);
        return this;
    }

    public void setGnid(Long gnid) {
        this.gnid = gnid;
    }

    public String getGameCd() {
        return this.gameCd;
    }

    public Gnid gameCd(String gameCd) {
        this.setGameCd(gameCd);
        return this;
    }

    public void setGameCd(String gameCd) {
        this.gameCd = gameCd;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Gnid game(Game game) {
        this.setGame(game);
        return this;
    }

    public Set<Nid> getSpidLists() {
        return this.spidLists;
    }

    public void setSpidLists(Set<Nid> nids) {
        if (this.spidLists != null) {
            this.spidLists.forEach(i -> i.setGnid(null));
        }
        if (nids != null) {
            nids.forEach(i -> i.setGnid(this));
        }
        this.spidLists = nids;
    }

    public Gnid spidLists(Set<Nid> nids) {
        this.setSpidLists(nids);
        return this;
    }

    public Gnid addSpidList(Nid nid) {
        this.spidLists.add(nid);
        nid.setGnid(this);
        return this;
    }

    public Gnid removeSpidList(Nid nid) {
        this.spidLists.remove(nid);
        nid.setGnid(null);
        return this;
    }

    public Spid getSpid() {
        return this.spid;
    }

    public void setSpid(Spid spid) {
        this.spid = spid;
    }

    public Gnid spid(Spid spid) {
        this.setSpid(spid);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gnid)) {
            return false;
        }
        return id != null && id.equals(((Gnid) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gnid{" +
            "id=" + getId() +
            ", gnid=" + getGnid() +
            ", gameCd='" + getGameCd() + "'" +
            "}";
    }
}
