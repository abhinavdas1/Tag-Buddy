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
  var theUrl="/api/v1/user/add";
  var xmlHttp = new XMLHttpRequest();
  xmlHttp.onreadystatechange = function() { 
      if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
      {
          resp=xmlHttp.responseText;
          parsed=JSON.parse(resp);
          userId=parsed.data[0].user.id;
          userName=parsed.data[0].user.full_name;
          for(i=0;i<parsed.data.length;i++)
            parsed.data[i].tags.forEach(appendTags);
          
      }
  }
  xmlHttp.open("POST", theUrl, true); // true for asynchronous 
  xmlHttp.send("userId="+userId+"&tags="+tagArr);
}
