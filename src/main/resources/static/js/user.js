$("#add-languaje").on("click", addLanguaje);

function addLanguaje() {
    var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});
    var dataString;
    dataString = 'idioma='+$("#idioma").val();
  $.ajax({
    url: '/user/addLanguaje',
    type: "POST",
    data: dataString,
    success: function(respuesta) {

      var listaIdiomas = $("#lista-idiomas");
      listaIdiomas.append(
        '<div>'
      +     '<p class="idiomas">'+$("#idioma").val()+ '</p>'
      + '</div>'
    );
    $("#idioma").val("");
    },
    error: function() {
      console.log("No se ha podido obtener la información");
    }
  });
}

$("#add-review").on("click", addReview);

function addReview() {
    var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});
    var valoracion;
    var textoReview;
    var instancia;
    val = +$("#valoracion").val();
    text = $("#textoReview").val();
    instancia =$("#elegida").val();
  $.ajax({
    url: '/tour/'+instancia+'/valorar',
    type: "POST",
    data: {valoracion: val, textoReview: text},
    success: function(respuesta) {
        console.log("Review añadida")
        $("#esconder").hide()
    },
    error: function() {
      console.log("No se ha podido realizar la review ", instancia, valoracion, textoReview);
    }
  });
}