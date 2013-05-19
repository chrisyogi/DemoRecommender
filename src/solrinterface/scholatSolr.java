package solrinterface;



import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class scholatSolr {
	
	
	private static final long serialVersionUID = 1L;
	private  CommonsHttpSolrServer server;
	private List<Paper> paperlist;
	

	public static void main(String[] args) {
		scholatSolr s=new scholatSolr();
		System.out.println(s.getCoAuthors("蒋运承"));
		
	}
	
	public scholatSolr() {
		
		if (server == null) {
			String url = "http://202.116.46.218:8080/solr";
			//String url = "http://127.0.0.1:8080/solr";
			try {
				server = new CommonsHttpSolrServer(url);
				server.setSoTimeout(Integer.MAX_VALUE); // socket read timeout
				server.setConnectionTimeout(100);
				server.setFollowRedirects(false); // defaults to false
				server.setAllowCompression(true);
				server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
				server.setParser(new XMLResponseParser()); // binary parser is
															// used by default
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public Map<String,Double> getAuthorKeyWordKeyValue(String authorName)
	{
		Map<String,Double> mapper=new HashMap<String,Double> ();
		List<String> coAuthorNames=new ArrayList<String>();
		//result按照降序的顺序存储和authorName合作过的所有<作者名字,合作次数>
		//coAuthorNames存储了所有和authorName合作过的作者名字
		List<Map.Entry<String, Integer>> result=getCoAuthors(authorName,coAuthorNames);
		List<Map.Entry<String, Double>> statkeyword;
		try 
		{
			List<Paper> paperlist=queryTop(authorName, Integer.MAX_VALUE);
			statkeyword=getAndStatAllKeyWords(paperlist);
		    if(paperlist.size()<=5)
		    for(int i=0; i<statkeyword.size(); i++)
		    {
		    	double num_author_kewords=statkeyword.get(i).getValue();
		    	int num_author=paperlist.size();
		    	//long num_keywords=queryHitkeywords(statkeyword.get(i).getKey());
		    	if( (int)(num_author_kewords)!=0 && num_author!=0 )
		    	{
		    		double newvalue=num_author_kewords/num_author;
		    		statkeyword.get(i).setValue(newvalue);
		    	}
		    	else
		    	{
		    		statkeyword.get(i).setValue(new Double(0));
		    	}
		    }
//		    System.out.println(statkeyword.size());
//		    for(int i=0; i<statkeyword.size();i++)
//		    {
//		    	System.out.println(statkeyword.get(i).getKey()+" "+statkeyword.get(i).getValue());
//		    }
//		    
//		    System.out.println();
		    for(int i=0; i<statkeyword.size();i++)
		    {
		    	mapper.put(statkeyword.get(i).getKey(),statkeyword.get(i).getValue());
		    }
		} 
		catch (SolrServerException e) 
		{
			
			e.printStackTrace();
		}
		
		return mapper;
	}
     
	public Double Similarity(String author1,String author2)
	{
		
		Map<String,Double>  mapperauthor1=getAuthorKeyWordKeyValue(author1);
        Map<String,Double>  mapperauthor2=getAuthorKeyWordKeyValue(author2);
        
        Double sum=0.0;//向量一和向量二的内积
        Double sum1=0.0;//向量一的模
        Double sum2=0.0;//向量二的模 
        for(String key: mapperauthor1.keySet())
        {
        	Double v1=mapperauthor1.get(key);
        	Double v2=mapperauthor2.get(key);
        	if(v2!=null)
        	{
        		sum+=(v1*v2);
        	}
        	sum1+=v1*v1;
        }
        
        for(String key:mapperauthor2.keySet())
        {
        	Double v2=mapperauthor2.get(key);
        	sum2+=(v2*v2);
        }
        
//        System.out.println(sum);
//        System.out.println(sum1);
//        System.out.println(sum2);
        if(sum1==0 || sum2==0) return 0.0;
        Double similarity=sum/( Math.sqrt(sum1)*Math.sqrt(sum2) );
         return similarity;

	}
	
	
	protected  List<Map.Entry<String, Integer>>  getCoAuthors(String query)
	{
		
		
		long num=0; 
	    String query_exact = "author_exact:"+query+" AND organization:计算机";
		num = queryHit(query);
		List<Map.Entry<String, Integer>> mappinglist=new ArrayList<Map.Entry<String, Integer>>();
		if(num==0)
		{
			return mappinglist;
		}
		List<Paper> documentList = new ArrayList<Paper>();
		List<String> documentList1=new ArrayList<String>();
		try 
		{
			
			 documentList=queryTop(query_exact, (int)num);
//			 documentList1=format2(documentList);
			 documentList1=format3(documentList,query);
			 mappinglist=stat2(documentList1);



		} 
		catch (SolrServerException e) 
		{
			
		}

		 return mappinglist;
		 
		
	}
	
	protected  List<Map.Entry<String, Double>>  getAndStatAllKeyWords(List<Paper> documentList)
	{
		
		List<Map.Entry<String, Double>> mappinglist=new ArrayList<Map.Entry<String, Double>>();
		List<String> documentList1=new ArrayList<String>();
		//			 documentList1=format2(documentList);
					 documentList1=format3keywords(documentList);
					 mappinglist=stat2keywords(documentList1);

		 return mappinglist;
		 
		
	}
	
	
	protected  List<Map.Entry<String, Integer>>  getCoAuthors(String query,List<String> coAuthorNames)
	{
		
		
		long num=0; 
	    String query_exact = "author_exact:"+query+" AND organization:计算机";
		num = queryHit(query);
		List<Map.Entry<String, Integer>> mappinglist=new ArrayList<Map.Entry<String, Integer>>();
		if(num==0)
		{
			return mappinglist;
		}
		List<Paper> documentList = new ArrayList<Paper>();
		List<String> documentList1=new ArrayList<String>();
		try 
		{
			
			 documentList=queryTop(query_exact, (int)num);
//			 documentList1=format2(documentList);
			 documentList1=format3(documentList,query);
			 mappinglist=stat2(documentList1);



		} 
		catch (SolrServerException e) 
		{
			
		}
		for(int i=0;i<mappinglist.size(); i++)
		{
			coAuthorNames.add(mappinglist.get(i).getKey());
		}
		

		 return mappinglist;
		 
		
	}
	protected long queryHit(String q){
		long resultNum = 0;
		SolrQuery query = new SolrQuery();
		query.setQuery("author_exact:"+q+" AND organization:计算机");
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			resultNum = docs.getNumFound();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
			
		return resultNum;
	}
	
	protected long queryHitkeywords(String q){
		long resultNum = 0;
		SolrQuery query = new SolrQuery();
		query.setQuery("keyword:"+q+" AND organization:计算机");
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			resultNum = docs.getNumFound();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
			
		return resultNum;
	}

	protected List<Paper> queryTop(String q,int resultNum) throws SolrServerException{
		List<Paper> list = new ArrayList<Paper>();
		StringBuilder document = new StringBuilder();
		SolrQuery query = new SolrQuery();
		query.setQuery("author_exact:"+q+" AND organization:计算机");
		query.setRows(resultNum);
		QueryResponse rsp = server.query(query);
		Iterator<SolrDocument> iter = rsp.getResults().iterator();
		int count =0;
		while(iter.hasNext())
		{
			SolrDocument resultDoc = iter.next();
			if(document.length()>0) document.delete(0, document.length());
			String authors="";
			String id="";
			String title="";
			String keywords="";
			String year="";
			try
			{
				 id = (String)resultDoc.getFieldValue("id");
				 title = (String)resultDoc.getFieldValue("title");
				 authors=(String)resultDoc.getFieldValue("author_exact").toString();
				 year=(String)resultDoc.getFieldValue("year");
				 
				 if(resultDoc.getFieldValue("keyword")==null)
				 {
					 keywords="";
				 }
				 else
				 {
					 keywords=(String)resultDoc.getFieldValue("keyword").toString();
					 keywords=formatKeyWords(keywords);
				 }
				 authors=format1(authors);
			}
			catch(Exception e)
			{
				
			}
			if(authors.split(" ").length>1)//先去掉一个作者的情况
			{
				if( !id.equals("")&& !title.equals("")&& !authors.equals("") && !keywords.equals("")
						&& !keywords.contains(":") && !keywords.contains("("))
				{
					list.add(new Paper(id,authors,title,keywords,year));
					count++;
				}
				
			}
			
		}
		this.paperlist=list;
		return list;
	}
	
//	protected List<Paper> queryTopByYear(String q,int resultNum) throws SolrServerException{
//		List<Paper> list = new ArrayList<Paper>();
//		StringBuilder document = new StringBuilder();
//		SolrQuery query = new SolrQuery();
//	//	query.setQuery("author_exact:"+q+" AND organization:计算机"+" AND year:( 2011 OR 2010 OR 2009)");
//		query.setQuery("author_exact:"+q+" AND organization:计算机");
//		query.setRows(resultNum);
//		QueryResponse rsp = server.query(query);
//		Iterator<SolrDocument> iter = rsp.getResults().iterator();
//		int count =0;
//		while(iter.hasNext())
//		{
//			SolrDocument resultDoc = iter.next();
//			if(document.length()>0) document.delete(0, document.length());
//			String authors="";
//			String id="";
//			String title="";
//			String keywords="";
//			String year="";
//			try
//			{
//				 id = (String)resultDoc.getFieldValue("id");
//				 title = (String)resultDoc.getFieldValue("title");
//				 authors=(String)resultDoc.getFieldValue("author_exact").toString();
//				 year=(String)resultDoc.getFieldValue("year");
//				 if(resultDoc.getFieldValue("keyword")==null)
//				 {
//					 keywords="";
//				 }
//				 else
//				 {
//					 keywords=(String)resultDoc.getFieldValue("keyword").toString();
//					 keywords=formatKeyWords(keywords);
//				 }
//				 authors=format1(authors);
//			}
//			catch(Exception e)
//			{
//				
//			}
//			if(authors.split(" ").length>1)//先去掉一个作者的情况
//			{
//				if( !id.equals("")&& !title.equals("")&& !authors.equals("") && !keywords.equals("")
//						&& !keywords.contains(":") && !keywords.contains("("))
//				{
//					list.add(new Paper(id,authors,title,keywords,year));
//					count++;
//				}
//				
//			}
//			
//		}
//		this.paperlist=list;
//		return list;
//	}
	
	//q:要查询的人名 resultNum:要返回的结果 n:最近几年
	protected List<Paper> queryTopByYear(String q,int resultNum,int n) throws SolrServerException{
		
		Calendar cal = Calendar.getInstance();
		int year_now = cal.get(Calendar.YEAR);//当前是几几年
		List<Paper> list = new ArrayList<Paper>();
		StringBuilder document = new StringBuilder();
		SolrQuery query = new SolrQuery();
	//	query.setQuery("author_exact:"+q+" AND organization:计算机"+" AND year:( 2011 OR 2010 OR 2009)");
		query.setQuery("author_exact:"+q+" AND organization:计算机");
		query.setRows(resultNum);
		QueryResponse rsp = server.query(query);
		Iterator<SolrDocument> iter = rsp.getResults().iterator();
		int count =0;
		while(iter.hasNext())
		{
			SolrDocument resultDoc = iter.next();
			if(document.length()>0) document.delete(0, document.length());
			String authors="";
			String id="";
			String title="";
			String keywords="";
			String year="";
			try
			{
				 id = (String)resultDoc.getFieldValue("id");
				 title = (String)resultDoc.getFieldValue("title");
				 authors=(String)resultDoc.getFieldValue("author_exact").toString();
				 year=(String)resultDoc.getFieldValue("year");
				 if(resultDoc.getFieldValue("keyword")==null)
				 {
					 keywords="";
				 }
				 else
				 {
					 keywords=(String)resultDoc.getFieldValue("keyword").toString();
					 keywords=formatKeyWords(keywords);
				 }
				 authors=format1(authors);
			}
			catch(Exception e)
			{
				
			}
			
			int yearValue=0;
			if(year!=null && !year.equals(""))
			{
				yearValue=Integer.parseInt(year);
				if((year_now-yearValue)<n)
				{
					if(authors.split(" ").length>1)//先去掉一个作者的情况
					{
						if( !id.equals("")&& !title.equals("")&& !authors.equals("") && !keywords.equals("")
								&& !keywords.contains(":") && !keywords.contains("("))
						{
							list.add(new Paper(id,authors,title,keywords,year));
							count++;
						}
						
					}
				}
			}
			
			
			
		}
		this.paperlist=list;
		return list;
	}
 	
  protected String formatKeyWords(String s) 
  {
	  if(s.charAt(0)=='[')
		{
			s=s.substring(1, s.length()-1);
		}
	  if(s.contains("，"))
	  {
		  s=s.replace("，", " ");
	  }
	  if(s.contains(", "))
	  {
		  s=s.replace(",", "");
	  }
	  if(s.contains(","))
	  {
		  s=s.replace(",", " ");
	  }
	  if(s.contains("["))
	  {
		  s=s.replace("[", "");
	  }
	  if(s.contains("]"))
	  {
		  s=s.replace("]", "");
	  }
		return s ;
  }

//输入：例如 汤庸，蒋运承，Ytang，Jiang-YunCheng
//输出：汤庸 蒋运承
	protected  static String format1(String s)
	{
	
		
		if(  (s.charAt(1)>='a'&&s.charAt(1)<='z')||   (s.charAt(1)>='A'&&s.charAt(1)<='Z') ) 
		{
			return "";
		}
		//
		if(s.charAt(0)=='[')
		{
			s=s.substring(1, s.length()-1);
		}
		int t=-1;
		int length=s.length();
		for(int i=0; i<s.length(); i++)
		{
			char c=s.charAt(i);
			if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z')))   
			{
				t=i;
				break;
			}
		}
		if(t!=-1)
		{
			t-=2;
		}
		else
		{
			t=length;
		}
		
		String result=s.substring(0, t);
		if(result.contains(", "))
		{
			result=result.replace(",", "");
		}
		else
		{
			result=result.replace(",", " ");
		}
		if(result.contains("，"))
		{
			result=result.replace("，", " ");
		}
		return result;
	}

//输入：所以有关于作者A的论文信息的一个list
//输出：去除了论文信息中作者的名字的拼音和英文部分
//功能：调用format1对所以的这些搜索返回结果进行格式化
	protected  List<String> format2(List<Paper> listStr)
	{
		List<String> ls=new ArrayList<String>();
		for(int i=0; i<listStr.size(); i++)
		{
			String temp=listStr.get(i).getAuthorstr();
			if( !temp.equals("") )
			{
			      temp=format1(temp);
			      if(!temp.equals(""))
			      {
			    	  listStr.remove(i);
				      ls.add(i, temp);
			      }
			      else
			      {
			    	  listStr.remove(i);
			    	  i--;
			      }
			      
			}
			else
			{
				  listStr.remove(i);
				  i--;
			}
		}
		
		return ls;
		
		
	}
	
	
//输入: 例如 { (汤庸 蒋运承 李建国),(汤庸 刘海 赵淦森),(汤庸 刘海 赵淦森 李建国)}
//输出：{蒋运承，李建国，李建国，赵淦森，赵淦森，刘海，刘海}
	protected  List<String> format3(List<Paper> authorlist,String str)
	{
	    List<String> result=new ArrayList<String>();
	    String[] aStr=null;
	    String temp=null;
	    for(int i=0; i<authorlist.size(); i++)
	    {
	    	temp=authorlist.get(i).getAuthorstr();
	    	aStr=temp.split(" ");
	    	for(int j=0; j<aStr.length; j++)
	    	{
	    		if(!aStr[j].equals(str))
	    		{
	    			result.add(aStr[j]);
	    		}
	    	}
	    }
	    
	    return result;
	}
	
	protected  List<String> format3keywords(List<Paper> authorlist)
	{
	    List<String> result=new ArrayList<String>();
	    String[] aStr=null;
	    String temp=null;
	    for(int i=0; i<authorlist.size(); i++)
	    {
	    	temp=authorlist.get(i).getKeywords();
	    	aStr=temp.split(" ");
	    	for(int j=0; j<aStr.length; j++)
	    	{
	    		
	    			result.add(aStr[j]);
	    		
	    	}
	    }
	    
	    return result;
	}
	
//对于搜索结果文章的所有作者，以 作者与作者之间用空格分隔
//并且以二元对形式出现，即在format3的基础上转换为作者两两关系
		public static List<String> format4(List<String> authors)
		{
			//格式转换，将作者的合作关系都转换为二元的
	        int  l=authors.size();
	        String[] authorsPaper=null;
	        for(int i=0; i<l; i++)
	        {
	            authorsPaper=authors.get(i).split(" ");
	            if(authorsPaper.length>2)
	            {
	            	//将多于两名的作者拆分成作者对，加入链表中，并把原来的多于两名作者的项目删除
	            	for(int j=0; j<authorsPaper.length; j++)
	            	{
	            		for(int s=j+1; s<authorsPaper.length; s++)
	            		{
	            			authors.add(authorsPaper[j]+" "+authorsPaper[s]);
	            		}
	            	}
	            } 
	        }
	        //遍历数组，由于多于两个关系的已经转换为二元关系，所以将其删除。
	        for(int i=0; i<authors.size(); i++)
	        {
	        	authorsPaper=authors.get(i).split(" ");
	        	if(authorsPaper.length>2)
	        	{
	        		authors.remove(i);
	        		i--; 
	        	}	
	        	if(authorsPaper.length==1)
	        	{
	        		authors.remove(i);
	        		authors.add(i,authorsPaper[0]+" "+".");
	        	}
	        }
	        
	        return authors;     
		}
		
//输入：A B C D
//输出：{(A B),(A C),(A D),(B C),(B D),(C D)}
				public  List<String> format5(String authors)
				{
					//格式转换，将作者的合作关系都转换为二元的
					    List<String> twoCoAuthor=new ArrayList<String>();
			            String authorsPaper[]=null;
			            if(authors.contains("  "))
			            {
			            	authorsPaper=authors.split("  ");
			            }
			            else
			            {
			            	authorsPaper=authors.split(" ");
			            }
			            if(authorsPaper.length==1)
			            {
			            	twoCoAuthor.add(authorsPaper[0]+" "+".");
			            }
			            if(authorsPaper.length>=2)
			            {
			            	//将多于两名的作者拆分成作者对，加入链表中，并把原来的多于两名作者的项目删除
			            	for(int j=0; j<authorsPaper.length; j++)
			            	{
			            		for(int s=j+1; s<authorsPaper.length; s++)
			            		{
			            			twoCoAuthor.add(authorsPaper[j]+" "+authorsPaper[s]);
			            		}
			            	}
			            } 
			        return twoCoAuthor;     
				}
//输入:paperList
//输出：跟这个作者合作过的所有作者的名称，不重复
				protected List<String> getCoAuthorsName(String sameName)
				{
					List<Paper> paperList;
					List<String> coAuthor=new ArrayList<String>();
					try 
					{
						paperList = queryTop(sameName,Integer.MAX_VALUE);
						coAuthor=new ArrayList<String>();
						for(int i=0; i<paperList.size(); i++)
						{
							String []authors=paperList.get(i).getAuthorstr().split(" ");
							for(int j=0; j<authors.length; j++)
							{
								if(!authors[j].equals(sameName) && !coAuthor.contains(authors[j]))
								{
									coAuthor.add(authors[j]);
								}
							}
						}
						
					} catch (SolrServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return coAuthor;
					
				}
				

//输入：例如 {蒋运承，李建国，李建国，赵淦森，赵淦森，刘海，刘海}
//输出：{(蒋运承 1),(李建国 2),(赵淦森 2),(刘海 2)}
	protected  List<Map.Entry<String,Integer>> stat2(List<String> authorList)
	{
		 List<Map.Entry<String,Integer>> mappingList = null;
		 Map<String, Integer> map = new HashMap<String, Integer>();
		//这个循环实现统计每个与该作者合作过的作者的次数，放在一个Map中
		 for   (String   ss   :   authorList)   
		 { 
			 if   (map.containsKey(ss))   
			 { 
				 map.put(ss, map.get(ss)+ 1); 
				 
			 }  
			 else   
			 { 
				 map.put(ss, 1); 
			 }
		 } 
		//实现对HashMap里面的数据安装key值降序排列，并存放在一个list里面list里面的每个项目是<Map.Entry<String,Integer>>
		 mappingList=new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		 Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>()
				 {
			        public int compare(Map.Entry<String,Integer> mapping1,Map.Entry<String,Integer> mapping2)
			        {
			           return mapping2.getValue().compareTo(mapping1.getValue());
			        }
			     } );	 
		return mappingList;
	}
	
	
	protected  List<Map.Entry<String,Double>> stat2keywords(List<String> authorList)
	{
		 List<Map.Entry<String,Double>> mappingList = null;
		 Map<String, Double> map = new HashMap<String, Double>();
		//这个循环实现统计每个与该作者合作过的作者的次数，放在一个Map中
		 for   (String   ss   :   authorList)   
		 { 
			 if   (map.containsKey(ss))   
			 { 
				 map.put(ss, map.get(ss)+ 1); 
			 }  
			 else   
			 { 
				 map.put(ss, (double)1); 
			 }
		 } 
		//实现对HashMap里面的数据安装key值降序排列，并存放在一个list里面list里面的每个项目是<Map.Entry<String,Integer>>
		 mappingList=new ArrayList<Map.Entry<String, Double>>(map.entrySet());
		 Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>()
				 {
			        public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2)
			        {
			           return mapping2.getValue().compareTo(mapping1.getValue());
			        }
			     } );	 
		return mappingList;
	}
	

	public List<Paper> getPaperlist() {
		return paperlist;
	}

	public void setPaperlist(List<Paper> paperlist) {
		this.paperlist = paperlist;
	}
	

	
	

}

