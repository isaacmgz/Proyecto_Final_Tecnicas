<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Panel de Administración</title>
  <!-- CSS principal del panel de administración -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css" />
</head>
<body>
  <header class="admin-header">
    <h1>Inscritos al Movimiento</h1>
  </header>

  <main class="admin-main">
    <div class="container">
      <!-- Mensaje de éxito o error tras operaciones -->
      <c:if test="${not empty mensaje}">
        <div class="alert success">${mensaje}</div>
      </c:if>

      <!-- Barra de búsqueda -->
      <div class="table-controls">
        <input
          type="text"
          id="search-input"
          placeholder="Filtrar por nombre o email…"
        />
      </div>

      <!-- Tabla de voluntarios -->
      <table id="tabla-inscritos">
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
            <th>Teléfono</th>
            <th>Departamento</th>
            <th>Ciudad</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <!-- Itera sobre la lista de voluntarios y muestra cada uno -->
          <c:forEach var="v" items="${voluntarios}">
            <tr>
              <td><c:out value="${v.nombre}" /></td>
              <td><c:out value="${v.apellido}" /></td>
              <td><c:out value="${v.email}" /></td>
              <td><c:out value="${v.telefono}" /></td>
              <td><c:out value="${v.departamento}" /></td>
              <td><c:out value="${v.ciudad}" /></td>
              <td>
                <button class="editar" data-id="${v.id}">✏️</button>
                <button class="eliminar" data-id="${v.id}">🗑️</button>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <!-- Contenedor para la paginación -->
      <div id="pagination" class="pagination"></div>
    </div>
  </main>

  <!-- Define el contexto base para scripts JS -->
  <script>
    // Ruta base de la aplicación para uso en JS
    const CTX = '${pageContext.request.contextPath}';
  </script>
  <!-- Script principal de administración -->
  <script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>
