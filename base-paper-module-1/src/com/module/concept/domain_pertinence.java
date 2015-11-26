package com.module.concept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class domain_pertinence {
	private static HashMap<String,Integer> data_s;
	private static HashMap<String,Double> domain_pert=new HashMap<String,Double>();
	private static HashMap<String,Double> domain_pert_final=new HashMap<String,Double>();
	private static HashMap<String, Integer> alter_c=new HashMap<String,Integer>();
	private static Set<String> allTerms;
	public void check_freq(ArrayList<String> data_set)
	{
		String[] alter_data_s=tokenizer(data_set);
		data_s=new Stanford_parsing().get_comp_count();
		allTerms=new Stanford_parsing().get_unique_terms();
		int w=5;
		for (int i=0;i<alter_data_s.length;i++)
		{
			String temp="";
			for (int j=i;j<w+i&&w+i<alter_data_s.length;j++)
			{
				temp=temp+" "+alter_data_s[i];
				temp=temp.trim();
				if (data_s.containsKey(temp))
				{
					if (!alter_c.containsKey(temp))
					{
						alter_c.put(temp, 1);
					}
					else
					{
						alter_c.put(temp, alter_c.get(temp)+1);
					}
				}
			}
		}
		calculate_score();
	}
	public void calculate_score()
	{
		for (String s : allTerms)
		{
			double di=data_s.get(s);
			double dj=0;
			if (alter_c.containsKey(s))
			{
				dj=alter_c.get(s);
			}
			double score=1;
			if (dj!=0)
			{
				score=di/dj;
			}
			else
			{
				score=di;
			}
			
				domain_pert.put(s, score);
		}
		HashMap<String, Double> hashmap=sortHashMapByValuesD(domain_pert);
		int size=(int) (0.3*hashmap.size());
		int i=0;
		for (String s : hashmap.keySet())
		{
			if (i>size)
			{
				break;
			}
			domain_pert_final.put(s, hashmap.get(s));
			i++;
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
	public String[] tokenizer(ArrayList<String> data_set)
	{
		ArrayList<String> tok=new ArrayList<String>();
		for (String d : data_set)
		{
			StringTokenizer token=new StringTokenizer(d, " \t\n\r\f,.:;?![]'?");
			while (token.hasMoreTokens())
			{
				tok.add(token.nextToken());
			}
		}
		String[] tokens=tok.toArray(new String[tok.size()]);
		return tokens;
	}
	public HashMap<String,Double> get_domain_pert()
	{
		return domain_pert_final;
	}
}
