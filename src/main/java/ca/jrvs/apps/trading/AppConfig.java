package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.util.StringUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${iex.host}")
    private String iex_host;

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MarketDataConfig marketDataConfig() {
        if(StringUtil.isEmpty(System.getenv("IEX_PUB_TOKEN"))||StringUtil.isEmpty(iex_host)){
            throw new IllegalArgumentException("ENV: IEX_PUB_TOKEN or property:iex_host is not set");
        }
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        marketDataConfig.setHost(iex_host);
        return marketDataConfig;
    }

    @Bean
    public DataSource dataSource() {

        String jdbcUrl;
        String user;
        String password;
        if(!StringUtil.isEmpty(System.getenv("RDBMS_HOSTNAME"))){
            logger.info("RDBMS_HOSTNAME: "+System.getenv("RDBMS_HOSTNAME"));
            logger.info("RDBMS_USERNAME: "+System.getenv("RDBMS_USERNAME"));
            logger.info("RDBMS_PASSWORD: "+System.getenv("RDBMS_PASSWORD"));
//            ???
            jdbcUrl = "jdbc:postgresql://" +System.getenv("RDBMS_HOSTNAME")+":"+System.getenv("PORT?")+"/jrvstradding";
            user = System.getenv("RDBMS_USERNAME");
            password = System.getenv("RDBMS_PASSWORD");
        }else{
            jdbcUrl = System.getenv("PSQL_URL");
            user = System.getenv("PSQL_USER");
            password = System.getenv("PSQL_PASSWORD");


        }
        logger.info("JDBC: "+jdbcUrl);
        if(StringUtil.isEmpty(jdbcUrl, user, password)){
            throw new IllegalArgumentException("Missing datasource config env vars");
        }

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

    //http://bit.ly/2tWTmzQ connectionPool
    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }
}
