package minssogi.study.jhipster.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GameServer.
 */
@Entity
@Table(name = "game_server")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GameServer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "game_server_id")
    private String gameServerId;

    @Column(name = "region")
    private String region;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameServerLists" }, allowSetters = true)
    private Game game;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameServer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameServerId() {
        return this.gameServerId;
    }

    public GameServer gameServerId(String gameServerId) {
        this.setGameServerId(gameServerId);
        return this;
    }

    public void setGameServerId(String gameServerId) {
        this.gameServerId = gameServerId;
    }

    public String getRegion() {
        return this.region;
    }

    public GameServer region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameServer game(Game game) {
        this.setGame(game);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameServer)) {
            return false;
        }
        return id != null && id.equals(((GameServer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameServer{" +
            "id=" + getId() +
            ", gameServerId='" + getGameServerId() + "'" +
            ", region='" + getRegion() + "'" +
            "}";
    }
}
