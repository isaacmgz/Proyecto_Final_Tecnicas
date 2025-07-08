package com.camp.politicalcampaign.model;

import com.camp.politicalcampaign.db.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones CRUD sobre la entidad Voluntario.
 */
public class VoluntarioDAO {

    /**
     * Inserta un nuevo voluntario en la tabla "inscritos".
     * La columna fecha_registro se asigna automáticamente en MySQL.
     */
    public void agregar(Voluntario v) {
        String sql = """
            INSERT INTO inscritos
              (nombre, apellido, email, telefono, departamento, ciudad, mensaje)
            VALUES (?,?,?,?,?,?,?)
            """;
        try (Connection conn = DB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, v.getNombre());
            ps.setString(2, v.getApellido());
            ps.setString(3, v.getEmail());
            ps.setString(4, v.getTelefono());
            ps.setString(5, v.getDepartamento());
            ps.setString(6, v.getCiudad());
            ps.setString(7, v.getMensaje());
            ps.executeUpdate();

            // Leer ID generado (opcional)
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    v.setId(keys.getLong(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera todos los voluntarios ordenados por fecha_registro descendente.
     */
    public List<Voluntario> listarTodos() {
        List<Voluntario> lista = new ArrayList<>();
        String sql = "SELECT * FROM inscritos ORDER BY fecha_registro DESC";

        try (Connection conn = DB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Voluntario v = mapear(rs);
                lista.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Actualiza un voluntario existente.
     */
    public boolean actualizar(Voluntario v) {
        String sql = """
            UPDATE inscritos SET
              nombre=?, apellido=?, email=?, telefono=?,
              departamento=?, ciudad=?, mensaje=?
            WHERE id = ?
            """;
        try (Connection conn = DB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getNombre());
            ps.setString(2, v.getApellido());
            ps.setString(3, v.getEmail());
            ps.setString(4, v.getTelefono());
            ps.setString(5, v.getDepartamento());
            ps.setString(6, v.getCiudad());
            ps.setString(7, v.getMensaje());
            ps.setLong(8, v.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina un voluntario por ID.
     */
    public boolean eliminar(Long id) {
        String sql = "DELETE FROM inscritos WHERE id = ?";
        try (Connection conn = DB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mapea un ResultSet a un objeto Voluntario, incluyendo fechaRegistro.
     */
    private Voluntario mapear(ResultSet rs) throws SQLException {
        Voluntario v = new Voluntario();
        v.setId(rs.getLong("id"));
        v.setNombre(rs.getString("nombre"));
        v.setApellido(rs.getString("apellido"));
        v.setEmail(rs.getString("email"));
        v.setTelefono(rs.getString("telefono"));
        v.setDepartamento(rs.getString("departamento"));
        v.setCiudad(rs.getString("ciudad"));
        v.setMensaje(rs.getString("mensaje"));

        Timestamp ts = rs.getTimestamp("fecha_registro");
        if (ts != null) {
            v.setFechaRegistro(ts.toLocalDateTime());
        }
        return v;
    }
}
