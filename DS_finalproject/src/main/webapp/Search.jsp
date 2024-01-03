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
      background-image: url(images/TV.jpg);
      background-repeat: no-repeat;
      background-position: -250px 0px;
      background-size: 2000px 800px;
      background-position: center top;
    }
    .container {
      max-width: 800px;
      height: 300px;
      margin: 150px auto;
      background-color: #545454;
      padding: 20px;
      /*box-shadow: 0 0 10px rgba(0,0,0,0.1);*/
      border-radius: 8px;
      text-align: center;
    }
    .search-box {
      margin: 30px auto;
      display: flex;
      border: 1px solid #ccc;
      border-radius: 20px;
      overflow: hidden;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    .search-input {
      flex: 1;
      padding: 10px;
      border: none;
      outline: none;
      font-size: 16px;
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



<div class="container">
	
    <img src="images/logo.png" width="300" height="150" alt="Google Logo">
    <form action='${requestUri}' method="get">
      <div class="search-box">
        <input type="text" name="keyword" class="search-input" placeholder="Search...">
        <button type="submit" class="search-button">Search</button>
      </div>
     </form>
</div>

</body>
</html>