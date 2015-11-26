package com.module.subsumption;

import java.util.Map;
import java.util.Set;

public class coocurrence {
	public void cooccur(Map<String,Integer[]> word_count)
	{
		Set<String> word=word_count.keySet();
		String[] words=word.toArray(new String[word.size()]);
		int[][] doc_cooccur=new int[word.size()][word.size()];
		int[] prior=new int[word.size()];
		System.out.println(word.size());
		for (int i=0;i<words.length;i++)
		{
			Integer[] arr=word_count.get(words[i]);
			prior[i]=calc_prior(arr);
			for (int j=i+1;j<words.length;j++)
			{
				int count=0;
				Integer[] brr=word_count.get(words[j]);
				for (int k=0;k<arr.length;k++)
				{
					if (arr[k]>0&&brr[k]>0)
					{
						count++;
					}
				}
				doc_cooccur[i][j]=count;
				doc_cooccur[j][i]=count;
			}
		}
	
	}
	public int calc_prior(Integer[] arr)
	{
		int sum=0;
		for (int i=0;i<arr.length;i++)
		{
			sum+=arr[i];
		}
		return sum;
	}
}
