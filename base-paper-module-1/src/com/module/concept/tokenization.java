package com.module.concept;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class tokenization {
	private static Set<String> unigram=new HashSet<String>();
	private static Set<String> multigram=new HashSet<String>();
	domain_pertinence dp=new domain_pertinence();
	Stanford_parsing sp=new Stanford_parsing();
	public void tokenize()
	{
		HashMap<String,Double> allTerms=dp.get_domain_pert();
		for (String s : allTerms.keySet())
		{
			if (!s.matches("^"))
					if (s.matches(".*[ ].*"))
					{
						multigram.add(s);
					}
					else
					{
						unigram.add(s);
					}
		}
	}
	public Set<String> get_terms()
	{
		return unigram;
	}
	public Set<String> get_compound_term()
	{
		return multigram;
	}
}
