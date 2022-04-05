package com.example.clinicaDental.service;

import com.example.clinicaDental.repository.IDao;
import com.example.clinicaDental.dominio.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PacienteService {

    private IDao<Paciente> pacienteIDao;
    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }
    public Paciente guardar(Paciente p){
        return pacienteIDao.guardar(p);
    }
    public Paciente buscar( int id){
        return pacienteIDao.buscar(id);
    }
    public List<Paciente> buscarTodos(){
        return pacienteIDao.buscarTodos();
    }
    public void eliminar(int id){
        pacienteIDao.eliminar(id);
    }
    public Paciente actualizar(Paciente p) {
        return pacienteIDao.actualizar(p);
    }
}