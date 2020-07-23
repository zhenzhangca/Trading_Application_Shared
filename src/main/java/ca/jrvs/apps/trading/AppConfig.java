package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.repositoris.models.config.MarketDataConfig;
import lombok.*;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig {
    private Logger logger = LoggerFactory.getLogger(AppConfig.class);
    //Can hard code this for now or use env_var
//    @Value("https://cloud.iexapis.com/v1")
//    private String iex_host;
    @Value("${app.host}")
    private String iex_host;
    @Value("${app.token")
    private String iex_token;


//    @Bean
//    public PlatformTransactionManager txManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

    @Bean
    public MarketDataConfig marketDataConfig() {
//        if (StringUtil.isEmpty(System.getenv("IEX_PUB_TOKEN")) || StringUtil.isEmpty(iex_host)) {
//            throw new IllegalArgumentException("ENV: IEX_PUB_TOKEN or property:iex_host is not set");
//        }
        MarketDataConfig marketDataConfig = MarketDataConfig.builder()
                .host(iex_host)
                .token(iex_token)
                .build();
        return marketDataConfig;
    }

//    @Bean
//    public DataSource dataSource() {
//        String jdbcUrl;
//        String user;
//        String password;
//        jdbcUrl = System.getenv("PSQL_URL");
//        user = System.getenv("PSQL_USER");
//        password = System.getenv("PSQL_PASSWORD");
//        logger.info("JDBC: " + jdbcUrl);
//        if (StringUtil.isEmpty(jdbcUrl, user, password)) {
//            throw new IllegalArgumentException("Missing datasource config env vars");
//        }
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setDriverClassName("org.postgresql.Driver");
//        basicDataSource.setUrl(jdbcUrl);
//        basicDataSource.setUsername(user);
//        basicDataSource.setPassword(password);
//        return basicDataSource;
//    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }
}
