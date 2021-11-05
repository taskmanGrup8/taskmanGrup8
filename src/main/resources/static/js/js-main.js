/* Funció per executar el plugin select2 per seleccionar
*  més d'un departament a la vista usuaris/crear */

$(document).ready(function () {
  $('.departaments-multiple').select2();
});

/* Funció per ocultar i mostrar els filtres de la taula
*  que hi ha a la vista usuaris/listar */

$(document).ready(function () {
  $("#btn-filtres").click(function () {
    if ($("#card-filtres").css('display') === 'none') {
      $("#card-filtres").css('display', 'block');
    } else {
      $("#card-filtres").css('display', 'none');
    }
  });
});

/* Funció per ocultar i mostrar els llistats de
*  les taules usuaris & departaments */

$(document).ready(function () {
  $("#btn-filtres").click(function () {
    if ($("#mostrar-taula").css('display') === 'none') {
      $("#mostrar-taula").css('display', 'block');
    } else {
      $("#mostrar-taula").css('display', 'none');
    }
  });
});