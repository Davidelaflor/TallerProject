package com.example.taller.empleados.infrastructure.adapter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.empleados.application.EmpleadoRequestDTO;
import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.empleados.infrastructure.port.EmpleadoServicePort;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;

@Service
public class EmpleadoService implements EmpleadoServicePort {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public EmpleadoDTO crearEmpleado(EmpleadoRequestDTO empleadoDTO) {
        // Crear una nueva instancia de Empleado sin asignar un ID
        EmpleadoEntity empleadoEntity = new EmpleadoEntity();

        // Configurar los valores de la entidad usando el DTO
        empleadoEntity.setNombre(empleadoDTO.getNombre());
        empleadoEntity.setApellido(empleadoDTO.getApellido());
        empleadoEntity.setTelefono(empleadoDTO.getTelefono());

        // Guardar la entidad en la base de datos (esto genera y asigna el ID)
        empleadoEntity = empleadoRepository.save(empleadoEntity);

        // Crear y devolver el DTO de respuesta, incluyendo el ID generado
        return new EmpleadoDTO(
                empleadoEntity.getId(), // Aquí obtienes el ID generado
                empleadoEntity.getNombre(),
                empleadoEntity.getApellido(),
                empleadoEntity.getTelefono());
    }

    @Override
    public EmpleadoEntity obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
     @Override
    public Optional<EmpleadoEntity> findById(Long id) {
        return empleadoRepository.findById(id);
    }
    @Override
    public boolean existsById(Long empleadoId) {
        return empleadoRepository.existsById(empleadoId);
    }
}
