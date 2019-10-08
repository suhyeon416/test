<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/movie.jsp</title>
<style type="text/css">
   .comm{width:400px;height:100px;border:1px solid cyan;margin-top:10px;}
</style>
<script type="text/javascript">
   window.onload=function(){
	   getList();
   };
   var xhrList=null;
   function getList(){
	   xhrList=new XMLHttpRequest(); 
	   xhrList.onreadystatechange=listOk;
	   xhrList.open('get','comments?cmd=list&mnum=${vo.mnum}',true);
	   xhrList.send();
   }
   function listOk(){
	   if(xhrList.readyState==4 && xhrList.status==200){
		   removeComm();
		   var data=xhrList.responseText;
		   var list=JSON.parse(data)[0];
		   var commList=document.getElementById("commList");
		   for(var i=0;i<list.length;i++){
			   var str=list[i].id + "<br>" +
			           list[i].comments + "<br>";
			   var div=document.createElement("div");
			   div.innerHTML=str + "<a href='javascript:delComm(" + list[i].num + ")'>삭제</a>";
			   div.className="comm";
			   commList.appendChild(div);         
		   }
	   }
   }
   var xhrInsert=null;
   function insertComm(){
	   xhrInsert=new XMLHttpRequest();
	   xhrInsert.onreadystatechange=insertOk;
	   xhrInsert.open('post','comments?cmd=insert',true);
	   //post로 요청시 아래와 같이 Content-type을 설정해야 함
	   xhrInsert.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	   var id=document.getElementById("id").value;
	   var comments=document.getElementById("comments").value;
	   var param="mnum=${vo.mnum}&id=" + id + "&comments=" + comments;
	   //post방식인 경우 send메소드로 파라미터 전송하기
	   xhrInsert.send(param);
   }
   function insertOk(){
	   if(xhrInsert.readyState==4 && xhrInsert.status==200){
		   var data=xhrInsert.responseText;
		   var json=JSON.parse(data);
		   if(json.code=='success'){
			   document.getElementById("id").value="";
			   document.getElementById("comments").value="";
			   getList();
		   }else{
			   alert("댓글 등록 실패");
		   }
	   }
   }
   var xhrDelete=null;
   function delComm(num){
	   xhrDelete=new XMLHttpRequest();
	   xhrDelete.onreadystatechange=deleteOk;
	   xhrDelete.open('get','comments?cmd=delete&num=' + num,true);
	   xhrDelete.send();  
   }
   function deleteOk(){
	   if(xhrDelete.readyState==4 && xhrDelete.status==200){
		   var data=xhrDelete.responseText;
		   var json=JSON.parse(data);
		   if(json.code=='success'){
			   getList();
		   }else{
			   alert("댓글 삭제 실패");
		   }
	   }
   }
   function removeComm(){
	   var commList=document.getElementById("commList");
	   var children=commList.childNodes; //commList의 모든 자식노드 얻어오기
	   for(var i=children.length-1;i>=0;i--){ //삭제는 뒤에서부터 하기
		   var comm=children.item(i); //i번째 자식댓글 얻어오기
		   commList.removeChild(comm); //commList에서 댓글(1개) 지우기
	   }
   }
</script>
</head>
<body>
<div style="width:500px;height:200px;padding:10px;border:2px solid blue">
   <h1>${vo.title }</h1>
   <h1>내용:${vo.content }</h1>
   <h1>감독:${vo.director }</h1>
</div>
<div><!-- 댓글div -->
   <div id="commList"></div>
   <div id="commReg">
        아이디<br>
      <input type="text" id="id"><br>
        영화평<br>
      <textarea rows="3" cols="30" id="comments"></textarea><br>
      <input type="button" value="등록" onclick="insertComm()">
   </div>
</div>
</body>
</html>