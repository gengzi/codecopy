//package fun.gengzi.config;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "entityManagerFactoryOld",
//        transactionManagerRef = "transactionManagerOld",
//        basePackages = {"fun.gengzi.oldentity"}
//)
//@EnableConfigurationProperties(JpaProperties.class)
//public class OldJpaDataSourceConfig {
//
//
//    private final Map<String, Object> jpaPropertyMap = new HashMap<>();
//    @Autowired
//    @Qualifier("oldDataSource")
//    private DataSource oldDataSource;
//
//    @Bean(name = "entityManagerOld")
//    public EntityManager entityManager() {
//        return entityManagerFactoryPrimary().getObject().createEntityManager();
//    }
//
//    @Resource
//    private Properties jpaProperties;
//
//
//
//    @Bean(name = "entityManagerFactoryOld")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary() {
//
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
//        hibernateJpaVendorAdapter.setGenerateDdl(true);
//        hibernateJpaVendorAdapter.setShowSql(true);
//        factoryBean.setDataSource(oldDataSource);
//        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
//        factoryBean.setPackagesToScan("fun.gengzi.oldentity");
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
//        properties.setProperty("hibernate.current_session_context_class","org.springframework.orm.hibernate5.SpringSessionContext");
//        factoryBean.setJpaProperties(properties);
//        factoryBean.setPersistenceUnitName("oldPersistenceUnit");
//        return  factoryBean;
////        LocalContainerEntityManagerFactoryBean entityManagerFactory = builder
////                .dataSource(oldDataSource)
////                .packages("fun.gengzi.oldentity") //设置实体类所在位置
////                .persistenceUnit("oldPersistenceUnit")
////                .build();
////        entityManagerFactory.setJpaProperties(jpaProperties);
////        return entityManagerFactory;
//    }
//
//    @Bean(name = "transactionManagerOld")
//    public PlatformTransactionManager transactionManagerPrimary() {
//        return new JpaTransactionManager(entityManagerFactoryPrimary().getObject());
//    }
//}
