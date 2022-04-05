package com.example.clinicaDental.service;

import com.example.clinicaDental.repository.IDao;
import com.example.clinicaDental.dominio.Odontologo;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class OdontologoService {
    private IDao<Odontologo> odontologoIDao;
    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }
    public Odontologo guardar(Odontologo o){
        return odontologoIDao.guardar(o);
    }
    public Odontologo buscar( int id){
        return odontologoIDao.buscar(id);
    }
    public List<Odontologo> buscarTodos(){
        return odontologoIDao.buscarTodos();
    }
    public void eliminar(int id){
        odontologoIDao.eliminar(id);
    }
    public Odontologo actualizar(Odontologo o) {
        return odontologoIDao.actualizar(o);
    }
}
