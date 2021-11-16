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
function enviarTexto() {

    

}

function enviartexto2(){
    console.log("entre")
    var texto= document.getElementById("lenguaje1").value.slice(0, -1); 
    return texto;
    
}


function enviarTexto4() {
        console.log("entre")
       var texto= document.getElementById("lenguaje1").value.slice(0, -1); 
       document.getElementById("lenguaje2").value = document.getElementById("lenguaje2").value +" "+texto ;
       document.getElementById("lenguaje1").value="";
        
}

