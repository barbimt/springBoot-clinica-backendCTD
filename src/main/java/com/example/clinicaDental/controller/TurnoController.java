package com.example.clinicaDental.controller;

import com.example.clinicaDental.repository.impl.OdontologoDAOH2;
import com.example.clinicaDental.repository.impl.PacienteDAOH2;
import com.example.clinicaDental.repository.impl.TurnoDAOH2;
import com.example.clinicaDental.dominio.Odontologo;
import com.example.clinicaDental.dominio.Paciente;
import com.example.clinicaDental.dominio.Turno;
import com.example.clinicaDental.service.OdontologoService;
import com.example.clinicaDental.service.PacienteService;
import com.example.clinicaDental.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;


    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno){
        ResponseEntity<Turno> respuesta;
        //controlar si los id son existentes
        Paciente paciente = pacienteService.buscar(turno.getPaciente().getId());
        Odontologo odontologo = odontologoService.buscar(turno.getOdontologo().getId());
        //control
        if (paciente!=null&&odontologo!=null){
            respuesta = ResponseEntity.ok(turnoService.guardar(turno));
        }else {
            respuesta=ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return respuesta;
    }

    @GetMapping
    public ResponseEntity<List<Turno>>listarTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable int id){
        return ResponseEntity.ok(turnoService.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTurno(@PathVariable  int id){
        ResponseEntity response = null;
        if(turnoService.buscar(id) == null){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            turnoService.eliminar(id);
            response= new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }
    @PutMapping("/actualizar")
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno){
        ResponseEntity response = null;
        if(turnoService.buscar(turno.getIdTurno()) == null ){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else{
            response = new ResponseEntity(turnoService.actualizar(turno),HttpStatus.OK);
        }
        return response;

    }
}
