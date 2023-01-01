package minssogi.study.tsdb.app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ActiveSignal {

    private String gameCd;
    private String nid;
    private String loginIdp;


    public ActiveSignal(String gameCd, String nid, String loginIdp) {
        this.gameCd = gameCd;
        this.nid = nid;
        this.loginIdp = loginIdp;
    }
}
