<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
  
    <title>推荐学者和论文 </title>
    
  </head>
  
  <body>
     <s:form method="post" id="evaluateForm" action="evaluate" >
     <table>
        <s:iterator id="people" value="peopleList"  status="stat">
           <tr>
              <td> <s:property value="people" />  </td>
              
           </tr>
        </s:iterator>
     </table>
     
     <table>   
        <s:iterator id="papers" value="paperList"  status="stat">
            <tr>
               <td> <s:property value="title"/> </td>
               <td> <s:property value="authorstr" /> </td>
               <td> <s:property value="year" /> </td>
               
            </tr>
        </s:iterator> 
     </table>
     </s:form>
  </body>
</html>
