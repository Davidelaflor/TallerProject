package com.example.taller.empleados.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.empleados.application.EmpleadoDTO;
import com.example.taller.empleados.domain.EmpleadoResponseDTO;
import com.example.taller.empleados.infrastructure.port.EmpleadoServicePort;

@Service
public class EmpleadoService implements EmpleadoServicePort {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
	 public EmpleadoResponseDTO crearEmpleado(EmpleadoDTO empleado) {
		Empleado empleadoEntity = new Empleado(empleado);
		empleadoRepository.save(empleado);
		return new EmpleadoResponseDTO(empleadoEntity);
    }

		private EmpleadoDTO opearcionx(Empleado empleado) {
		}

    @Override
    public Empleado obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}
