const API = "http://localhost:8080";

function getToken(){
    return localStorage.getItem("token");
}

function setToken(token){
    localStorage.setItem("token",token);
}

async function login(){

const username=document.getElementById("username").value;
const password=document.getElementById("password").value;

const res=await fetch(API+"/auth/login",{
method:"POST",
headers:{
"Content-Type":"application/json"
},
body:JSON.stringify({
username,
password
})
});

const data=await res.json();

setToken(data.token);

window.location="feed.html";
}

async function register(){

const username=document.getElementById("username").value;
const email=document.getElementById("email").value;
const mobile=document.getElementById("mobile").value;
const bio=document.getElementById("bio").value;
const password=document.getElementById("password").value;

await fetch(API+"/auth/register",{
method:"POST",
headers:{
"Content-Type":"application/json"
},
body:JSON.stringify({
username,
email,
mobileNumber:mobile,
bio,
password
})
});

window.location="login.html";
}

async function loadFeed(){

const res = await fetch(API + "/users/feed?page=0&size=10",{
headers:{
Authorization:"Bearer " + getToken()
}
});

const posts = await res.json();

const feed = document.getElementById("feed");

feed.innerHTML = "";

posts.forEach(p => {

const username = p.user && p.user.username ? p.user.username : "User";

feed.innerHTML += `
<div class="post">

<h3>${username}</h3>

<p>${p.content}</p>

<img src="${p.imageUrl || ""}" width="200">

<p>Likes: ${p.likeCount} | Comments: ${p.commentCount}</p>

<button onclick="likePost(${p.postId})">Like</button>

<br><br>

<div>
<input id="comment-${p.postId}" placeholder="Write comment">
<button onclick="commentPost(${p.postId})">Comment</button>
</div>

<br>

<button onclick="toggleComments(${p.postId}, this)">
View Comments
</button>

<div id="comments-${p.postId}" style="display:none; margin-top:10px;"></div>

</div>
`;

});

}

async function likePost(postId){

await fetch(API+"/posts/"+postId+"/like",{
method:"POST",
headers:{
Authorization:"Bearer "+getToken()
}
});

loadFeed();
}
async function createPost(){

const content=document.getElementById("content").value;
const image=document.getElementById("image").files[0];

const form=new FormData();

form.append("content",content);

if(image){
form.append("image",image);
}

await fetch(API+"/posts",{
method:"POST",
headers:{
Authorization:"Bearer "+getToken()
},
body:form
});

window.location="feed.html";

}

async function commentPost(postId){

const content = document.getElementById("comment-" + postId).value;

await fetch(API + "/posts/" + postId + "/comments?content=" + encodeURIComponent(content),{
method:"POST",
headers:{
Authorization:"Bearer " + getToken()
}
});

loadFeed();

}

async function loadComments(postId){

const res = await fetch(API + "/posts/" + postId + "/comments",{
headers:{
Authorization:"Bearer " + getToken()
}
});

const comments = await res.json();

const div = document.getElementById("comments-" + postId);

div.innerHTML="";

comments.forEach(c => {

div.innerHTML += `
<p><b>${c.user.username}</b>: ${c.content}</p>
`;

});

}

async function toggleComments(postId){

const div = document.getElementById("comments-" + postId);

if(div.style.display === "none"){

div.style.display = "block";

await loadComments(postId);

}else{

div.style.display = "none";

}

}

// SEARCH LOGIC

async function searchUsers(){

const query = document.getElementById("searchUser").value;

const res = await fetch(API + "/users/search?username=" + query,{
headers:{
Authorization: "Bearer " + getToken()
}
});

const users = await res.json();

console.log("API RESPONSE:", users);   // 👈 ADD THIS

const results = document.getElementById("userResults");

results.innerHTML = "";

users.forEach(u => {

console.log("User object:", u);   // 👈 ADD THIS

const name = u.username;

results.innerHTML += `
<div>

<b>${name}</b>

<button onclick="followUser('${name}')">Follow</button>
<button onclick="unfollowUser('${name}')">Unfollow</button>

</div>
`;

});

}

//FOLLOW

async function followUser(username){

const res = await fetch(API + "/users/" + username + "/follow",{
method:"POST",
headers:{
Authorization:"Bearer " + getToken()
}
});

if(res.ok){
alert("Followed user");
}else{
const err = await res.text();
alert("Follow failed: " + err);
}

}

//UNFOLLOW

async function unfollowUser(username){

await fetch(API + "/users/" + username + "/unfollow",{
method:"POST",
headers:{
Authorization:"Bearer " + getToken()
}
});

alert("Unfollowed user");

}


