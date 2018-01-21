var retArr=[];
var postsArray = [];	
var comments =[];
var likes = [];	
var counter = 0;		
var postResponse ='';
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
	      var username=parsed.data[0].user.username;
	      for(i=0;i<parsed.data.length;i++)
	      {
	      	    postsArray.push(parsed.data[i].images.standard_resolution.url);
				likes.push(parsed.data[i].likes.count);
				comments.push(parsed.data[i].comments.count);
	            parsed.data[i].tags.forEach(appendTags);
				
	      }
	      $(".userpicimg").attr("src",dpLink);
	      $(".username").html(fullName);
	      console.log(retArr);
		  httpGetAsync(retArr, username, fullName, dpLink);
		  document.getElementById("app").innerHTML= outPosts();
	  }
	}
	xmlHttp.open("GET", theUrl, true); // true for asynchronous 
	xmlHttp.send(null);
});
function appendTags(item)
{
  retArr.push(item);
}
function httpGetAsync(tagArr, username, fullName, dpLink)
{
  var theUrl="http://10.136.214.106:8080/api/v1/user/add";
  var xmlHttp = new XMLHttpRequest();
  xmlHttp.onreadystatechange = function() { 
      if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
      {
      	 var tagsString="";
       	 var resp=xmlHttp.responseText;
       	 var parsed=JSON.parse(resp);
       	 console.log(parsed);
       	 var dom="";
       	 var no=0;
       	 var ppList=parsed.userPicture;
       	 var nameList=parsed.userNames;
       	 for (var i in parsed.matchCounts)
       	 {
       	 	var e={'id':i,'tag':parsed.matchCounts[i]};
       	 	var prob=parsed.matchCounts[i];
       	 	var dp=ppList[i];
       	 	var name=nameList[i];
       	 	var tags=parsed.matchedTags[i];
       	 	if(tags.length >= 5)
	       	 	tagLen=5;
       	 	else
       	 		tagLen=tags.length;
       	 	tagsString="";
       	 	for (var tag=0; tag<tagLen; tag++)
       	 		tagsString+="#"+tags[tag]+" ";
       	 	console.log(dp+name+tags);
	       	if (no==0)
	 		 {
	 		 	dom = `<div class="item active">
	            <blockquote>
	              <div class="row">
	              <table border="0" style="width:100%">
	                 <tr>
					    <th rowspan="2">
					        <div class="col-sm-3 text-center">
		                  		<a href="http://wwww.instagram.com/`+i+`">
		                    		<img class="img-circle" src="`+dp+`" style="width: 200px;height:200px;">
		                  		</a>
		                	</div>
		                </th>
					    <td>
					    	<h><b>`+name+`</b></h2>
					    </td>
					  </tr>
					  <tr>
					    <td>
					    	<div class="col-sm-9">
				                <p>Common tags between you two:</p>
				                <small><strong>`+tagsString+`</strong></small>
		               		</div>
					    </td>
					  </tr>
	              </table>
	              </div>
	            </blockquote>
	         	</div>`;
	         	dots=`<li data-target="#quote-carousel" data-slide-to="`+no+`" class="active"></li>`;
	 		 }
	 		 else
	 		 {
	 		 	dom = `<div class="item">
	            <blockquote>
	              <div class="row">
	              <table border="0" style="width:100%">
	                 <tr>
					    <th rowspan="2">
					        <div class="col-sm-3 text-center">
		                  		<a href="http://wwww.instagram.com/`+i+`">
		                    		<img class="img-circle" src="`+dp+`" style="width: 200px;height:200px;">
		                  		</a>
		                	</div>
		                </th>
					    <td>
					    	<h2><b>`+name+`</b></h2>
					    </td>
					  </tr>
					  <tr>
					    <td>
					    	<div class="col-sm-9">
				                <p>Common tags between you two:</p>
				                <small><strong>`+tagsString+`</strong></small>
		               		</div>
					    </td>
					  </tr>
	              </table>
	              </div>
	            </blockquote>
	            </div>`;
	            dots=`<li data-target="#quote-carousel" data-slide-to="`+no+`"></li>`;
	 		 }
	         console.log(dom);
	         $(".carousel-indicators").append(dots);
	         $(".carousel-inner").append(dom);
	         $("#quote-carousel").carousel();
	         $( "#spinner" ).remove();
	         $( "#spinnerDot" ).remove();
       	 	no++;
       	 }
       	 //$(".carousel-inner").html(dom);
      }
  }
  xmlHttp.open("POST", theUrl, true); // true for asynchronous 
  xmlHttp.send("userId="+username+"&tags="+tagArr+"&name="+fullName+"&picture="+dpLink);
}
function getDom(userId, parseTags, no)
{
	var access_token=localStorage.getItem('access_token');
	var theUrl="https://api.instagram.com/v1/users/"+userId+"/?access_token="+access_token;
	var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
      if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
      {
       	 var resp=xmlHttp.responseText;
       	 var parsed=JSON.parse(resp);
       	 var pp=parsed.data.profile_picture;
       	 var tagsString="";
       	 var tagLen=0;
       	 var dom=``;
       	 var dots=``;
       	 console.log(parseTags);
       	 for (var id in parseTags.matchedTags)
       	 {
       	 	var tags=parseTags.matchedTags[id];
       	 	console.log("id:"+id+",value:"+tags);
       	 	if(tags.length >= 5)
       	 		tagLen=5;
       	 	else
       	 		tagLen=tags.length;
       	 	for (var tag=0; tag<tagLen; tag++)
       	 		tagsString+="#"+tags[tag]+" ";
       	 }
 		 console.log(tagsString);
 		 if (no==0)
 		 {
 		 	dom = `<div class="item active">
            <blockquote>
              <div class="row">
                <div class="col-sm-3 text-center">
                  <a href="http://wwww.instagram.com/`+i+`">
                    <img class="img-circle" src="`+pp+`" style="width: 200px;height:200px;">
                  </a>
                </div>
                <div class="col-sm-9">
                  <p>Common tags between you two:</p>
                  <small><strong>`+tagsString+`</strong></small>
                </div>
              </div>
            </blockquote>
         	</div>`;
         	dots=`<li data-target="#quote-carousel" data-slide-to="0" class="active"></li>`;
 		 }
 		 else
 		 {
 		 	dom = `<div class="item">
            <blockquote>
              <div class="row">
                <div class="col-sm-3 text-center">
                  <a href="http://wwww.instagram.com/`+i+`">
                    <img class="img-circle" src="`+pp+`" style="width: 200px;height:200px;">
                  </a>
                </div>
                <div class="col-sm-9">
                  <p>Common tags between you two:</p>
                  <small><strong>`+tagsString+`</strong></small>
                </div>
              </div>
            </blockquote>
            </div>`;
            dots=`<li data-target="#quote-carousel" data-slide-to="`+no+`"></li>`;
 		 }
         console.log(dom);
         $(".carousel-inner").append(dom);
         $(".carousel-indicators").append(dom);
      }
  }
  xmlHttp.open("GET", theUrl, true); // true for asynchronous 
  xmlHttp.send(null);
}

