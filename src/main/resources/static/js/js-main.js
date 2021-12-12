/* Funció per executar el plugin select2 per seleccionar
*  més d'un departament a la vista usuaris/crear.html */
$(document).ready(function () {
  $('.departaments-multiple').select2({
    placeholder: "Selecciona un o més departaments"
  });
});

/* Mostrar el placeholder al Select2 de crear tasques */
$(document).ready(function () {
  $('.tasques-multiple').select2({
    placeholder: "Selecciona una o més fases"
  });
});

/* Funció per ocultar i mostrar els filtres de la taula
*  que hi ha a la vista usuaris/listar.html */
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

/* Mostrar i ocultar canvi de clau */
$(document).ready(function () {
  $("#btn-canvi-clau").click(function () {
    if ($("#mostrar-canvi-clau").css('display') === 'none') {
      $("#mostrar-canvi-clau").css('display', 'block');
    } else {
      $("#mostrar-canvi-clau").css('display', 'none');
    }
  });
});

/* Funció per poder fer servir el plugin Poppin */
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl)
})