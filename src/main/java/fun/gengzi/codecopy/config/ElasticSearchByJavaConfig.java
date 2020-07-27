package fun.gengzi.codecopy.config;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.UUID;

@Configuration
public class ElasticSearchByJavaConfig {


    /**
     * es 客户端
     * @return
     */
    @Bean
    public NodeClientFactoryBean client() {
        NodeClientFactoryBean bean = new NodeClientFactoryBean(true);
        bean.setClusterName(UUID.randomUUID().toString());
        bean.setEnableHttp(false);
        bean.setPathData("target/elasticsearchTestData");
        bean.setPathHome("src/test/resources/test-home-dir");
        return bean;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) {
        return new ElasticsearchTemplate(client);
    }


}
