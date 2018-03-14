package org.coin.crawler.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.coin.crawler.config.properties.DruidProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by shawnkent on 2018/3/14.
 */
@Configuration
@EnableTransactionManagement
public class DruidConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public DataSource isspuDataSource() throws Exception {
        return buildDataSource(url, username, password, driverClassName, connectionProperties);
    }


    /**
     * 使用事务管理
     *
     * @param isspuDataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager isspuManager(DataSource isspuDataSource) {
        return new DataSourceTransactionManager(isspuDataSource);
    }

    /**
     * 引入mybatis配置
     * 创建SqlSessionFactory
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true);
        configuration.setCacheEnabled(false);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 引入分页配置
     *
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("pageSizeZero", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("helperDialect", "mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    /**
     * 创建SqlSessionTemplate
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String s) {
                try {
                    return new Date(Long.parseLong(s));
                } catch (Exception e) {
                }
                return null;
            }
        };
    }

    /**
     * 创建druid数据源
     *
     * @param url
     * @param username
     * @param password
     * @param driverClassName
     * @param connectionProperties
     * @return
     */

    private DataSource buildDataSource(String url, String username, String password, String driverClassName, String
            connectionProperties) {
        DruidDataSource dataDataSource = new DruidDataSource();
        dataDataSource.setUrl(url);
        dataDataSource.setUsername(username);
        dataDataSource.setPassword(password);
        dataDataSource.setDriverClassName(driverClassName);

        //configuration
        dataDataSource.setInitialSize(druidProperties.getInitialSize());
        dataDataSource.setMinIdle(druidProperties.getMinIdle());
        dataDataSource.setMaxActive(druidProperties.getMaxActive());
        dataDataSource.setMaxWait(druidProperties.getMaxWait());
        dataDataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        dataDataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        dataDataSource.setValidationQuery(druidProperties.getValidationQuery());
        dataDataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
        dataDataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        dataDataSource.setTestOnReturn(druidProperties.isTestOnReturn());
        dataDataSource.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements());
        dataDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties
                .getMaxPoolPreparedStatementPerConnectionSize());

        try {
            dataDataSource.setFilters(druidProperties.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataDataSource.setConnectionProperties(connectionProperties);
        return dataDataSource;
    }
}
