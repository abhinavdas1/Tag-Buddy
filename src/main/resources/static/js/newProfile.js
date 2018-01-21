var retArr=[];
$( document ).ready(function()
{
  	var access_token=localStorage.getItem('access_token');
	console.log(access_token);
	var theUrl="https://api.instagram.com/v1/users/self/media/recent/?access_token="+access_token;
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() { 
	  if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
	  {
	      var resp=xmlHttp.responseText;
	      var parsed=JSON.parse(resp);
	      var dpLink=parsed.data[0].user.profile_picture;
	      var fullName=parsed.data[0].user.full_name;
	      var userId=parsed.data[0].user.id;
	      for(i=0;i<parsed.data.length;i++)
	            parsed.data[i].tags.forEach(appendTags);
	      $(".userpicimg").attr("src",dpLink);
	      $(".username").html(fullName);
	      console.log(retArr);
		  httpGetAsync(retArr, userId);
	  }
	}
	xmlHttp.open("GET", theUrl, true); // true for asynchronous 
	xmlHttp.send(null);
});
function appendTags(item)
{
  retArr.push(item);
}
function httpGetAsync(tagArr, userId)
{
  var theUrl="http://10.136.214.106:8080/api/v1/user/add";
  var xmlHttp = new XMLHttpRequest();
  xmlHttp.onreadystatechange = function() { 
      if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
      {
       	 var resp=xmlHttp.responseText;
       	 var parsed=JSON.parse(resp);
       	 console.log(parsed);
       	 for (var i in parsed.matchCounts)
       	 {
       	 	var e={'id':i,'tag':parsed.matchCounts[i]};
       	 	console.log(e);
       	 }
      }
  }
  xmlHttp.open("POST", theUrl, true); // true for asynchronous 
  xmlHttp.send("userId=das&tags=four");
}