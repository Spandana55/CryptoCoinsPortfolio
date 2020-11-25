package com.bob.crypto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bob.crypto.service.FileService;

public class Application {

	public static void main(String[] args) throws IOException {

		Scanner sc = null;
		FileReader fr = null;
		BufferedReader br = null;
		Logger log = LoggerFactory.getLogger(Application.class);
		try {

			BasicConfigurator.configure();
			sc = new Scanner(System.in);
			log.info("Enter Crypto Coins File Path: ");
			fr = new FileReader(sc.nextLine());
			br = new BufferedReader(fr);

			FileService fs = new FileService();
			HashMap<String, Integer> map = fs.readFile(br);
			fs.showCryptoPortfolio(map);

		} catch (Exception e) {

			e.printStackTrace();
			log.error("Exception occured: {}", e);

		} finally {
			br.close();
			fr.close();
			sc.close();
		}

	}
}
