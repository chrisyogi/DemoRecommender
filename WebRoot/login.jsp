<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
    
    <title>推荐用户登录</title>
    

  </head>
  
  <body>
  <center>
    <div>
       <h2>学者论文与好友推荐</h2>
       
       <s:form method="post" id="loginForm" action="login">
          <table> 
               <tr>
                  <td>学者姓名:</td>
                  <td><input type="text" name="name" /></td>
               </tr>
               <tr>
                 <td> 请选择推荐的论文数目: </td>
                 
                 <td> 
                   <select name="recommendNum" id="recommendNum">
                      <option value="25">25</option>
                      <option value="20">20</option>
                      <option value="15">15</option>
                      <option value="10">10</option>
                   </select>
                 </td>
               </tr>
              <tr>
                  <td><input type="submit" value="提交" /></td>
              </tr>
           </table>
       </s:form>
    </div>
   </center>
  </body>
</html>
