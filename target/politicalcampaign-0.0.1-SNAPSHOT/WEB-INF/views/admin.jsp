<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Panel de Administración</title>
  <!-- CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css" />
</head>
<body>
  <header class="admin-header">
    <h1>Inscritos al Movimiento</h1>
  </header>

  <main class="admin-main">
    <div class="container">
      <!-- Mensaje de éxito/error -->
      <c:if test="${not empty mensaje}">
        <div class="alert success">${mensaje}</div>
      </c:if>

      <!-- Búsqueda -->
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

      <!-- Paginación -->
      <div id="pagination" class="pagination"></div>
    </div>
  </main>

  <!-- Define el contexto JS antes de tus scripts -->
  <script>
    // Esto pone en JS la ruta base de tu app web (ej: "/politicalcampaign-0.0.1-SNAPSHOT")
    const CTX = '${pageContext.request.contextPath}';
  </script>
  <!-- Carga tu admin.js usando CTX para resolver rutas internas -->
  <script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>
