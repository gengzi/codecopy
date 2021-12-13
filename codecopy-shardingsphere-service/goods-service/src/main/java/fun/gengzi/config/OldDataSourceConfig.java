//package fun.gengzi.config;
//
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class OldDataSourceConfig {
//
//    @Bean(name = "oldDataSource")
//    @Qualifier("oldDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.old")
//    public DataSource masterDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//}
