const form = document.querySelector('form')

form.addEventListener('submit',e =>{
    e.preventDefault()
    const formdata = new FormData(e.target)
    iniciarSesion(formdata.get("email"),formdata.get("pass"))
})


const iniciarSesion = async (email,pass) =>{

   const datos={email,pass}
   console.log(datos)

   const request = await fetch("login",{
       method:"POST",
       headers:{
           'Content-Type': 'application/json'
       },
       body: JSON.stringify(datos)
   })


      if(request.ok){
            const token = await request.text();
            localStorage.setItem('token',token);
            localStorage.setItem('email',datos.email);
            window.location.href="index.html"

       }else{
            console.log(request.status)
       }

}
