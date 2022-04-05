package com.example.clinicaDental.controller;

import com.example.clinicaDental.repository.impl.PacienteDAOH2;
import com.example.clinicaDental.dominio.Paciente;
import com.example.clinicaDental.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;
            //= new PacienteService(new PacienteDAOH2());

    //listar todos los pacientes
    @GetMapping()
    public ResponseEntity<List<Paciente>>buscarPacientes(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable int id){
        return ResponseEntity.ok(pacienteService.buscar(id));
    }

    @PostMapping()
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardar(paciente));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPaciente(@PathVariable  int id){
        ResponseEntity response = null;
        if(pacienteService.buscar(id) == null){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            pacienteService.eliminar(id);
            response= new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }

    //vamos a retornar un paciente en el body
    @PutMapping()
    public ResponseEntity<Paciente> actualizar(@RequestBody Paciente paciente){
        ResponseEntity response = null;
        if(pacienteService.buscar(paciente.getId()) == null ){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else{
            response = new ResponseEntity(pacienteService.actualizar(paciente),HttpStatus.OK);
        }
        return response;

    }
}


