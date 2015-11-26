package com.module.concept;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;
public class temp_parsing {
	private static String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";        
	public final LexicalizedParser par = LexicalizedParser.loadModel(PCG_MODEL);
	List<String> dt=Arrays.asList("a","an","the","these","it","this","that","those","we","i","those","there","he","she","they");
	public final TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");

	private static Map<String,Integer[]> unigram=new HashMap<String,Integer[]>();
	private static Map<String,Integer[]> bigram1=new HashMap<String,Integer[]>();
	private static Map<String,Integer> bigram=new HashMap<String,Integer>();
	public static Map<String,Integer[]> allTerms=new HashMap<String,Integer[]>();
	File file = new File("/home/vignesh/workspace1/parsetree.txt");
	BufferedWriter bw;
	public static int count;
	public temp_parsing(int count) throws IOException {
		// TODO Auto-generated constructor stub
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		this.count=count;
		
	}
	public temp_parsing() {
		// TODO Auto-generated constructor stub
	}
	public Tree parse(String str) throws IOException {     
		List<CoreLabel> tokens = tokenize(str);
		par.setOptionFlags(new String[]{"-maxLength", "80", "-retainTmpSubcategories"});
		Tree tree = par.apply(tokens);
		return tree;
	}
	private List<CoreLabel> tokenize(String str) {
		Tokenizer<CoreLabel> tokenizer =
				tokenizerFactory.getTokenizer(
						new StringReader(str));    
		return tokenizer.tokenize();
	}
	public void parser(ArrayList<ArrayList<String>> sent) throws IOException
	{
		for (List<String> d : sent)
		{		
			for (String s : d)
			{
				Tree parsed_sent=parse(s);
				if (!parsed_sent.label().value().toString().equals("X"))
				{
					//bw.write(parsed_sent.toString());
					extract_noun_phrases(parsed_sent);
				}
			}
		}
		System.out.println(unigram.keySet());
	}
	public void extract_noun_phrases(Tree tree)
	{
		Integer[] val=new Integer[this.count];
		for (int i=0;i<val.length;i++)
		{
			val[i]=0;
		}
		System.out.println(tree.toString());

		Set<String> str=new HashSet<String>();
		for (Tree sub : tree)
		{
			String temp=" ";
			if (sub.label().value().equals("NP"))
			{
				for (Tree s : sub)
				{
					if (s.label().value().equals("NP"))
					{
						temp=s.getLeaves().toString();
					}
				}
				str.add(temp);
			}
		}
		for (String s : str)
		{
			System.out.println(s);
		}
	}

	public HashMap<String,Integer> get_bigram()
	{
		return (HashMap<String, Integer>) bigram;
	}
	public HashMap<String,Integer[]> get_unigram()
	{
		return (HashMap<String, Integer[]>) unigram;
	}
}





































