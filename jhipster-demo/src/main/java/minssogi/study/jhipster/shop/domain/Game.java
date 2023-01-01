package minssogi.study.jhipster.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "game_nm")
    private String gameNm;

    @Column(name = "game_cd")
    private String gameCd;

    @OneToMany(mappedBy = "game")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "game" }, allowSetters = true)
    private Set<GameServer> gameServerLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Game id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return this.gameId;
    }

    public Game gameId(Long gameId) {
        this.setGameId(gameId);
        return this;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameNm() {
        return this.gameNm;
    }

    public Game gameNm(String gameNm) {
        this.setGameNm(gameNm);
        return this;
    }

    public void setGameNm(String gameNm) {
        this.gameNm = gameNm;
    }

    public String getGameCd() {
        return this.gameCd;
    }

    public Game gameCd(String gameCd) {
        this.setGameCd(gameCd);
        return this;
    }

    public void setGameCd(String gameCd) {
        this.gameCd = gameCd;
    }

    public Set<GameServer> getGameServerLists() {
        return this.gameServerLists;
    }

    public void setGameServerLists(Set<GameServer> gameServers) {
        if (this.gameServerLists != null) {
            this.gameServerLists.forEach(i -> i.setGame(null));
        }
        if (gameServers != null) {
            gameServers.forEach(i -> i.setGame(this));
        }
        this.gameServerLists = gameServers;
    }

    public Game gameServerLists(Set<GameServer> gameServers) {
        this.setGameServerLists(gameServers);
        return this;
    }

    public Game addGameServerList(GameServer gameServer) {
        this.gameServerLists.add(gameServer);
        gameServer.setGame(this);
        return this;
    }

    public Game removeGameServerList(GameServer gameServer) {
        this.gameServerLists.remove(gameServer);
        gameServer.setGame(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        return id != null && id.equals(((Game) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", gameId=" + getGameId() +
            ", gameNm='" + getGameNm() + "'" +
            ", gameCd='" + getGameCd() + "'" +
            "}";
    }
}
