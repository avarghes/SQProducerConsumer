package com.stockquote.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SuppressWarnings("deprecation")
@Configuration
@PropertySource(value = "classpath:cassandradb.properties")
@EnableCassandraRepositories(basePackages={"com.stockquote.cassandra.repository"})
public class CassandraConfig extends AbstractCassandraConfiguration {
	
	@Value("${cassandra.keyspace}")
	private String keyspace;
	
	@Value("${cassandra.server.host}")
	private String host;
	
	@Value("${cassandra.server.port}")
	private String port;
	
	@Value("${cassandra.baseentity.package}")
	private String basePackage;
	
	
	
	@Override
	protected String getKeyspaceName() {
		return keyspace;
	}
	
	@Override
	protected boolean getMetricsEnabled() {
		return false;
	}
	
	@Bean
	public CassandraClusterFactoryBean cluster(){
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(host);
		cluster.setPort(Integer.valueOf(port));
		cluster.setJmxReportingEnabled(false);
		return cluster;
		
	}
	
	@Bean
	public CassandraMappingContext cassandraMapping(){
		CassandraMappingContext mappingContext = new CassandraMappingContext();
		return mappingContext;
	}
	
	@Override
	public String[] getEntityBasePackages() {
	    return new String[]{basePackage};
	}

	@Override
	protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
	    return CassandraEntityClassScanner.scan(getEntityBasePackages());
	}

}
