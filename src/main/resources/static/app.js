const API = "http://localhost:8080";

/* =========================
TOKEN STORAGE
========================= */

function getToken(){
    return localStorage.getItem("token");
}

function setToken(token){
    localStorage.setItem("token",token);
}


/* =========================
LOGIN
========================= */

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


/* =========================
REGISTER
========================= */

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


/* =========================
POST CARD RENDER (UI)
========================= */

function renderPost(p){

const username = p.user && p.user.username ? p.user.username : "User";

return `

<div class="post-card">

<div class="post-header">
${username}
</div>

<div class="post-content">
${p.content}
</div>

${p.imageUrl ? `<img class="post-image" src="${p.imageUrl}">` : ""}

<div style="margin-top:10px; color:#8b98a5">
Likes: ${p.likeCount} | Comments: ${p.commentCount}
</div>

<div class="post-actions">

<button onclick="likePost(${p.postId})">
Like
</button>

<button onclick="toggleComments(${p.postId})">
View Comments
</button>

</div>

<div class="comment-input">

<input id="comment-${p.postId}" placeholder="Write comment">

<button onclick="commentPost(${p.postId})">
Comment
</button>

</div>

<div id="comments-${p.postId}" style="display:none; margin-top:10px"></div>

</div>

`;

}

/* =========================
LOAD FEED
========================= */

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

feed.innerHTML += renderPost(p);

});

}


/* =========================
LIKE POST
========================= */

async function likePost(postId){

await fetch(API+"/posts/"+postId+"/like",{
method:"POST",
headers:{
Authorization:"Bearer "+getToken()
}
});

loadFeed();

}


/* =========================
CREATE POST
========================= */

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


/* =========================
COMMENT POST
========================= */

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


/* =========================
LOAD COMMENTS
========================= */

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


/* =========================
TOGGLE COMMENTS
========================= */

async function toggleComments(postId){

const div = document.getElementById("comments-" + postId);

if(div.style.display === "none"){

div.style.display = "block";

await loadComments(postId);

}else{

div.style.display = "none";

}

}


/* =========================
SEARCH USERS
========================= */

async function searchUsers(){

const username = document.getElementById("searchUser").value

const res = await fetch(API + "/users/search?username=" + username,{
headers:{
"Authorization":"Bearer " + getToken()
}
})

const users = await res.json()

const results = document.getElementById("userResults")

results.innerHTML=""

users.forEach(user => {

results.innerHTML += `

<div class="post-card">

<b>${user.username}</b>

<br><br>

<button onclick="followUser('${user.username}')">
Follow
</button>

<a href="profile.html?username=${user.username}">
View Profile
</a>

</div>

`

})

}


/* =========================
FOLLOW USER
========================= */

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


/* =========================
UNFOLLOW USER
========================= */

async function unfollowUser(username){

await fetch(API + "/users/" + username + "/unfollow",{
method:"POST",
headers:{
Authorization:"Bearer " + getToken()
}
});

alert("Unfollowed user");

}


/* =========================
PROFILE PAGE
========================= */

async function loadProfile(){

const urlParams = new URLSearchParams(window.location.search);

let username = urlParams.get("username");

/* if profile opened without username → show logged in user */
if(!username){

const token = getToken();

if(token){
const payload = JSON.parse(atob(token.split('.')[1]));
username = payload.sub || payload.username;
}

}

const res = await fetch(API + "/users/" + username + "/profile",{
headers:{
Authorization:"Bearer " + getToken()
}
});

const data = await res.json();

const profileInfo = document.getElementById("profileInfo");

profileInfo.innerHTML = `

<div style="display:flex; align-items:center; gap:15px">

<div style="
width:60px;
height:60px;
background:#1DA1F2;
border-radius:50%;
display:flex;
align-items:center;
justify-content:center;
font-size:22px;
font-weight:bold;
">

${data.username.charAt(0).toUpperCase()}

</div>

<div>

<h3>${data.username}</h3>

<p style="color:#8b98a5">${data.bio}</p>

</div>

</div>

<div class="profile-stats">

<span>Posts ${data.postCount}</span>

<span>Followers ${data.followerCount}</span>

<span>Following ${data.followingCount}</span>

</div>

`;

const postsDiv = document.getElementById("profilePosts");

postsDiv.innerHTML="";

data.posts.forEach(post => {

postsDiv.innerHTML += `

<div class="post-card">

<div class="post-content">
${post.content}
</div>

${post.imageUrl ? `<img class="post-image" src="${post.imageUrl}">` : ""}

<div style="margin-top:10px">
Likes: ${post.likeCount} | Comments: ${post.commentCount}
</div>

</div>

`;

});

}

