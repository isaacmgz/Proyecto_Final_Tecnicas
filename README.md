## Proyecto Técnicas de Programación

**Tema**: Campaña pólitica

**Contexto**: Ofrecer un proyecto a un político para su campaña electoral, una página web con su biografía, información y noticias relacionadas, todo ésto con el fin de atraer más votantes y que sus seguidores más fieles puedan registrarse en una lista privada del candidato donde tendrán más contacto con él e información de primera mano

#### Estructura y contenido de la página:

**$I.$ _Capa de Frontend_**

1. Páginas HTML/JSP

    - `index.html`: contiene la UI pública (hero, biografía, plataforma, noticias, eventos, “Involúcrate”) asociada al candidato.

    - `admin.jsp`: la UI de administración, donde el servidor maneja la lista de voluntarios inscritos.
  
2. CSS y JS:

   - `styles.css` / `admin.css`: estilos para cada sección.

   - `main.js`: Captura el submit del form, lo serializa a JSON y obtiene los datos de la base de datos (fetch).

   - `admin.js`: Renderiza la tabla, añade paginación y filtrado en tiempo real.

**$II.$ _Capa de control (Backend – Servlets)_**

1. API JSON REST
   
   - `ApiVoluntarioServlet` (mapeado en `/api/inscritos/*`):
     - `doGet`: serializa la lista completa (dao.listarTodos()) como JSON.
     - `doPost`: deserializa JSON a Voluntario y llama a dao.agregar(v)
     - `doPut`: lee el id de la URL y el body JSON, actualiza con dao.actualizar(v).
     - `doDelete`: borra el voluntario con dao.eliminar(id).

2. Panel JSP de Administración

   - AdminVoluntario (mapeado en /admin/voluntarios):
     - `doGet`: obtiene List<Voluntario> lista = dao.listarTodos() y la inyecta en el request como atributo "voluntarios", luego hace forward("/WEB-INF/views/admin.jsp").
     - `doPost`: lee los campos del formulario, construye un Voluntario, lo inserta con dao.agregar(v), vuelve a recargar la lista y reenvía a la misma JSP con un mensaje de éxito.

**$III.$ _Capa de datos (DAO y Base de Datos)_**

1. `DB`
   - Clase singleton que gestiona la conexión JDBC a MySQL.
     
   - Parámetros: URL, usuario, contraseña, driver.
2. `VoluntarioDAO`
   - Clase que aísla la lógica de acceso a la base de datos en una clase.

```
politicalcampaign/                  ← raíz del proyecto Maven
├── pom.xml                         ← descriptor Maven (packaging = war)
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── camp
│   │   │           └── politicalcampaign
│   │   │               ├── PoliticalcampaignApplication.java
│   │   │               ├── controller
│   │   │               │   ├── AdminVoluntario.java         ← servlet JSP‑panel
│   │   │               │   └── ApiVoluntarioServlet.java    ← servlet JSON REST
│   │   │               ├── db
│   │   │               │   └── DB.java                       ← util JDBC singleton
│   │   │               └── model
│   │   │                   ├── Voluntario.java               ← bean/POJO
│   │   │                   └── VoluntarioDAO.java            ← CRUD JDBC
│   │   ├── resources
│   │   │   └── application.properties                        ← configuración DataSource, puerto, etc.
│   │   └── webapp
│   │       ├── WEB-INF
│   │       │   └── views
│   │       │       └── admin.jsp                            ← JSP de administración
│   │       ├── index.html                                   ← UI pública
│   │       ├── css
│   │       │   ├── styles.css                              ← estilos públicos
│   │       │   └── admin.css                               ← estilos admin
│   │       ├── js
│   │       │   ├── main.js                                  ← lógica página/formulario público
│   │       │   └── admin.js                                 ← lógica tabla/admin
│   │       └── assets                                         
│   │           ├── logo-campana.svg                          
│   │           ├── candidato.jpg                             
│   │           ├── fondo.png                                 
│   │           ├── noticia1.jpg                                   
│   │           ├── noticia2.jpg                               
│   │           └── noticia3.jpg                                                            
└── src
    └── test
```
