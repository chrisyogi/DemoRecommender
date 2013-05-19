<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>

<title>推荐</title>

<link href="templatemo_style.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript">

</script>
</head>
<body>
<div id="templatemo_container">

    <div id="templatemo_header">	
    </div>&nbsp; <!-- end of header -->
    
    <div id="templatemo_banner">
    	<div id="templatemo_banner_content">
        	<div id="templatemo_banner_title">学者论文与好友推荐</div>
        	         <br/><br/>
			<a href="#">您好，<s:property value="name" /> 老师</a>
        </div>        
    </div> <!-- end of banner -->
    
    <div id="templatemo_menu">
        <ul>
             
        </ul>    	
    </div> <!-- end of menu -->
   
    
    <div id="templatemo_content">
         <s:form method="post" id="evaluateForm" action="evaluate">
     
         
        <input name="recommendNum" id="recommendNum" value="<s:property value="recommendNum" />" type="hidden" />
        <input name="personId" id="personId" value="<s:property value="user.id" />" type="hidden"/>
    	
    	<div id="column_w610_outter">
        	
            <div id="column_w610_inner">
              
               
                <div class="header_01">和 <s:property value="name" /> 有相似兴趣的人</div>
               
                 <s:iterator id="people" value="userList"  status="stat">
                 
                  <div class="news_box">
                  
                        <div class="news_title">
                           <div style="float:left;width:70px">
                              
                            <s:if test="#people.pictureUrl!=null">
                              <img   height="50px" width="50px"
										src="http://www.scholat.com/<s:property value="pictureUrl" />" />
				            </s:if>
				            <s:else>
				               <img src="images/1.png" width="50px" height="50px"/>
				            </s:else>
                           </div>  
                          
                            <div style="float:left;width:240px">
                              <p> <a href="http://scholat.com/~<s:property value="username" />">   <s:property value="chineseName" />  </a>(点击进入学者网个人主页） <br/>
                                  研究领域：<s:property value="scholarField" />
                            </p></div>
                                      <br/>
			                
			                 <div>
                               <s:radio name="peopleradio[%{#stat.index}]" list="ratingList" listKey="key" listValue="value" theme="simple" />
                             </div>
                             
                                 
                        </div>

                    </div>
                   
                 
                </s:iterator>

            </div>
           
             <div class="cleaner"></div>
        </div>
        
        <div id="column_w280_outter">
        	
            <div id="column_w280_inner">
            	
                <div class="header_01">推荐给 <s:property value="name" /> 的论文</div>
                   <s:iterator id="papers" value="paperList"  status="stat">
                 
                    <div class="news_box">
                  
                        <div class="news_title"><a href="#"> <s:property value="title"/> </a></div> 
                        
                                   作者：  <s:property value="authorstr" /> &nbsp;&nbsp;<br/>
                                   关键字: <s:property value="keywords" /> <br/>
                        
                          <div>
                            <s:radio name="radio[%{#stat.index}]" list="ratingList" listKey="key" listValue="value" theme="simple" />	
                          </div>
                        
                  </div>
                  
                </s:iterator>
                 
               </div>
                 <div> <input type="submit" value="提交" />  </div>
            </div>
             
            
           </s:form> 
           <div class="cleaner"></div>  
       </div>
       
     
   </div>
    <!-- end of content -->
     
 
 
</body>
</html>