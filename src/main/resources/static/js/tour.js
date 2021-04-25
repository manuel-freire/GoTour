function muestraInstancia(tour, max){
    if (document.getElementById){
        for (let index = 1; index <= max; index++) {
            document.getElementById("instancia" + index).style.display = 'none';
        }
        document.getElementById("instancia" + tour).style.display = 'block';
    }
}
