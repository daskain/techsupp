package ru.rfibank.techsupport.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//конфиг для подключения к elk
@Configuration
public class ELKConfig {

	@Value("${elasticsearch.host}")
    private String elkHost;

    @Value("${elasticsearch.port}")
    private int elkPort;

    @Value("${elasticsearch.protocol}")
    private String elkProtocol;
    
    private RestHighLevelClient restHighLevelClient;
    
	@Bean(destroyMethod = "close")
	public RestHighLevelClient createInstance() throws Exception {
		
		try {
			restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(elkHost, elkPort, elkProtocol)));
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return restHighLevelClient;
		
	}
}
