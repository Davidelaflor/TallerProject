package com.example.taller.repuestos.infrastructure.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.repuestos.domain.RepuestoDTO;
import com.example.taller.repuestos.infrastructure.port.RepuestoServicePort;

@Service
public class RepuestoService implements RepuestoServicePort {
    @Autowired
    private RepuestoRepository repuestoRepository;

    @Override
    public List<RepuestoDTO> listarRepuestos() {
        List<RepuestoEntity> repuestoEntities = repuestoRepository.findAll();

        // Convierte cada RepuestoEntity a RepuestoDTO
        return repuestoEntities.stream()
                .map(this::convertToDTO) // Aplica el método de conversión a cada elemento
                .collect(Collectors.toList()); // Recolecta los resultados en una lista de RepuestoDTO
    }

    // Método de conversión de RepuestoEntity a RepuestoDTO
    private RepuestoDTO convertToDTO(RepuestoEntity entity) {
        return new RepuestoDTO(
                entity.getCodigoInventario(),
                entity.getNombre(),
                entity.getPrecio(),
                entity.getCantidad());
    }

    @Override
    public RepuestoDTO crearRepuesto(RepuestoDTO repuestoDTO) {
        // Convierte el DTO a Entity
        RepuestoEntity repuestoEntity = convertToEntity(repuestoDTO);

        // Guarda el Entity en el repositorio
        RepuestoEntity savedEntity = repuestoRepository.save(repuestoEntity);

        // Convierte el resultado a DTO y lo devuelve
        return convertToDTO(savedEntity);
    }

    // Método de conversión de RepuestoDTO a RepuestoEntity
    private RepuestoEntity convertToEntity(RepuestoDTO dto) {
        return new RepuestoEntity(
                dto.getCodigoInventario(),
                dto.getNombre(),
                dto.getPrecio(),
                dto.getCantidad());

    }

    @Override
    public RepuestoDTO obtenerRepuestoPorCodigo(String codigo) {
        return repuestoRepository.findById(codigo)
                .map(this::convertToDTO) // Convierte a DTO usando el método creado
                .orElse(null); // Retorna null si no se encuentra
    }

    @Override
    public void eliminarRepuesto(String codigo) {
        repuestoRepository.deleteById(codigo);
    }

}
