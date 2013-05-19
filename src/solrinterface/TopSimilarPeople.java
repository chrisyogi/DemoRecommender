package solrinterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrServerException;
//忽悠数据：蒋运承0.4036339927347503  潘炎0.276193664591732  
//郭欢0.030052261245382413 肖菁0.00817020246906775 王驹0.266182061338617 陈国良0.024337074407735297
//刘冬宁0.29586677877491274 冀高峰0.40242453805302913 叶小平0.09269380432478158
public class TopSimilarPeople {

	public static void main(String[] args)
	{
		TopSimilarPeople t=new TopSimilarPeople();
		//这个数字代表要找出前n个相似用户
		List<Map.Entry<String,Double>>  mappingList=t.TopSimilarity("汤庸", 10);
		for(int i=0; i<mappingList.size(); i++)
		{
			System.out.println(mappingList.get(i).getKey()+" "+mappingList.get(i).getValue());
		}
		
		List<String> author=new ArrayList<String>();
		for(int i=0; i<mappingList.size(); i++)
		{
			author.add(mappingList.get(i).getKey());
		}
		List<Paper> paperlist=t.getSimilarytyAuthorPaper(author);
		System.out.println(paperlist.size());
		for(int i=0; i<paperlist.size(); i++)
		{
			String title=paperlist.get(i).getTitle();
			String coAuthor=paperlist.get(i).getAuthorstr();
			String year=paperlist.get(i).getYear();
			System.out.println(title+"　"+coAuthor+" "+year);
		}
	}
	
	public List<Map.Entry<String, Double>> TopSimilarity(String authorName1, int k)
	{
		List<Map.Entry<String,Double>>  mappingList = null;
		Map<String,Double>  map=new HashMap<String,Double>();
		try  
		{ 
//		    File authorNameFile = new File("D:\\cc.txt");
//		    BufferedReader br = new BufferedReader(new FileReader(authorNameFile));
			
			String filePath = "solrinterface/cc.txt";
			ClassLoader classLoader = TopSimilarPeople.class.getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		    //test
//			System.out.println("read file" + br.readLine());
       
		    String authorName;
		    List<String> authorNameList = new ArrayList<String>();
		    scholatSolr solr=new scholatSolr();
		    int authorId=1;
		    while((authorName = br.readLine())!= null)
		    {
			   authorNameList.add(authorId+" "+authorName);
			   authorId++;
		    }
		    
		    for(int i=0; i<authorNameList.size(); i++)
		    {
		    	
		    		String authorInfo2=authorNameList.get(i);
		    		String []author2=authorInfo2.split(" ");
		    		String authorName2=author2[1];
		    		if(!authorName1.equals(authorName2))
		    		{
		    			map.put(authorName2, solr.Similarity(authorName1,authorName2));
		    		}
		    		
		    		
		    }
		    mappingList=new ArrayList<Map.Entry<String, Double>>(map.entrySet());
			 Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>()
					 {
				        public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2)
				        {
				           return mapping2.getValue().compareTo(mapping1.getValue());
				        }
				     } );	 
		    
		    
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		List<Map.Entry<String,Double>>  mappingList2=new ArrayList<Map.Entry<String,Double>>();
		for(int i=0; i<k;i++)
		{
			mappingList2.add(mappingList.get(i));
		}
		
		return mappingList2;
	}
	
	public List<Paper> getSimilarytyAuthorPaper(List<String> author) 
	{
			
		scholatSolr solr=new scholatSolr();
		List<Paper> paperlist=new ArrayList<Paper>();
		try 
		{
		    for(int i=0; i<author.size(); i++)
		    {
		    	//第一个数字代表针对每个相似用户要找出几篇论文，第二个数字代表这些论文必须是前n年内的
				paperlist.addAll(solr.queryTopByYear(author.get(i),40,5));
		    }
		    
		    
			 Collections.sort(paperlist, new Comparator<Paper>()
					 {
				        public int compare(Paper paper1,Paper paper2)
				        {
				           return paper2.getYear().compareTo(paper1.getYear());
				        }
				     } );
		   
		} 
		catch (SolrServerException e) 
		{
			e.printStackTrace();
		}
		 return paperlist;
	}
 
	
	
	
	
	
	

}

