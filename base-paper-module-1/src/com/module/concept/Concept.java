package com.module.concept;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tika.exception.TikaException;

public class Concept {
	public static void main(String[] args) throws FileNotFoundException, IOException, TikaException {
		long startTime = System.currentTimeMillis();
		domain_scores ds=new domain_scores();
		domain_consensus d=new domain_consensus();
		domain_pertinence dp=new domain_pertinence();
		lexical_cohesion l=new lexical_cohesion();
		tokenization t=new tokenization();
		extract_files f=new extract_files();
		f.parseFile("/home/vignesh/doc");
		List<String> data_set=f.get_dataset();
//		ArrayList<ArrayList<String>> sent=f.get_sentlist();
//		Stanford_parsing s = new Stanford_parsing(data_set.size());
//		s.parser(sent);		
//		extract_files f1=new extract_files();
//		f1.parseFile("/home/vignesh/workspace1/alternate");
//    	ArrayList<String> alter=(ArrayList<String>) f1.get_dataset();
//    	dp.check_freq(alter);
//    	t.tokenize();
//    	l.calculate();
//		d.calculate();
//		ds.calculate();
	}
}
