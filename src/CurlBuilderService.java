package ru.rfibank.techsupport.service;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CurlBuilderService {
	
	public String buildCurlRequest(LinkedHashMap<String,String> allParams, String host) {
		
		StringBuffer curlRequest = new StringBuffer();
		
		String params = allParams.entrySet().stream()
				.map(s -> s.getKey() + "=" + s.getValue())
				.collect(Collectors.joining("&"));
		
		curlRequest.append("curl -H \"Content-Type: application/x-www-form-urlencoded\" -d \"");
		curlRequest.append(params);
		curlRequest.append("\" -X POST ");
		curlRequest.append(host);
		
		return curlRequest.toString();
	}

}
