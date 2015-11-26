package com.module.concept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class lexical_cohesion {
	tokenization t=new tokenization();
	Stanford_parsing sp=new Stanford_parsing();
	private static Set<String> compoundterms=new HashSet<String>();
	LinkedHashMap<String, Double> lex_cohesion=new LinkedHashMap<String,Double>();
	public void calculate()
	{
		Set<String> multiterms=t.get_compound_term();
		HashMap<String,Integer> term_count=sp.get_comp_count();
		for (String s : multiterms)
		{
			StringTokenizer token=new StringTokenizer(s, " \t\n\r\f,.:;?![]'?");
			double num=term_count.get(s);
			double result=0;
			double denom=0;
			if (num!=0)
			{	
				int c=0;
				while (token.hasMoreTokens())
				{
					String label=token.nextToken();
					if (term_count.containsKey(label))
					{
						denom+=term_count.get(label);
					}
					c++;
				}
				num=num*Math.log(num)*c;
				if (denom!=0)
				{
					result=num/denom;
				}
			}
			lex_cohesion.put(s, result);
		}
		LinkedHashMap<String, Double> hashmap=sortHashMapByValuesD(lex_cohesion);
		int size=(int) (0.2*hashmap.size());
		int i=0;
		for (String s : hashmap.keySet())
		{
			if (i>size)
			{
				break;
			}
			compoundterms.add(s);
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
	public Set<String> get_compound_term()
	{
		return compoundterms;
	}
}