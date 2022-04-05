package com.example.clinicaDental.repository;

import java.util.List;

public interface IDao<T> {
    public T guardar(T t);
    public T buscar(int id);
    T actualizar(T t);
    public void eliminar(int id);
    public List<T> buscarTodos();

}