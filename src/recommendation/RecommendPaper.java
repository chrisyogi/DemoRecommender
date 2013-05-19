package recommendation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recommendation.model.Paper;
import solrinterface.TopSimilarPeople;

public class RecommendPaper {
	
	private TopSimilarPeople tsp = new TopSimilarPeople();
	
	private Map<String,Double> similarPeople = new HashMap<String, Double>();
	
	private Map<Paper,Double> similarPaper = new HashMap<Paper, Double>();
	
	public List<Paper> recommend(String author,int n){
		
		List<Map.Entry<String,Double>>  mappingList=tsp.TopSimilarity(author, n);
		List<Paper> paperList = new ArrayList<Paper>();
		
		for(int i=0; i<mappingList.size(); i++)
		{
			similarPeople.put(mappingList.get(i).getKey(), mappingList.get(i).getValue()); 	
		}
		for(String name : similarPeople.keySet()){
			
		}
		
	    return paperList;
	}

}
