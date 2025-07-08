window.addEventListener('DOMContentLoaded', () => {
  console.log('Front cargado correctamente ✅');

  // ================= BOTÓN "VOLVER ARRIBA" =================
  const btn = document.getElementById('back-to-top');

  // Muestra u oculta el botón según el scroll
  window.addEventListener('scroll', () => {
    if (window.scrollY > 400) {
      btn.classList.add('show');
    } else {
      btn.classList.remove('show');
    }
  });

  // Desplazamiento suave al hacer click en el botón
  btn.addEventListener('click', () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  });

  // ================= BROADCAST CHANNEL PARA INSCRIPCIONES =================
  // Permite comunicación entre pestañas/ventanas
  const bc = new BroadcastChannel('inscripciones');

  // ================= FORMULARIO DE INSCRIPCIÓN =================
  const form = document.getElementById('form-involucrarse');

  // Verifica que el formulario exista antes de continuar
  if (!form) return console.warn('Formulario de inscripción no encontrado');
  console.log('Formulario de inscripción encontrado:', form);

  // URL de la API para enviar inscripciones
  const API_URL = `${CTX}/api/inscritos`;

  // Maneja el envío del formulario
  form.addEventListener('submit', async (event) => {
    event.preventDefault(); // Previene recarga de la página

    // Recopila datos del formulario
    const data = {
      nombre:       form.nombre.value.trim(),
      apellido:     form.apellido.value.trim(),
      email:        form.email.value.trim(),
      telefono:     form.telefono.value.trim(),
      departamento: form.departamento.value,
      ciudad:       form.ciudad.value.trim(),
      mensaje:      form.mensaje.value.trim()
    };

    try {
      // Envía los datos al backend usando fetch
      const resp = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
      });

      // Si la respuesta no es exitosa, lanza error
      if (!resp.ok) {
        const errorText = await resp.text();
        console.error(`Error ${resp.status}: ${errorText}`);
        throw new Error(`Error ${resp.status}: ${errorText}`);
      }

      // Procesa la respuesta del backend
      const creado = await resp.json();
      form.reset(); // Limpia el formulario
      alert('¡Inscripción enviada correctamente!');

      // Notifica a otras pestañas/ventanas
      bc.postMessage(creado);

    } catch (err) {
      // Manejo de errores en el envío
      console.error('Error al enviar inscripción:', err);
      alert('Hubo un error al enviar tus datos. Intenta de nuevo.');
    }
  });
});