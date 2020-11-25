package com.bob.crypto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.bob.crypto.service.FileService;
import com.bob.crypto.service.RestConsumeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FileServiceTests {

	FileService fileService;
	RestConsumeService restService;

	@BeforeEach
	void setUp() throws Exception {
		FileService fs = new FileService();
		this.fileService = fs;
		
		RestConsumeService restService = new RestConsumeService();
		this.restService = restService;
	}

	@Test
	void testFile() throws IOException {

		File f = new File("/Users/Spandana/Desktop/test_crypto.txt");
		FileWriter fw = new FileWriter(f.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("BTC=10\n" + "ETH=5\n" + "XRP=2000");
		bw.close();
		fw.close();

		FileReader fr = new FileReader("/Users/Spandana/Desktop/test_crypto.txt");
		BufferedReader br = new BufferedReader(fr);
		HashMap<String, Integer> map = fileService.readFile(br);

		assertEquals(10, map.get("BTC"));
		assertEquals(5, map.get("ETH"));
		assertEquals(2000, map.get("XRP"));

		br.close();
		fr.close();
		f.delete();
	}
	
	@Test
	void testRestAPI() throws IOException {

		Double price = restService.getPrice("BTC");
		assertNotEquals(0.0, price);
		
	}
}
