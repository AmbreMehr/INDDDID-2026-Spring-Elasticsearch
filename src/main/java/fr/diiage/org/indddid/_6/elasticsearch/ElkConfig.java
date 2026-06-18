package fr.diiage.org.indddid._6.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
public class ElkConfig {

    @Value("${elk.username}")
    private String elkUsername;

    @Value("${elk.password}")
    private String elkPassword;

    private ElasticsearchClient elkClientMaker(
        String host,
        String user,
        String password
    ) {
        return ElasticsearchClient.of(c ->
            c.host(host).usernameAndPassword(user, password)
        );
    }

    @Bean
    @Primary
    public ElasticsearchClient elkClient1() {
        return elkClientMaker(
            "http://localhost:9200",
            elkUsername,
            elkPassword
        );
    }

    @Bean
    public ElasticsearchClient elkClient2() {
        return elkClientMaker(
            "http://localhost:9201",
            elkUsername,
            elkPassword
        );
    }
}
