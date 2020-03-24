package com.stockquote.benchmark;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.stockquote.common.Utility;
import com.stockquote.config.CassandraConfig;
import com.stockquote.config.KafkaConsumerConfig;
import com.stockquote.config.KafkaProducerConfig;
import com.stockquote.consumer.StockQuoteConsumer;
import com.stockquote.dto.StockQuote;
import com.stockquote.listeners.StockQuoteProducerListener;
import com.stockquote.producer.StockQuoteProducer;

//@SpringBootApplication
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(jvmArgsAppend = {"-XX:+UseParallelGC", "-Xms2g", "-Xmx2g"})
public class StockQuoteProducerConsumerBenchmark {
	
	private String topicName;

	private StockQuoteProducerListener listener;
	
	private StockQuoteConsumer consumer;
	
	private StockQuoteProducer producer;
	
	protected AnnotationConfigApplicationContext context;
	
	@Setup(Level.Trial)
	public void setup(){
		this.context = new AnnotationConfigApplicationContext();
		context.register(KafkaProducerConfig.class);
		context.register(KafkaConsumerConfig.class);
		context.register(CassandraConfig.class);
		context.registerBean(StockQuoteConsumer.class);
		context.registerBean(StockQuoteProducer.class);
		
		context.refresh();
		
		this.topicName = "stockexchange";

		this.listener = context.getBean(StockQuoteProducerListener.class);
		
		this.producer = context.getBean(StockQuoteProducer.class);
		
	}
	
	@Benchmark
	public ListenableFuture<SendResult<String, StockQuote>> benchmarkProducer(){
		Random stockPriceVolumeGenerator = new Random();
		
		StockQuote quote = new StockQuote("TATASTEEL", stockPriceVolumeGenerator.nextFloat(),
				stockPriceVolumeGenerator.nextFloat(), stockPriceVolumeGenerator.nextInt(100000),
				Utility.timestamp());
		
		ListenableFuture<SendResult<String, StockQuote>> producerFuture = this.producer.sendStockQuote(quote, topicName, listener);
		
		return producerFuture;
	}
	
	@Benchmark
	public boolean benchmarkConsumer(){
		this.consumer = context.getBean(StockQuoteConsumer.class);
		return true;
	}
	
	public static void main(String [] args) throws RunnerException {
		
        Options opt = new OptionsBuilder()
            .include("\\." + StockQuoteProducerConsumerBenchmark.class.getSimpleName() + "\\.")
            .forks(0)
            .threads(1)
            .warmupIterations(0)
            .measurementIterations(2)
            .shouldDoGC(true)
            .shouldFailOnError(true)
            .resultFormat(ResultFormatType.JSON)
            .shouldFailOnError(true)
            .jvmArgs("-server")
            .build();

        new Runner(opt).run();
    }
	
	@TearDown
	public void tearDown(){
		this.context.close();
	}

}
