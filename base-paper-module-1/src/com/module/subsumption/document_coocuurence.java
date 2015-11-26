package com.module.subsumption;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class document_coocuurence {
	public static void main(String[] args) throws IOException {
		extract_count e=new extract_count();
		coocurrence c=new coocurrence();
		File file=new File("/home/vignesh/workspace1/Output.json");
		Map<String,Integer[]> word_count=e.extract_word_count(file);
		c.cooccur(word_count);
	}
}