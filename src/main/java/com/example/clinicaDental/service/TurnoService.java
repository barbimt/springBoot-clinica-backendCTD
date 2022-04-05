package com.example.clinicaDental.service;

import com.example.clinicaDental.repository.IDao;
import com.example.clinicaDental.dominio.Turno;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TurnoService {

    private IDao<Turno> turnoIDao;
    public TurnoService(IDao<Turno> turnoIDao) {
        this.turnoIDao = turnoIDao;
    }
    public Turno guardar(Turno t){
        return turnoIDao.guardar(t);
    }
    public Turno buscar(int id){
        return turnoIDao.buscar(id);
    }
    public List<Turno> buscarTodos(){
        return turnoIDao.buscarTodos();
    }
    public void eliminar(int id){
        turnoIDao.eliminar(id);
    }
    public Turno actualizar(Turno t) {
        return turnoIDao.actualizar(t);
    }
}
