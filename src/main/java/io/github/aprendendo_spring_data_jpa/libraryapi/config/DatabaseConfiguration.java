package io.github.aprendendo_spring_data_jpa.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

   @Value("${spring.datasource.driver-class-name}")
    String drive;

  // @Bean
    public DataSource dataSource(){ // jeito simples de fazer
       DriverManagerDataSource ds = new DriverManagerDataSource();
       ds.setUsername(username);
       ds.setPassword(password);
       ds.setDriverClassName(drive);
       ds.setUrl(url);
       return ds;
   }

   @Bean
    public DataSource hikariDataSource (){
       HikariConfig config = new HikariConfig();
       config.setPassword(password);
       config.setDriverClassName(drive);
       config.setUsername(username);
       config.setJdbcUrl(url);

       config.setMaximumPoolSize(10); // numero maximo de conexões no pool, banco de dados
       config.setMinimumIdle(1);   // minimo de conexões no pool
       config.setPoolName("library-pool"); // nome do pool para aparecer no log
       config.setMaxLifetime(600000); // tempo maximo da aplicação em milisegundo
       config.setConnectionTimeout(1000000); // sera o tempo que tentará a conexão, se não conseguir a aplicação vai quebrar
       config.setConnectionTestQuery("select 1"); // test para ver se está funcionando o banco

       return new HikariDataSource(config);
   }
}
