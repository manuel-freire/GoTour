
function muestraInstancia(tour, max){
    if (document.getElementById){
        var elements = document.getElementsByClassName("contratacion");
        for (let index = 0; index < elements.length; index++) {
            elements[index].style.display = 'none';
        }
        document.getElementById("instancia" + tour).style.display = 'block';
    }
}

function validarFechas()
{
   var fechaIni = document.getElementById("fechaInicial").value;
   var fechaFin = document.getElementById("fechaFinal").value;

   var now = new Date(Date.now());

   if(Date.parse(now) > Date.parse(fechaIni) || Date.parse(fechaFin) < Date.parse(fechaIni)) {
       document.getElementById("fechaInicial").value = "";
       document.getElementById("fechaFinal").value = "";
       document.getElementById("divFinal").style.display = "none";
       alert("La fecha inicial debe ser menor que la fecha final y mayor que la fecha actual");
   }
}

function siguientePaso(){
    document.getElementById("divFinal").style.display = "block";
}

function instanciaNueva(){
    document.getElementById("instanciaNueva").style.display = "block";
}