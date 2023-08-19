# WebCrawler
Web Crawler to crawl websites
This Application will be to crawl websites with configuration provided.

mongod.exe --dbpath D:\mongodb_server_data\data

docker compose up -d
docker ps
docker logs -f broker
docker compose down


Elastic Search, Kibana and Logstash

1. Download Elastic Search 8.9 higher and run elasticsearch.bat
Local Disable Security
# Enable security features
xpack.security.enabled: false

xpack.security.enrollment.enabled: false

# Enable encryption for HTTP API client connections, such as Kibana, Logstash, and Agents
xpack.security.http.ssl:
enabled: false

# Enable encryption and mutual authentication between cluster nodes
xpack.security.transport.ssl:
enabled: false

2. http://locahost:9200
3. Download Kibana
4. Configure kibana.yml file in config folder
5. UnComment ElasticSearch
6. Execute kibana.bat
7. http://localhost:5601/
8. Download Logstash
9. Create a file logstash.conf in config folder
10. 
input {
file {
path => "D:/logs/elk-stack.log"
start_position => "beginning"
}
}

output {

	elasticsearch {
		hosts => "localhost:9200"
	}
	
	stdout {
		codec => rubydebug
	}
}

# MicroService Configuration
# Received log over TCP
input {
tcp {
port => 5000
codec => json
}
}

#index => "microservice-logs-inv"
output {
elasticsearch {
hosts => ["localhost:9200"]
index => "micro-%{appName}"
}

	stdout {
		codec => json_lines
	}
}

12. ./logstash -f ./config/logstash.conf
13. http://localhost:9600/






