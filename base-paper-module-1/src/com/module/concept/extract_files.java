package com.module.concept;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class extract_files {
	private static ArrayList<String> data_set;
	private static ArrayList<ArrayList<String>> comp_sent_list;
	
	File file = new File("/home/vignesh/workspace1/parsetree.txt");
	
	public extract_files() {
		data_set=new ArrayList<String>();
		comp_sent_list=new ArrayList<ArrayList<String>>();
	}
	
	public void parseFile(String filepath) throws FileNotFoundException, IOException {

		File[] allfiles = new File(filepath).listFiles();
		BufferedReader in = null;
		int i=0;
		for (File f : allfiles) {
			
			if (f.getName().endsWith(".txt")) {
				ArrayList<String> sent_list=new ArrayList<String>();
				in = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
				StringBuilder sb = new StringBuilder();
				String s = null;
				while ((s = in.readLine()) != null) {
					s=s.replaceAll("[()]", "").replaceAll("\\[(.*?)\\]", "").replaceAll(" +"," ").replaceAll("\\(.*?\\)", "");
					sb.append(s.replaceAll("[ ]+"," ").replace(".", ". ").trim());
				}
				System.out.println(i+" "+f.getName());
				i++;
				data_set.add(sb.toString().toLowerCase());
				Reader reader = new StringReader(sb.toString());
				DocumentPreprocessor dp = new DocumentPreprocessor(reader);
				
				for (List<HasWord> sentence : dp) {
					String sent1="";
					for (HasWord s1 : sentence)
					{
						sent1=sent1+" "+s1;
					}
					sent_list.add(sent1.toLowerCase().replace(",", " ,").trim());
				}
				comp_sent_list.add(sent_list);
			}
	
		}
		System.out.println(comp_sent_list.size());
	}
	public  List<String> get_dataset()
	{
		return data_set;
	}
	public  ArrayList<ArrayList<String>> get_sentlist()
	{
		return comp_sent_list;
	}
}
