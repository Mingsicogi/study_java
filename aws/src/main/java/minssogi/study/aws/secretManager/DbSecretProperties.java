package minssogi.study.aws.secretManager;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DbSecretProperties {
    private String username;
    private String password;
    private String engine;
    private String host;
    private Integer port;
    private String dbClusterIdentifier;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEngine() {
        return engine;
    }

    //TODO dynamically logic
    public String getHost() {
        return "jdbc:mysql://" + host + ":" + this.port + "/secretManager_poc";
    }

    public Integer getPort() {
        return port;
    }

    public String getDbClusterIdentifier() {
        return dbClusterIdentifier;
    }
}