//const API = "http://localhost:8080";
//
//function getToken(){
//    return localStorage.getItem("token");
//}
//
//function setToken(token){
//    localStorage.setItem("token",token);
//}
//
//async function login(){
//
//const username=document.getElementById("username").value;
//const password=document.getElementById("password").value;
//
//const res=await fetch(API+"/auth/login",{
//method:"POST",
//headers:{
//"Content-Type":"application/json"
//},
//body:JSON.stringify({
//username,
//password
//})
//});
//
//const data=await res.json();
//
//setToken(data.token);
//
//window.location="feed.html";
//}
//
//async function register(){
//
//const username=document.getElementById("username").value;
//const email=document.getElementById("email").value;
//const mobile=document.getElementById("mobile").value;
//const bio=document.getElementById("bio").value;
//const password=document.getElementById("password").value;
//
//await fetch(API+"/auth/register",{
//method:"POST",
//headers:{
//"Content-Type":"application/json"
//},
//body:JSON.stringify({
//username,
//email,
//mobileNumber:mobile,
//bio,
//password
//})
//});
//
//window.location="login.html";
//}
//
//async function loadFeed(){
//
//const res = await fetch(API + "/users/feed?page=0&size=10",{
//headers:{
//Authorization:"Bearer " + getToken()
//}
//});
//
//const posts = await res.json();
//
//const feed = document.getElementById("feed");
//
//feed.innerHTML = "";
//
//posts.forEach(p => {
//
//const username = p.user && p.user.username ? p.user.username : "User";
//
//feed.innerHTML += `
//<div class="post">
//
//<h3>${username}</h3>
//
//<p>${p.content}</p>
//
//<img src="${p.imageUrl || ""}" width="200">
//
//<p>Likes: ${p.likeCount} | Comments: ${p.commentCount}</p>
//
//<button onclick="likePost(${p.postId})">Like</button>
//
//<br><br>
//
//<div>
//<input id="comment-${p.postId}" placeholder="Write comment">
//<button onclick="commentPost(${p.postId})">Comment</button>
//</div>
//
//<br>
//
//<button onclick="toggleComments(${p.postId}, this)">
//View Comments
//</button>
//
//<div id="comments-${p.postId}" style="display:none; margin-top:10px;"></div>
//
//</div>
//`;
//
//});
//
//}
//
//async function likePost(postId){
//
//await fetch(API+"/posts/"+postId+"/like",{
//method:"POST",
//headers:{
//Authorization:"Bearer "+getToken()
//}
//});
//
//loadFeed();
//}
//async function createPost(){
//
//const content=document.getElementById("content").value;
//const image=document.getElementById("image").files[0];
//
//const form=new FormData();
//
//form.append("content",content);
//
//if(image){
//form.append("image",image);
//}
//
//await fetch(API+"/posts",{
//method:"POST",
//headers:{
//Authorization:"Bearer "+getToken()
//},
//body:form
//});
//
//window.location="feed.html";
//
//}
//
//async function commentPost(postId){
//
//const content = document.getElementById("comment-" + postId).value;
//
//await fetch(API + "/posts/" + postId + "/comments?content=" + encodeURIComponent(content),{
//method:"POST",
//headers:{
//Authorization:"Bearer " + getToken()
//}
//});
//
//loadFeed();
//
//}
//
//async function loadComments(postId){
//
//const res = await fetch(API + "/posts/" + postId + "/comments",{
//headers:{
//Authorization:"Bearer " + getToken()
//}
//});
//
//const comments = await res.json();
//
//const div = document.getElementById("comments-" + postId);
//
//div.innerHTML="";
//
//comments.forEach(c => {
//
//div.innerHTML += `
//<p><b>${c.user.username}</b>: ${c.content}</p>
//`;
//
//});
//
//}
//
//async function toggleComments(postId){
//
//const div = document.getElementById("comments-" + postId);
//
//if(div.style.display === "none"){
//
//div.style.display = "block";
//
//await loadComments(postId);
//
//}else{
//
//div.style.display = "none";
//
//}
//
//}
//
//// SEARCH LOGIC
//
//async function searchUsers(){
//
//    const username = document.getElementById("searchUser").value
//
//    const res = await fetch(API + "/users/search?username=" + username,{
//        headers:{
//            "Authorization":"Bearer " + getToken()
//        }
//    })
//
//    const users = await res.json()
//
//    const results = document.getElementById("userResults")
//
//    results.innerHTML=""
//
//    users.forEach(user => {
//
//        results.innerHTML += `
//        <div>
//
//            <b>${user.username}</b>
//
//            <button onclick="follow('${user.username}')">
//                Follow
//            </button>
//
//            <a href="profile.html?username=${user.username}">
//                View Profile
//            </a>
//
//        </div>
//        `
//    })
//
//}
//
////FOLLOW
//
//async function followUser(username){
//
//const res = await fetch(API + "/users/" + username + "/follow",{
//method:"POST",
//headers:{
//Authorization:"Bearer " + getToken()
//}
//});
//
//if(res.ok){
//alert("Followed user");
//}else{
//const err = await res.text();
//alert("Follow failed: " + err);
//}
//
//}
//
////UNFOLLOW
//
//async function unfollowUser(username){
//
//await fetch(API + "/users/" + username + "/unfollow",{
//method:"POST",
//headers:{
//Authorization:"Bearer " + getToken()
//}
//});
//
//alert("Unfollowed user");
//
//}
//
//// Profile Page
//
//async function loadProfile(){
//
//    const urlParams = new URLSearchParams(window.location.search)
//    const username = urlParams.get("username")
//
//    const res = await fetch(API + "/users/" + username + "/profile",{
//        headers:{
//            "Authorization":"Bearer " + getToken()
//        }
//    })
//
//    const data = await res.json()
//
//    const profileInfo = document.getElementById("profileInfo")
//
//    profileInfo.innerHTML = `
//        <h3>${data.username}</h3>
//        <p>${data.bio}</p>
//
//        <p>Posts: ${data.postCount}</p>
//        <p>Followers: ${data.followerCount}</p>
//        <p>Following: ${data.followingCount}</p>
//    `
//
//    const postsDiv = document.getElementById("profilePosts")
//
//    postsDiv.innerHTML=""
//
//    data.posts.forEach(post => {
//
//        postsDiv.innerHTML += `
//            <div class="post">
//                <p>${post.content}</p>
//
//                ${post.imageUrl ? `<img src="${post.imageUrl}" width="200">` : ""}
//
//                <p>Likes: ${post.likeCount}</p>
//                <p>Comments: ${post.commentCount}</p>
//
//                <hr>
//            </div>
//        `
//    })
//
//}
//
//
