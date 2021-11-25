/*
function enviarTexto() {
    console.log("entreadfe")
    var a = document.getElementById("lenguaje1").value
   
    console.log( a.slice(-1))
    if(a.slice(-1)== ","){
        enviarTexto2();
    }
    
    
}
*/
var tecnos = [];
function enviarTexto() {

var tecno=document.getElementById("lenguaje1").value;


 
if( tecno.slice(-1) == ","){
	
	if(!tecnos.includes(enviartexto2())){
		
   // document.getElementById("resultado").innerHTML = document.getElementById("resultado").innerHTML   +"<button class='boton' class='botone' name='lenguajes[]' value="+enviartexto2()+ ">"+ enviartexto2()+"</button> ";
   document.getElementById("resultado").innerHTML = document.getElementById("resultado").innerHTML +
   "  <div class='boton'  name='lenguajes' value="+enviartexto2()+">"+ "<input type='checkbox' class='invisible' name='lenguajes' value="+enviartexto2()+" checked='checked'>"+ enviartexto2()+"</div> "
   	 tecnos.push(enviartexto2());
    document.getElementById("lenguaje1").value="";
    }else{
	alert("ya ingreso este lenguaje");
	 document.getElementById("lenguaje1").value="";
	}
}
    //	<input class="form-check-input" type="checkbox" id="tecnos" th:value="${tec.lenguaje}"  name="lenguajes" id="flexCheckDefault" >
		//				<label th:text="${tec.lenguaje}" for="tecnologia" class="form-check-label" ></label>

}

function enviartexto2(){
    
    var texto= document.getElementById("lenguaje1").value.slice(0, -1); 
    return texto.toUpperCase();
    
}


function enviarTexto4() {
        console.log("entre")
       var texto= document.getElementById("lenguaje1").value.slice(0, -1); 
       document.getElementById("lenguaje2").value = document.getElementById("lenguaje2").value +" "+texto ;
       document.getElementById("lenguaje1").value="";
        
}

