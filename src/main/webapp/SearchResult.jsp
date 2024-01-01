<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>GoogleSearch</title>
<style type="text/css">

body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #545454;
    }
        .search-box {
      margin: 30px auto;
      display: flex;
      border: 1px solid #ccc;
      border-radius: 20px;
      overflow: hidden;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
        .search-button {
      background-color: #A36D29;
      color: #fff;
      border: none;
      padding: 10px 20px;
      cursor: pointer;
      font-size: 16px;
      font-family: "Arial Black", sans-serif;
    }


    




</style>
</head>
<body>
<form action='${requestUri}' method="get">
	<div style='position: absolute;margin-top:190px;margin-left:50px'>
		<%
		String[][] orderList = (String[][]) request.getAttribute("query");
		for (int i = 0; i < orderList.length; i++) {
			String s=orderList[i][1];
			
		%>
		
		<a href='<%=s%>' style='color: #fff'><%=orderList[i][0]%> </a> <br>連結<br>
		<br>
		<%
}
%>
	</div>

<div>
    <img src="images/logo.png"
    style='position: absolute; width: 150px; height: 100px; left: 50%; top: 50%; margin-top: -280px; margin-left: -590px' alt="Google Logo">
    
</div>
    <div>
        <input type="text" name="keyword"
        style='font-size: 120%; position: absolute; left: 50%; top: 48%; margin-top: -250px; margin-left: -400px; width: 800px; height: 25px'
        placeholder="Search..." value='<%=request.getParameter("keyword")%>'>
      </div>
</form>


</body>
</html>