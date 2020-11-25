package com.bob.crypto.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bob.crypto.Application;
import com.bob.crypto.entity.CyrptoCoin;


public class FileService {
	
	Logger log = LoggerFactory.getLogger(FileService.class);
    
	public HashMap<String, Integer> readFile(BufferedReader br) throws IOException {
		String line = null;
		line = br.readLine();

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		while (line != null) {

			String[] coins = line.split("=");

			CyrptoCoin cc = new CyrptoCoin();
			cc.setCoinType(coins[0]);
			cc.setCount(Integer.parseInt(coins[1]));

			map.put(cc.getCoinType(), cc.getCount());

			line = br.readLine();

		}
		br.close();
		return map;
	}

	public void showCryptoPortfolio(HashMap<String, Integer> map) {

		Double total = 0.0;

		RestConsumeService rs = new RestConsumeService();
		Iterator<Map.Entry<String, Integer>> itr = map.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, Integer> entry = itr.next();
			String type = entry.getKey();
			Double totalCoinValue = (rs.getPrice(type) * entry.getValue());
			log.info(type + ": " + totalCoinValue + " EUR");
			total = total + totalCoinValue;
		}

		log.info("Total value: " + total + " EUR");

	}

}
