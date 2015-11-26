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

import javax.json.Json;

import com.google.gson.Gson;
import com.sun.syndication.feed.rss.Item;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;
public class Stanford_parsing {
	private static String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";        
	public final LexicalizedParser par = LexicalizedParser.loadModel(PCG_MODEL);
	List<String> dt=Arrays.asList("a","an","the","these","it","this","that","those","we","i","those","there","he","she","they");
	public final TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");

	private static HashMap<String,Integer[]> domain_terms=new HashMap<String,Integer[]>();
	private static HashMap<String, Integer> domain_terms_comp=new HashMap<String,Integer>();
	private static Set<String> allTerms=new HashSet<String>();
	public static int count,k=0;;
	private static int j=0;
	Integer[] arr;
	public Stanford_parsing(int count) throws IOException {
		// TODO Auto-generated constructor stub
		this.count=count;
		arr=new Integer[this.count];
		for (int i=0;i<arr.length;i++)
		{
			arr[i]=0;
		}

	}
	public Stanford_parsing() {
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
			System.out.println("File no :"+k);

			for (String s : d)
			{
				s=s.trim();
				//System.out.println(s);
				Tree parsed_sent=parse(s);
				if (!parsed_sent.label().value().toString().equals("X"))
				{
					extract_noun_phrases(parsed_sent,k);
				}
			}
			k++;
		}
		Gson gson=new Gson();
		String json=gson.toJson(domain_terms_comp);
		File file=new File("/home/vignesh/workspace1/document_word_count.txt");
		FileWriter fw=new FileWriter(file.getAbsolutePath());
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(json);
		String json1=gson.toJson(domain_terms);
		File file1=new File("/home/vignesh/workspace1/word_count.txt");
		FileWriter fw1=new FileWriter(file.getAbsolutePath());
		BufferedWriter bw1=new BufferedWriter(fw);
		bw.write(json1);
		//		System.out.println(domain_terms);
		//	System.out.println(domain_terms_comp);
	}
	public void extract_noun_phrases(Tree tree,int k) throws IOException
	{
		if (tree==null&&tree.isLeaf())
		{
			return;
		}
		List<Tree> child=tree.getChildrenAsList();
		for (Tree c : child)
		{
			if (c.label().value().equals("NP"))
			{
				extract_noun(c, k);
			}
			else
			{
				extract_noun_phrases(c,k);
			}
		}
	}
	public void extract_noun(Tree tree,int k) throws IOException
	{
		List<Tree> child=tree.getChildrenAsList();
		int num=tree.numChildren();
		int co=0;
		for (Tree c : child)
		{
			if (c.label().value().equals("NP"))
			{
				extract_noun(c, k);
			}
			else if (c.numChildren()==1&&c.getChild(0).isLeaf())
			{
				co++;
			}
			else
			{
				extract_noun_phrases(c, k);
			}
		}
		if (co==num)
		{
			String temp="";
			for (Tree c : child)
			{
				if (!c.label().value().equals("DT")&&(!dt.contains(c.getLeaves().toString().replace("[","").replace("]", "").trim())))
					temp=temp+" "+c.getLeaves().toString().replace("[","").replace("]", "");
			}
			//	System.out.println(temp.trim());
			if (!temp.trim().matches(".*[1234567890%'.].*")&&(!(temp.length()<3)))
			{
//				System.out.println(temp.trim());
				insert(temp.trim(),k);
				allTerms.add(temp.trim());
			}
		}

	}
	void insert(String str,int k)
	{
		Integer[] arr1=new Integer[this.count];
		for (int i=0;i<arr1.length;i++)
		{
			arr1[i]=0;
		}
		if (!domain_terms.containsKey(str))
		{
			arr1[k]+=1;
			domain_terms.put(str, arr1);
			domain_terms_comp.put(str, 1);
		}
		else
		{
			Integer[] val=domain_terms.get(str);
			val[k]=val[k]+1;
			domain_terms.put(str, val);
			domain_terms_comp.put(str, domain_terms_comp.get(str)+1);
		}
	}
	void insert1(String str,int k)
	{

	}
	public HashMap<String, Integer[]> get_count()
	{
		return domain_terms;
	}
	public HashMap<String,Integer> get_comp_count()
	{
		return domain_terms_comp;
	}
	public Set<String> get_unique_terms()
	{
		return allTerms;
	}
}





































