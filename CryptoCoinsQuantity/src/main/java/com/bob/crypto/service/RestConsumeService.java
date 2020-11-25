package com.bob.crypto.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestConsumeService {

	Logger log = LoggerFactory.getLogger(RestConsumeService.class);
	
	public Double getPrice(String coinType) {
		Double price = 0.0;
		try {
			String restServiceUrl = construtUrl(coinType);
			URL url = new URL(restServiceUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Connection Failed : HTTP Error code : " + conn.getResponseCode());
			}
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			
			while ((output = br.readLine()) != null) {
				JSONParser parser = new JSONParser();
				JSONObject jobj = (JSONObject) parser.parse(output);
			    price = (Double) jobj.get("EUR");
			}

			conn.disconnect();

		} catch (Exception e) {
			log.error("Exception while consuming Rest API " + e);
		}
		
		return price;

	}

	private String construtUrl(String coinType) {

		String baseUrl = "https://min-api.cryptocompare.com/data/price";
		return baseUrl + "?fsym=" + coinType + "&tsyms=EUR";

	}

}
