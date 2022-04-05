package com.example.clinicaDental.repository.impl;

import com.example.clinicaDental.repository.IDao;
import com.example.clinicaDental.dominio.Domicilio;
import com.example.clinicaDental.Util.*;
import com.example.clinicaDental.dominio.Odontologo;
import com.example.clinicaDental.dominio.Paciente;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class PacienteDAOH2 implements IDao<Paciente> {
    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:~/db_clinica2;INIT=RUNSCRIPT FROM 'create.sql'";
    private final static String DB_USER ="sa";
    private final static String DB_PASSWORD = "";
    private DomicilioDAOH2 domicilioDaoH2 = new DomicilioDAOH2();

    @Override
    public Paciente guardar(Paciente paciente) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Domicilio domicilio = domicilioDaoH2.guardar(paciente.getDomicilio());
            paciente.getDomicilio().setId(domicilio.getId());



            paciente.getDomicilio().setId(domicilio.getId());
            preparedStatement = connection.prepareStatement("INSERT INTO pacientes(nombre,apellido,email,dni,fecha_ingreso,domicilio_id) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            //preparedStatement.setInt(1,paciente.getId());
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setString(3, paciente.getEmail());
            preparedStatement.setInt(4, paciente.getDni());
            preparedStatement.setDate(5, Util.utilDateToSqlDate(paciente.getFechaIngreso()));
            preparedStatement.setInt(6, paciente.getDomicilio().getId());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if(keys.next())
                paciente.setId(keys.getInt(1));
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return paciente;
    }

    @Override
    public Paciente buscar( int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Paciente paciente = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT id,nombre,apellido,email,dni,fecha_ingreso,domicilio_id  FROM pacientes where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int idPaciente = result.getInt("id");
                String nombre = result.getString("nombre");
                String apellido = result.getString("apellido");
                String email = result.getString("email");
                int dni = result.getInt("dni");
                Date fechaIngreso = result.getDate("fecha_ingreso");
                int idDomicilio = result.getInt("domicilio_id");

                //Con el domicilio_id traemos el domicilio de la tabla domicilio a traves de DAO de Domicilios
                Domicilio domicilio = domicilioDaoH2.buscar(idDomicilio);

                paciente = new Paciente(idPaciente,nombre,apellido,email,dni,fechaIngreso,domicilio);
            }
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return paciente;
    }

    @Override
    public void eliminar(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM pacientes where id = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public List<Paciente> buscarTodos() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Paciente> pacientes = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT *  FROM pacientes");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int idPaciente = result.getInt("id");
                String nombre = result.getString("nombre");
                String apellido = result.getString("apellido");
                String email = result.getString("email");
                int dni = result.getInt("dni");
                Date fechaIngreso = result.getDate("fecha_ingreso");
                int idDomicilio = result.getInt("domicilio_id");

                Domicilio domicilio = domicilioDaoH2.buscar(idDomicilio);

                Paciente paciente = new Paciente(idPaciente,nombre,apellido,email,dni,fechaIngreso,domicilio);
                pacientes.add(paciente);
            }
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return pacientes;
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Domicilio domicilio = domicilioDaoH2.actualizar(paciente.getDomicilio());
            preparedStatement = connection.prepareStatement("UPDATE pacientes SET nombre=?, apellido=?, email=?, dni=?, fecha_ingreso=?, domicilio_id=? WHERE id = ?");
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setString(3, paciente.getEmail());
            preparedStatement.setInt(4, paciente.getDni());
            preparedStatement.setDate(5, Util.utilDateToSqlDate(paciente.getFechaIngreso()));
            preparedStatement.setInt(6, paciente.getDomicilio().getId());
            preparedStatement.setInt(7, paciente.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return paciente;
    }
}
