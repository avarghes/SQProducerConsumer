# SQProducerConsumer

Cod works with a single topic with three partitions.


Commands to Run Zookeeper , Kafka and Cassandra

# Starts the ZooKeeper Server
zookeeper-server-start.bat %KFKCFGROOT%\zookeeper.properties

# Starts the Kafka Server
kafka-server-start.bat %KFKCFGROOT%\server.properties

# Creates Topic with 3 partitions
kafka-topics.bat --zookeeper localhost:2181 --create --replication-factor 1 --partitions 3 --topic stockexchange

# Starts the Cassandra Server
cassandra.bat -f  

# Creates keyspace & table in the cassandra database 

CREATE KEYSPACE stockexchange WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = false;

CREATE TABLE stockexchange.stockquote (
    id uuid PRIMARY KEY,
    stockclosingprice float,
    stockopenprice float,
    stockticker text,
    stocktradingday timestamp,
    stockvolume int
)


