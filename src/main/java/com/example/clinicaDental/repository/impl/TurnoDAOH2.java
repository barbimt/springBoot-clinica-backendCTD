package com.example.clinicaDental.repository.impl;

import com.example.clinicaDental.Util.Util;
import com.example.clinicaDental.repository.IDao;

import com.example.clinicaDental.dominio.Odontologo;
import com.example.clinicaDental.dominio.Paciente;
import com.example.clinicaDental.dominio.Turno;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class TurnoDAOH2 implements IDao<Turno> {
    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:~/db_clinica2;INIT=RUNSCRIPT FROM 'create.sql'";
    private final static String DB_USER ="sa";
    private final static String DB_PASSWORD = "";
    private PacienteDAOH2 pacienteDaoH2 = new PacienteDAOH2();
    private OdontologoDAOH2 odontologoDaoH2 = new OdontologoDAOH2();
    @Override
    public Turno guardar(Turno turno) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO turno(fecha,hora,paciente_id,odontologo_id) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
           Odontologo odontologo = odontologoDaoH2.guardar(turno.getOdontologo());
            Paciente paciente = pacienteDaoH2.guardar(turno.getPaciente());
            turno.getPaciente().setId(paciente.getId());
            turno.getOdontologo().setId(odontologo.getId());
            preparedStatement.setDate(1, Util.utilDateToSqlDate(turno.getFecha()));
            preparedStatement.setTime(2,  Util.utilDateToSqlTime(turno.getHora()));
            preparedStatement.setInt(3, turno.getPaciente().getId());
            preparedStatement.setInt(4, turno.getOdontologo().getId());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if(keys.next())
                turno.setIdTurno(keys.getInt(1));

            preparedStatement.close();


        } catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();

        }
        return turno;
    }

    @Override
    public Turno buscar( int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Turno turno = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT id,fecha,hora,paciente_id,odontologo_id FROM turno where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int idTurno = result.getInt("id");
                Date fecha = result.getDate("fecha");
                Time hora = result.getTime("hora");
                int idPaciente = result.getInt("paciente_id");
                int idOdontologo = result.getInt("odontologo_id");
                Odontologo odontologo = odontologoDaoH2.buscar(idOdontologo);
                Paciente paciente = pacienteDaoH2.buscar(idPaciente);
                turno = new Turno(idTurno,fecha,hora,paciente,odontologo);
            }
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return turno;

    }

    @Override
    public void eliminar(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM turno where id = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Turno> buscarTodos() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Turno> turnos = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT *  FROM turno");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int idTurno = result.getInt("id");
                Date fecha = result.getDate("fecha");
                Time hora = result.getTime("hora");
                int idPaciente = result.getInt("paciente_id");
                int idOdontologo = result.getInt("odontologo_id");
                Odontologo odontologo = odontologoDaoH2.buscar(idOdontologo);
                Paciente paciente = pacienteDaoH2.buscar(idPaciente);
                Turno turno = new Turno(idTurno,fecha,hora,paciente,odontologo);
                turnos.add(turno);
            }
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return turnos;
    }

    @Override
    public Turno actualizar(Turno turno){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Odontologo odontologo = odontologoDaoH2.actualizar(turno.getOdontologo());
            Paciente paciente = pacienteDaoH2.actualizar(turno.getPaciente());
            preparedStatement = connection.prepareStatement("UPDATE turno SET fecha=?,hora=?,paciente_id=?,odontologo_id=? WHERE id=?");
            preparedStatement.setDate(1,Util.utilDateToSqlDate(turno.getFecha()));
            preparedStatement.setTime(2,Util.utilDateToSqlTime(turno.getHora()));
            preparedStatement.setInt(3,turno.getPaciente().getId());
            preparedStatement.setInt(4,turno.getOdontologo().getId());
            preparedStatement.setInt(5, turno.getIdTurno());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return turno;
    }
}
