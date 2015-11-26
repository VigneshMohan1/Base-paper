package com.module.concept;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


public class domain_consensus {
	tokenization t=new tokenization();
	lexical_cohesion l=new lexical_cohesion();
	Stanford_parsing sp=new Stanford_parsing();
	private static HashMap<String,Double> domain_cons=new HashMap<String,Double>();
	private static Set<String> domain_consensus_final=new HashSet<String>();
	private static HashMap<String,Double> sortedMap;
	public void calculate()
	{
		Set<String> unigrams=t.get_terms();
		Set<String> multigrams=l.get_compound_term();
		HashMap<String,Integer[]> term_count=sp.get_count();
		for (String s : term_count.keySet())
		{
			Integer[] arr=term_count.get(s);
			System.out.print(s+" ");
			for (int i=0;i<arr.length;i++)
			{
				System.out.print(arr[i]+" ");
			}
			System.out.println();
		}
		for (String s : unigrams)
		{
			if (term_count.containsKey(s))
			{
				Integer[] arr=term_count.get(s);
				double max=Collections.max(Arrays.asList(arr));
				double sum=0;
				for (int i=0;i<arr.length;i++)
				{
					double t=arr[i]/max;
					if (t!=0)
					{
						sum-=(t*Math.log(t));
					}
				}
				domain_cons.put(s, sum);
				//System.out.println(s+" "+sum);
			}
		}
		for (String s : multigrams)
		{
			if (term_count.containsKey(s))
			{
				Integer[] arr=term_count.get(s);
				double max=Collections.max(Arrays.asList(arr));
				double sum=0;
				for (int i=0;i<arr.length;i++)
				{
					double t=arr[i]/max;
					if (t!=0)
					{
						sum-=(t*Math.log(t));
					}
				}
				domain_cons.put(s, sum);
				//System.out.println(s+" "+sum);
			}
		}
		sortedMap=sortHashMapByValuesD(domain_cons);
		System.out.println(sortedMap);
		int size=(int) (0.2*sortedMap.size()),i=0;
		for (String s : sortedMap.keySet())
		{
			if (i>size) break;
			domain_consensus_final.add(s);
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
	public Set<String> get_terms()
	{
		return domain_consensus_final;
	}
	public HashMap<String,Double> get_domain_consensus()
	{
		return sortedMap;
	}

}
