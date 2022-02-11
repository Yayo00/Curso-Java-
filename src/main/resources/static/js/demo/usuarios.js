const search =document.querySelector('.dataTables_filter > label > input')
console.log(search)

// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#usuarios').DataTable();
  cargarUsuarios();
  if(localStorage.getItem('token')!==null && localStorage.getItem('email')!==null){
      verNombre(localStorage.getItem('email'))
  }
});

search.addEventListener('input',e =>{
    console.log(e.target.value)
})

const buscarNombre = async valor =>{
    const request  = await fetch(`search/${valor}`,{
        method:'GET',
        headers:{
            "Accept":"application/json",
            "Content-type":"application/json",
            'Authorization': localStorage.getItem('token') //Autoriza el token a que se cumpla la petición
        }
    })

    console.log(await request.json())
}



const verNombre= async email=>{
    const obj={email}

    const request = await fetch("nombre",{
        method:"POST",
        headers:{
            "Content-type":"application/json",
            'Authorization': localStorage.getItem('token') //Autoriza el token a que se cumpla la petición
        },
        body: JSON.stringify(obj) //casteo a una cadena json
    })
    if(request.ok){
        let user = document.querySelector('.usuario')
        user.textContent= await request.text()
    }
    return null;

}

//Autorización de acciones por token iniciado
const cargarUsuarios= async()=>{
    const request = await fetch("users",{
        method:"GET",
        headers:{
            "Accept":"application/json",
            "Content-type":"application/json",
            'Authorization': localStorage.getItem('token') //Autoriza el token a que se cumpla la petición
        }
    });
    const respuesta = await request.json();
    llenarTabla(respuesta)

}

const llenarTabla = (datos)=>{
    console.log(datos)
    const tablaBody = document.querySelector("#usuarios > tbody")

    datos.forEach(registro =>{


        const {id,nombre,apellido,email,telefono} = registro
        let telefonotxt = telefono == "" || telefono == null ? "Sin registro": telefono;
         tablaBody.innerHTML+=`
            <tr>
                <td>${id}</td>
                <td>${nombre} ${apellido}</td>
                <td>${email}</td>
                <td>${telefonotxt}</td>
                <td>
                    <a href="#" class="btn btn-danger btn-circle eliminar" id='${id}'>
                        <i class="fas fa-trash"></i>
                    </a>
                </td>
            </tr>`

    })
    if(tablaBody.children[0].classList.contains('odd')){
        tablaBody.removeChild(tablaBody.children[0])
    }

    for(let row of tablaBody.children){
        let btnEliminar = row.children[4].children[0]

        btnEliminar.addEventListener('click',e=>{
               if(e.target.classList.contains("fas","fa-trash") || e.target.classList.contains("btn")){

                    if(eliminarRegistro(btnEliminar.id)){
                        tablaBody.removeChild(row)
                    }

               }
        })
    }

}

const eliminarRegistro = async id =>{
    const request = await fetch(`user/${id}`,{
        method:"DELETE",
        headers:{
            'Accept':'application/json',
            'Content-Type':'application/json',
            'Authorization': localStorage.getItem('token') //Autoriza el token a que se cumpla la petición
        }
    })
    return true
}




