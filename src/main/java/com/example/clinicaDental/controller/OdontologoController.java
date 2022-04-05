package com.example.clinicaDental.controller;

import com.example.clinicaDental.repository.impl.OdontologoDAOH2;
import com.example.clinicaDental.dominio.Odontologo;
import com.example.clinicaDental.dominio.Paciente;
import com.example.clinicaDental.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

@Autowired
private OdontologoService odontologoService;
            //= new OdontologoService(new OdontologoDAOH2());

    @GetMapping()
    public ResponseEntity<List<Odontologo>>buscarOdontologos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable int id){
        return ResponseEntity.ok(odontologoService.buscar(id));
    }
    @PostMapping()
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardar(odontologo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarOdontologo(@PathVariable  int id){
        ResponseEntity response = null;
        if(odontologoService.buscar(id) == null){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            odontologoService.eliminar(id);
            response= new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }
    @PutMapping()
    public ResponseEntity<Paciente> actualizar(@RequestBody Odontologo odontologo){
        ResponseEntity response = null;
        if(odontologoService.buscar(odontologo.getId()) == null ){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else{
            response = new ResponseEntity(odontologoService.actualizar(odontologo),HttpStatus.OK);
        }
        return response;

    }

}
