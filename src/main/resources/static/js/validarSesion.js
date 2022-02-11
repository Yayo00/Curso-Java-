document.addEventListener('DOMContentLoaded',()=>{
    if(localStorage.getItem('token')===null &&localStorage.getItem('email')===null){
        window.location.href="login.html";
    }
})