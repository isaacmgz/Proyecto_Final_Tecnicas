package com.camp.politicalcampaign.controller;

import com.camp.politicalcampaign.model.VoluntarioDAO;
import com.camp.politicalcampaign.model.Voluntario;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para administración de voluntarios.
 * Permite listar y agregar voluntarios desde el panel de administración.
 */
@WebServlet("/admin/voluntarios")
public class AdminVoluntario extends HttpServlet {

    private final VoluntarioDAO dao = new VoluntarioDAO();

    /**
     * Maneja las peticiones GET.
     * Lista todos los voluntarios y los envía a la vista admin.jsp.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene la lista de voluntarios y la pone en el request
        List<Voluntario> lista = dao.listarTodos();
        request.setAttribute("voluntarios", lista);

        // Envía a la JSP que mostrará la tabla de voluntarios
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin.jsp");
        rd.forward(request, response);
    }

    /**
     * Maneja las peticiones POST.
     * Recibe datos del formulario, crea un voluntario y lo guarda.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lee parámetros del formulario
        String nombre       = request.getParameter("nombre");
        String apellido     = request.getParameter("apellido");
        String email        = request.getParameter("email");
        String telefono     = request.getParameter("telefono");
        String departamento = request.getParameter("departamento");
        String ciudad       = request.getParameter("ciudad");
        String mensaje      = request.getParameter("mensaje");

        // Construye el objeto Voluntario
        Voluntario v = new Voluntario();
        v.setNombre(nombre);
        v.setApellido(apellido);
        v.setEmail(email);
        v.setTelefono(telefono);
        v.setDepartamento(departamento);
        v.setCiudad(ciudad);
        v.setMensaje(mensaje);

        // Persiste el voluntario usando el DAO
        dao.agregar(v);

        // Prepara mensaje de éxito y recarga la lista de voluntarios
        request.setAttribute("mensaje", "Voluntario agregado exitosamente");
        request.setAttribute("voluntarios", dao.listarTodos());

        // Envía a la misma JSP para mostrar la tabla actualizada
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin.jsp");
        rd.forward(request, response);
    }
}