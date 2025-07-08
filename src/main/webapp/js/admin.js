document.addEventListener('DOMContentLoaded', () => {
  console.log('✅ admin.js cargado y DOM listo');

  // Configuración y referencias DOM
  const API_URL     = 'http://localhost:8080/politicalcampaign-0.0.1-SNAPSHOT/api/inscritos';
  const tbody       = document.querySelector('#tabla-inscritos tbody');
  const searchInput = document.getElementById('search-input');
  const pagination  = document.getElementById('pagination');
  const rowsPerPage = 5;

  if (!tbody || !searchInput || !pagination) {
    console.error('❌ Faltan elementos en el DOM:', { tbody, searchInput, pagination });
    return;
  }

  let allData = [], filteredData = [], currentPage = 1;

  // Crea una fila de la tabla
  function addRow(ins) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${ins.nombre}</td>
      <td>${ins.apellido}</td>
      <td>${ins.email}</td>
      <td>${ins.telefono}</td>
      <td>${ins.departamento}</td>
      <td>${ins.ciudad}</td>
      <td>
        <button class="editar"   data-id="${ins.id}">✏️</button>
        <button class="eliminar" data-id="${ins.id}">🗑️</button>
      </td>`;
    tbody.appendChild(tr);
  }

  // Renderiza la tabla con los datos filtrados y paginados
  function renderTable() {
    tbody.innerHTML = '';
    const start = (currentPage - 1) * rowsPerPage;
    const end   = start + rowsPerPage;
    const slice = filteredData.slice(start, end);

    if (slice.length === 0) {
      tbody.innerHTML = `<tr><td colspan="7" class="no-data">No hay registros</td></tr>`;
    } else {
      slice.forEach(addRow);
    }
  }

  // Asocia los eventos de los botones de eliminar y editar
  function attachHandlers() {
    // Eliminar registro
    document.querySelectorAll('.eliminar').forEach(btn => {
      btn.onclick = async () => {
        if (!confirm('¿Eliminar este registro?')) return;
        const id = btn.dataset.id;
        const resp = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (resp.ok) {
          allData = allData.filter(i => i.id != id);
          filteredData = filteredData.filter(i => i.id != id);
          renderTable();
          renderPagination();
          attachHandlers();
        } else {
          alert('Error al eliminar');
        }
      };
    });

    // Edición inline de registro
    document.querySelectorAll('.editar').forEach(btn => {
      btn.onclick = () => {
        const tr = btn.closest('tr');
        const id = btn.dataset.id;
        const tds = tr.querySelectorAll('td');
        const orig = Array.from(tds).slice(0, 6).map(td => td.textContent);

        // Cambia celdas a inputs editables
        tds[0].innerHTML = `<input type="text" value="${orig[0]}" />`;
        tds[1].innerHTML = `<input type="text" value="${orig[1]}" />`;
        tds[2].innerHTML = `<input type="email" value="${orig[2]}" />`;
        tds[3].innerHTML = `<input type="tel" value="${orig[3]}" />`;
        tds[4].innerHTML = `<input type="text" value="${orig[4]}" />`;
        tds[5].innerHTML = `<input type="text" value="${orig[5]}" />`;

        // Botones Guardar / Cancelar
        const actionsTd = tds[6];
        actionsTd.innerHTML = `
          <button class="guardar">✔️</button>
          <button class="cancelar">✖️</button>
        `;

        // Cancelar edición
        actionsTd.querySelector('.cancelar').onclick = () => {
          orig.forEach((val, i) => tds[i].textContent = val);
          actionsTd.innerHTML = `
            <button class="editar" data-id="${id}">✏️</button>
            <button class="eliminar" data-id="${id}">🗑️</button>
          `;
          attachHandlers();
        };

        // Guardar cambios
        actionsTd.querySelector('.guardar').onclick = async () => {
          const updated = {
            nombre:       tds[0].querySelector('input').value.trim(),
            apellido:     tds[1].querySelector('input').value.trim(),
            email:        tds[2].querySelector('input').value.trim(),
            telefono:     tds[3].querySelector('input').value.trim(),
            departamento: tds[4].querySelector('input').value.trim(),
            ciudad:       tds[5].querySelector('input').value.trim(),
            mensaje:      ''
          };
          try {
            const resp = await fetch(`${API_URL}/${id}`, {
              method: 'PUT',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(updated)
            });
            if (!resp.ok) throw new Error(`Status ${resp.status}`);
            const saved = await resp.json();

            // Actualiza la fila con los nuevos valores
            tds[0].textContent = saved.nombre;
            tds[1].textContent = saved.apellido;
            tds[2].textContent = saved.email;
            tds[3].textContent = saved.telefono;
            tds[4].textContent = saved.departamento;
            tds[5].textContent = saved.ciudad;

            actionsTd.innerHTML = `
              <button class="editar"   data-id="${id}">✏️</button>
              <button class="eliminar" data-id="${id}">🗑️</button>
            `;
            attachHandlers();
            alert('Registro actualizado exitosamente');
          } catch (err) {
            console.error('Error al actualizar:', err);
            alert('No se pudo actualizar el registro');
          }
        };
      };
    });
  }

  // Renderiza la paginación
  function renderPagination() {
    pagination.innerHTML = '';
    const pageCount = Math.ceil(filteredData.length / rowsPerPage);
    for (let i = 1; i <= pageCount; i++) {
      const btn = document.createElement('button');
      btn.textContent = i;
      btn.classList.toggle('active', i === currentPage);
      btn.addEventListener('click', () => {
        currentPage = i;
        renderTable();
        renderPagination();
        attachHandlers();
      });
      pagination.appendChild(btn);
    }
  }

  // Carga inicial de datos desde la API
  const loadData = async () => {
    try {
      console.log('==> Llamando a:', API_URL);
      const res  = await fetch(API_URL);
      console.log('==> HTTP status:', res.status);
      const text = await res.text();
      console.log('==> RAW response:', text);
      let data;
      try {
        data = JSON.parse(text);
      } catch (e) {
        console.error('❌ Respuesta no es JSON válido:', e);
        return;
      }
      allData = data;
      filteredData = allData;
      renderTable();
      renderPagination();
      attachHandlers();
    } catch (err) {
      console.error('❌ Error al cargar inscritos:', err);
      alert('No se pudieron cargar los registros.');
    }
  };

  // Filtrado en tiempo real
  searchInput.addEventListener('input', () => {
    const term = searchInput.value.trim().toLowerCase();
    filteredData = allData.filter(item =>
      item.nombre.toLowerCase().includes(term) ||
      item.email.toLowerCase().includes(term)
    );
    currentPage = 1;
    renderTable();
    renderPagination();
    attachHandlers();
  });

  // Broadcast para nuevas altas
  const bc = new BroadcastChannel('inscripciones');
  bc.onmessage = e => {
    allData.unshift(e.data);
    filteredData = allData;
    currentPage = 1;
    renderTable();
    renderPagination();
    attachHandlers();
  };

  // Inicializa la carga de datos
  loadData();
});
