package com.camp.politicalcampaign.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.camp.politicalcampaign.model.Voluntario;
import com.camp.politicalcampaign.model.VoluntarioDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * Servlet API REST para operaciones CRUD sobre voluntarios.
 * Permite listar, crear, actualizar y eliminar voluntarios vía JSON.
 */
@WebServlet(urlPatterns = { "/api/inscritos", "/api/inscritos/*" })
public class ApiVoluntarioServlet extends HttpServlet {

    private final VoluntarioDAO dao = new VoluntarioDAO();
    private final ObjectMapper json = new ObjectMapper()
    .registerModule(new JavaTimeModule())                           // Soporta LocalDateTime
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);       // Salida en ISO‑8601

    /**
     * Devuelve la lista de voluntarios en formato JSON.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        json.writeValue(resp.getOutputStream(), dao.listarTodos());
    }

    /**
     * Crea un nuevo voluntario a partir de un JSON recibido.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Voluntario v = json.readValue(req.getInputStream(), Voluntario.class);
        dao.agregar(v);
        resp.setStatus(201);
        json.writeValue(resp.getOutputStream(), v);
    }

    /**
     * Actualiza un voluntario existente según su ID (en la URL).
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getPathInfo().substring(1));
        Voluntario v = json.readValue(req.getInputStream(), Voluntario.class);
        v.setId(id);
        boolean ok = dao.actualizar(v);
        resp.setStatus(ok ? 200 : 404);
        if (ok) json.writeValue(resp.getOutputStream(), v);
    }

    /**
     * Elimina un voluntario según su ID (en la URL).
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getPathInfo().substring(1));
        boolean ok = dao.eliminar(id);
        resp.setStatus(ok ? 204 : 404);
    }
}