function outPosts()
{
	for(counter =0; counter<postsArray.length; counter++){
		var postsRemaining = postsArray.length - counter;
		if( postsRemaining >=3){
		postResponse += `<div class="row">
			<div class="image-size profile">
				<div class="img-box ">
			
				    <img src=" `+ postsArray[counter++]+ `" alt="" class="img-responsive" />
					<ul class="text-center">
					  <a href="#"><li><i class="fa fa-heart"></i></li>`+ likes[counter]+`</a>
					  <a href="#"><li><i class="fa fa-comments"></i></li>`+ comments[counter]+`</a>
					</ul>
				</a>
			</div>
			</div>
			<div class="image-size profile">
				<div class="img-box">
				
				    <img src="`+ postsArray[counter++]+ `" alt="" class="img-responsive" />
					<ul class="text-center">
					  <a href="#"><li><i class="fa fa-heart"></i></li>`+ likes[counter]+`</a>
					  <a href="#"><li><i class="fa fa-comments"></i></li>`+ comments[counter]+`</a>
					</ul>
				</a>
				</div>
			</div>
			<div class="image-size profile">
				<div class="img-box">
				
				    <img src="`+ postsArray[counter]+ `" alt="" class="img-responsive" />
					<ul class="text-center">
					  <a href="#"><li><i class="fa fa-heart"></i></li>`+ likes[counter]+`</a>
					  <a href="#"><li><i class="fa fa-comments"></i></li>`+ comments[counter]+`</a>
					</ul>
				</a>
				</div>
			</div>
		</div>`;
		}
	  	else if(postsRemaining ==2){
		  	postResponse += `<div class="row">
			  	<div class="image-size profile">
				<div class="img-box">
				
				    <img src="`+ postsArray[counter++]+ `" alt="" class="img-responsive" />
					<ul class="text-center">
					  <a href="#"><li><i class="fa fa-heart"></i></li>`+ likes[counter]+`</a>
					  <a href="#"><li><i class="fa fa-comments"></i></li>`+ comments[counter]+`</a>
					</ul>
				</a>
				</div>
			</div>
			  	<div class="image-size profile">
				<div class="img-box">
				
				    <img src="`+ postsArray[counter]+ `" alt="" class="img-responsive" />
					<ul class="text-center">
					  <a href="#"><li><i class="fa fa-heart"></i></li>`+ likes[counter]+`</a>
					  <a href="#"><li><i class="fa fa-comments"></i></li>`+ comments[counter]+`</a>
					</ul>
				</a>
				</div>
			</div>
			</div>`;
	  	}
	   	else if(postsRemaining ==1){
		  	postResponse += `<div class="row">
			  	<div class="image-size profile">
				<div class="img-box">
				
				    <img src="`+ postsArray[counter]+ `" alt="" class="img-responsive" />
					<ul class="text-center">
					  <a href="#"><li><i class="fa fa-heart"></i></li>`+ likes[counter]+`</a>
					  <a href="#"><li><i class="fa fa-comments"></i></li>`+ comments[counter]+`</a>
					</ul>
				</a>
				</div>
			</div>
			</div>`;
	  	}
	  	else 
	  	{
		  	return postResponse;
	  	}
	}
	return postResponse;
}