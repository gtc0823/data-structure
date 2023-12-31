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
    
.button{
	position:absolute;
	width:45px;
	height:25px;
	font-size:15px;
	left:50%;
	top:50%;
}




.border-style {
	border-radius:75px/90px;
}
#padding{
	padding: 0px 0px 0px 15px;
}
.note{
	position:absolute;
	width:50px;
	height:50px;
	animation-timing-function: ease-in-out;
	animation-direction: alternate;
	animation-name:note;
	animation-duration:5s;
	animation-iteration-count:infinite;
	
}
@keyframes note{
	0%{
		
		left:640px;		
		top:0px;
		
	}
	25%{
		left:700px;
		top:60px;
		
	}
	50%{
		left:760px;
		top:0px;
	}
	75%{
		left:700px;
		top:-60px;
	}
	100%{
		left:640px;
		top:0px;
	}
}
.note1{
	position:absolute;
	width:50px;
	height:50px;
	animation-direction: alternate;
	animation-timing-function: ease-in-out;
	animation-name:note1;
	animation-duration:5s;
	animation-iteration-count:infinite;
}
@keyframes note1{
	0%{
		left:760px;		
		top:0px;
		
	}
	25%{
		left:700px;
		top:-60px;
	}
	50%{
		left:640px;
		top:0px;
	}
	75%{
		left:700px;
		top:60px;
	}
	100%{
		left:760px;
		top:0px;
		
	}
}
.note2{
	position:absolute;
	width:50px;
	height:50px;
	animation-direction: alternate;
	animation-timing-function: ease-in-out;
	animation-name:note2;
	animation-duration:5s;
	animation-iteration-count:infinite;
}
@keyframes note2{
	0%{
		left:700px;		
		top:-60px;		
	}
	25%{
		left:640px;
		top:0px;
	}
	50%{
		left:700px;
		top:60px;
	}
	75%{
		left:760px;
		top:0px;
	}
	100%{
		left:700px;
		top:-60px;
		
	}
}
.note3{
	position:absolute;
	width:50px;
	height:50px;
	animation-direction: alternate;
	animation-timing-function: ease-in-out;
	animation-name:note3;
	animation-duration:5s;
	animation-iteration-count:infinite;
}
@keyframes note3{
	0%{
		left:700px;		
		top:60px;		
	}
	25%{
		left:760px;
		top:0px;
	}
	50%{
		left:700px;
		top:-60px;
	}
	75%{
		left:640px;
		top:0px;
	}
	100%{
		left:700px;
		top:60px;
		
	}
}
.box{
  position:relative;
}
.box:before{
  content:'';
  position:absolute;
  z-index:2;
  top:60px;
  left:50px;
  width:50px;
  height:50px;
  
  border-radius:2px;
  transform: rotate(45deg);
  -webkit-animation:box 1.25s infinite ; 
}
@-webkit-keyframes box{
  0%{
    top:50px;
  }
  20%{
    border-radius:2px;  
  }
  50%{
    top:80px; 
    border-bottom-right-radius:25px;
  }
  80%{
    border-radius:2px; 
  }
  100%{
    top:50px;
  }
}
.box:after{
  content:'';
  position:absolute;
  z-index:1;
  top:128px;
  left:52px;
  width:44px;
  height:3px;
  background:#eaeaea;
  border-radius:100%;
  -webkit-animation:shadow 1.25s infinite ; 
}
@-webkit-keyframes shadow{
  0%,100%{
    left:54px;
    width:40px;
    background:#eaeaea;
  }
  50%{
    top:126px;
    left:50px;   
    width:50px;
    height:7px;
    background:#eee;
  }
}
</style>
<script type="text/javascript">
function click10() {
	document.getElementsByName("searchNum")[0].value = 10;
	document.getElementsByName("searchNum")[0].style.color = '#0489B1';
}
function click20() {
	document.getElementsByName("searchNum")[0].value = 20;
	document.getElementsByName("searchNum")[0].style.color = '#0489B1';
}
function click40() {
	document.getElementsByName("searchNum")[0].value = 40;
	document.getElementsByName("searchNum")[0].style.color = '#0489B1';
}
function click80() {
	document.getElementsByName("searchNum")[0].value = 80;
	document.getElementsByName("searchNum")[0].style.color = '#0489B1';
}
</script>
</head>
<body>
<form action='${requestUri}' method='get'>



<div class="container">
	<div class='note'>
	<img src="images/musical-note.png" style = 'position:absolute;width:40px;height:40px;margin-top:520px;margin-left:320px' >
	</div> 
	<div class='note1'>
	<img src="images/musical-note.png" style = 'position:absolute;width:40px;height:40px;margin-top:520px;margin-left:320px' >
	</div> 
	<div class='note2'>
	<img src="images/musical-note-2.png" style = 'position:absolute;width:40px;height:40px;margin-top:520px;margin-left:320px' >
	</div>
	<div class='note3'>
	<img src="images/musical-note-2.png" style = 'position:absolute;width:40px;height:40px;margin-top:520px;margin-left:320px' >
	</div>  
    <img src="images/logo.png" width="300" height="150" alt="Google Logo">
    
      <div class="search-box">
        <input type="text" name="keyword" class="search-input" placeholder="Search...">
        <button type="submit" class="search-button">Search</button>
      </div>
    
    <div>
	<button type='button' class='button' name='button20' onclick='click10()' style='border-radius:10px;cursor:pointer;margin-left:-100px;margin-top:50px'>10</button>
	</div>
	<div>
	<button type='button' class='button' name='button40' onclick='click20()' style='border-radius:10px;cursor:pointer;margin-left:-47.5px;margin-top:50px'>20</button>
	</div>
	<div>
	<button type='button' class='button' name='button60' onclick='click40()' style='border-radius:10px;cursor:pointer;margin-left:2.5px;margin-top:50px'>40</button>
	</div>
	<div>
	<button type='button' class='button' name='button80' onclick='click80()' style='border-radius:10px;cursor:pointer;margin-left:52.5px;margin-top:50px'>80</button>
	</div>
	<div>
<input type='text' name='searchNum' value='10' style='color:#545454;border-style:none;background-color:#545454'>
</div>
</div>
  


<div>
<a href ='http://localhost:8080/Final_Project/TestProject'></a>
</div>
</form>
</body>
</html>