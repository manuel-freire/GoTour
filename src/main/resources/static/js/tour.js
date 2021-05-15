function muestraInstancia(tour, max){
    if (document.getElementById){
        var elements = document.getElementsByClassName("contratacion");
        for (let index = 0; index < elements.length; index++) {
            elements[index].style.display = 'none';
        }
        document.getElementById("instancia" + tour).style.display = 'block';
    }
}