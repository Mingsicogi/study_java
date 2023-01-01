package minssogi.study.aws.secretManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Configuration
public class DbConfiguration {

    ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    @Primary
    public HikariConfig hikariConfig() {
        DbSecretProperties dbSecretPropertiesFromSecretManager = getDbSecretPropertiesFromSecretManager();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(dbSecretPropertiesFromSecretManager.getUsername());
        hikariConfig.setPassword(dbSecretPropertiesFromSecretManager.getPassword());
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl(dbSecretPropertiesFromSecretManager.getHost());

        return hikariConfig;
    }

    @Bean
    public HikariDataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "transactionManager")
    @Primary
    public JpaTransactionManager jpaTransactionManager(HikariDataSource dataSource) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource);

        return jpaTransactionManager;
    }

    @SneakyThrows
    public DbSecretProperties getDbSecretPropertiesFromSecretManager() {

        String secretName = "prod/secretManagerPoc/admin";
        Region region = Region.of("ap-northeast-2");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("", ""))) //TODO in local env
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            // For a list of exceptions thrown, see
            // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
            throw e;
        }

        String secret = getSecretValueResponse.secretString();

        // Your code goes here.
        return objectMapper.readValue(secret, DbSecretProperties.class);
    }
}
