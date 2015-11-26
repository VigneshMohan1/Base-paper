package com.module.subsumption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class extract_count {
	Map<String,Integer[]> extract_word_count(File file) throws IOException
	{
		BufferedReader bin=new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		String content;
		StringBuffer sb=new StringBuffer();
		while ((content=bin.readLine())!=null)
		{
			sb.append(content);
		}
		String json = sb.toString();
		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, Integer[]>>(){}.getType();
		Map<String,Integer[]> word_count=gson.fromJson(json, stringStringMap);
		for (String s : word_count.keySet())
		{
			Integer[] arr=word_count.get(s);
			System.out.print(s+" ");
			for (int i=0;i<arr.length;i++)
			{
				System.out.print(arr[i]+" ");
			}
			System.out.println();
		}
		return word_count;
	}
}
