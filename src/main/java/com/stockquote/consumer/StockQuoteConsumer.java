package com.stockquote.consumer;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stockquote.cassandra.repository.StockQuoteRepository;
import com.stockquote.common.Utility;
import com.stockquote.dto.StockQuote;
import com.stockquote.entities.StockQuoteEntity;

//@Lazy
@Component
@State(Scope.Benchmark)
public class StockQuoteConsumer {

	private AtomicInteger count = new AtomicInteger();

	@Autowired
	private StockQuoteRepository stockQuoteRepo;

	@KafkaListener(id = "${kafka.consumer.id}", 
			topicPartitions = @TopicPartition(topic = "${kafka.consumer.topic}", 
			partitionOffsets = {
			   @PartitionOffset(partition = "${kafka.consumer.topic.partition.1}", initialOffset = "${kafka.consumer.topic.offset}"),
			   @PartitionOffset(partition = "${kafka.consumer.topic.partition.2}", initialOffset = "${kafka.consumer.topic.offset}"),
			   @PartitionOffset(partition = "${kafka.consumer.topic.partition.3}", initialOffset = "${kafka.consumer.topic.offset}")}))
	public void listen(final StockQuote stockQuote,final Acknowledgment acknowledgment) {
		try {

			String jsonStockQuote = Utility.ObjecttoJson(stockQuote);
			System.out.println(
					Thread.currentThread().getName() + " " + count.getAndIncrement() + " Json Stock Quote : " + jsonStockQuote);
			StockQuoteEntity stockQuoteEntity = Utility.JsontoEntity(jsonStockQuote);

			stockQuoteEntity.setId(UUID.randomUUID());

			System.out.println(stockQuoteEntity);

			stockQuoteRepo.insert(stockQuoteEntity);
			
			acknowledgment.acknowledge();
			
		} catch (JsonProcessingException jsonexe) {
			System.err.println("Error Parsing the Object [" + jsonexe.getMessage() + "]");
		} catch (Exception exe) {
			System.err.println("Unknown Error !!! " + exe.getMessage());
		}
	}
}
