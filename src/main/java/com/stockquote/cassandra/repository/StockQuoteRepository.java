package com.stockquote.cassandra.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.datastax.driver.mapping.annotations.Param;
import com.stockquote.entities.StockQuoteEntity;

@Repository
public interface StockQuoteRepository extends CassandraRepository<StockQuoteEntity, UUID> {
	@Query("insert into stockexchange.stockquote JSON ':quote'")
	public void insertStockQuoteJson(@Param("quote")String stockQuote);
}
