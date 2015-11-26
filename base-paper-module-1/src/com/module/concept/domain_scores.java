package com.module.concept;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class domain_scores {
	domain_pertinence dp=new domain_pertinence();
	domain_consensus dc=new domain_consensus();
	HashMap<String,Double> domain_s=new HashMap<String,Double>();
	HashMap<String,Integer[]> document_occur=new HashMap<String,Integer[]>();
	Stanford_parsing sp=new Stanford_parsing();
	public void calculate() throws IOException
	{
		Set<String> terms=dc.get_terms();
		HashMap<String,Double> domain_pert=dp.get_domain_pert();
		HashMap<String,Double> domain_cons=dc.get_domain_consensus();
		HashMap<String,Integer[]> word_count=sp.get_count();
		System.out.println(domain_pert);
		for (String t : terms)
		{
			double score1=0,score2=0;
			if (domain_pert.containsKey(t))
			{
				score1=domain_pert.get(t);
			}
			if (domain_cons.containsKey(t))
			{
				score2=domain_cons.get(t);
			}
			double score=0.4*score1+0.6*score2;
			domain_s.put(t, score);
		}
		HashMap<String, Double> sortedMap=sortHashMapByValuesD(domain_s);
		int size=(int) (0.4*sortedMap.size());
		int l=0;
		for (String s : sortedMap.keySet())
		{
			if (l>size) break;
			l++;
			System.out.println(s+"  "+sortedMap.get(s));
			document_occur.put(s, word_count.get(s));
		}
//		Gson gson=new Gson();
//		String json=gson.toJson(document_occur);
//		File file = new File("/home/vignesh/workspace1/document_count.txt");
//		FileWriter fw = new FileWriter(file.getAbsoluteFile());
//		BufferedWriter bw = new BufferedWriter(fw);
//		bw.write(json);
//		bw.close();
		try (Writer writer = new FileWriter("/home/vignesh/workspace1/Output.json")) {
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(document_occur, writer);
		}
	}
	public LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
		List mapKeys = new ArrayList(passedMap.keySet());
		List mapValues = new ArrayList(passedMap.values());
		Comparator cmp = Collections.reverseOrder();
		Collections.sort(mapValues,cmp);
		Collections.sort(mapKeys,cmp);

		LinkedHashMap sortedMap = new LinkedHashMap();

		Iterator valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)){
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((String)key, (Double)val);
					break;
				}

			}

		}
		return sortedMap;
	}
}
