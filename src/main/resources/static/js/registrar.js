
const form = document.querySelector('form')

form.addEventListener('submit',e =>{
    e.preventDefault()
    const formData = new FormData(e.target)

    if(formData.get("contrasena")!==formData.get("repetir-contra")){
        alert("Contraseñas no son iguales")
        return
    }
    registrar(formData)


})

const registrar = async formData => {

    const datos = { //NOTA: los parámetros pasados deben llamarse igual que los de la entidad Usuario
        nombre: formData.get("nombre"),
        apellido: formData.get("apellido"),
        email: formData.get("email"),
        telefono: formData.get("telefono"),
        pass: formData.get("contrasena")
    }
    console.log(JSON.stringify(datos))

    const request = await fetch("registrar",{
        method:"POST",
        headers:{
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos) //convierte a una cadena json
    })

    //Si no entra la petición
    if (request.ok) {
        console.log(request.status)
        crearMensaje("Insertado correctamente")
        window.location.href="login.html"
    }


}

const crearMensaje = mensaje =>{
    const alert = document.createElement('div')
    alert.style.width="100%"
    alert.style.background="black"
    alert.style.padding="15px"
    alert.textContent = mensaje
    form.insertAdjacentElement('beforebegin',alert)
    setTimeout(()=>{
        alert.remove();
    },3000)


}