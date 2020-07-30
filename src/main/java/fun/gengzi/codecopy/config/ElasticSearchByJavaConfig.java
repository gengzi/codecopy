package fun.gengzi.codecopy.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Configuration
public class ElasticSearchByJavaConfig extends AbstractElasticsearchConfiguration {


//    /**
//     * es 客户端
//     * @return
//     */
//    @Bean
//    public NodeClientFactoryBean client() {
//        NodeClientFactoryBean bean = new NodeClientFactoryBean(true);
//        bean.setClusterName(UUID.randomUUID().toString());
//        bean.setEnableHttp(false);
//        bean.setPathData("target/elasticsearchTestData");
//        bean.setPathHome("src/test/resources/test-home-dir");
//        return bean;
//    }

//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate(Client client) {
//        return new ElasticsearchTemplate(client);
//    }


//    @Bean
//    public Client elasticsearchClient() throws UnknownHostException {
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        TransportClient client = new PreBuiltTransportClient(settings);
//        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//        return client;
//    }

//    @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
//    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
//        return new ElasticsearchTemplate(elasticsearchClient());
//    }

    /**
     * 高级客户端
     * @return
     */
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

//    @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
//    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
//        return new ElasticsearchTemplate((Client) elasticsearchClient());
//    }

}
