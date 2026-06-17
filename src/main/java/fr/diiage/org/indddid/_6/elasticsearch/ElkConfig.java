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

    @Value("${elk.1.password}")
    private String elk1Password;

    @Value("${elk.2.password}")
    private String elk2Password;

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
    public ElasticsearchClient elkClient1Build() {
        return elkClientMaker(
            "http://localhost:9200",
            elkUsername,
            elk1Password
        );
    }

    @Bean
    public ElasticsearchClient elkClient2Run() {
        return elkClientMaker(
            "http://localhost:9201",
            elkUsername,
            elk2Password
        );
    }
}
