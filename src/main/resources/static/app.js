const API="http://localhost:8080"

function getToken(){
    return localStorage.getItem("token")
}

function setToken(token){
    localStorage.setItem("token",token)
}
//LOGIN -------------------------------------------------
async function login(){

const username=document.getElementById("username").value
const password=document.getElementById("password").value

const res=await fetch(API+"/auth/login",{
method:"POST",
headers:{
"Content-Type":"application/json"
},
body:JSON.stringify({
username,
password
})
})

const data=await res.json()

setToken(data.token)

window.location="feed.html"
}

//REGISTER -------------------------------------------------

async function register(){

const username=document.getElementById("username").value
const email=document.getElementById("email").value
const mobile=document.getElementById("mobile").value
const bio=document.getElementById("bio").value
const password=document.getElementById("password").value

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
})

window.location="login.html"
}

//LOAD-FEED -------------------------------------------------

async function loadFeed(){

const res=await fetch(API+"/feed?page=0&size=10",{
headers:{
Authorization:"Bearer "+getToken()
}
})

const posts=await res.json()

const feed=document.getElementById("feed")

feed.innerHTML+=`

<div class="post">

<h3>${p.user.username}</h3>

<p>${p.content}</p>

<img src="${p.imageUrl || ""}" width="200">

<p> ${p.likeCount}  ${p.commentCount}</p>

<button onclick="likePost(${p.postId})">Like</button>

<div>
<input id="comment-${p.postId}" placeholder="Write comment">
<button onclick="commentPost(${p.postId})">
Comment
</button>
</div>

<div id="comments-${p.postId}"></div>

</div>

`

})

}

//LIKE-POST -------------------------------------------------

async function likePost(postId){

await fetch(API+"/posts/"+postId+"/like",{
method:"POST",
headers:{
Authorization:"Bearer "+getToken()
}
})

loadFeed()

}

//CREATE-POST -------------------------------------------------

async function createPost(){

const content=document.getElementById("content").value
const image=document.getElementById("image").files[0]

const form=new FormData()

form.append("content",content)

if(image){
form.append("image",image)
}

await fetch(API+"/posts",{
method:"POST",
headers:{
Authorization:"Bearer "+getToken()
},
body:form
})

window.location="feed.html"
}

//COMMENT-POST -------------------------------------------------

async function commentPost(postId){

const content=document.getElementById("comment-"+postId).value

await fetch(API+"/posts/"+postId+"/comments",{
method:"POST",
headers:{
"Content-Type":"application/json",
Authorization:"Bearer "+getToken()
},
body:JSON.stringify({
content
})
})

loadFeed()

}

//LOAD-COMMENT-UNDER-POST -------------------------------------------------

async function loadComments(postId){

const res=await fetch(API+"/posts/"+postId+"/comments",{
headers:{
Authorization:"Bearer "+getToken()
}
})

const comments=await res.json()

const div=document.getElementById("comments-"+postId)

comments.forEach(c=>{

div.innerHTML+=`
<p><b>${c.user.username}</b>: ${c.content}</p>
`

})

}