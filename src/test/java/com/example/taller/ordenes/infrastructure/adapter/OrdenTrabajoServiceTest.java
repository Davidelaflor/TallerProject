package com.example.taller.ordenes.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoRepository;
import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.infrastructure.adapter.VehiculoEntity;
import com.example.taller.vehiculos.infrastructure.port.VehiculoServicePort;

@ExtendWith(MockitoExtension.class)
class OrdenTrabajoServiceTest {

    @Mock
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Mock
    private VehiculoServicePort vehiculoServicePort;

    @Mock
    private EmpleadoRepository empleadoServicePort;

    @Mock
    private PropietarioServicePort propietarioServicePort;

    @InjectMocks
    private OrdenTrabajoService ordenTrabajoService;

    private OrdenTrabajoRequestDTO ordenTrabajoRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un DTO de ejemplo para crear una orden de trabajo
        ordenTrabajoRequestDTO = new OrdenTrabajoRequestDTO();
        ordenTrabajoRequestDTO.setVehiculoPatente("ABC123");
        ordenTrabajoRequestDTO.setEmpleadoId(1L);
        ordenTrabajoRequestDTO.setPropietarioDni("12345678");
        ordenTrabajoRequestDTO.setDetalleFalla("Falla en motor");
        ordenTrabajoRequestDTO.setHorasTrabajadas(5);
        ordenTrabajoRequestDTO.setFechaIngreso(LocalDate.parse("2024-11-18"));
    }

    @Test
    void testCrearOrdenTrabajo() {
        // Given
        VehiculoEntity vehiculo = new VehiculoEntity();
        vehiculo.setPatente("ABC123");

        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setId(1L);
        empleado.setNombre("Juan");

        PropietarioEntity propietario = new PropietarioEntity();
        propietario.setDni("12345678");
        propietario.setNombre("Carlos");

        // Mocking los servicios para devolver los valores esperados
        when(vehiculoServicePort.findByPatente(ordenTrabajoRequestDTO.getVehiculoPatente()))
                .thenReturn(Optional.of(vehiculo));
        when(empleadoServicePort.findById(ordenTrabajoRequestDTO.getEmpleadoId()))
                .thenReturn(Optional.of(empleado));
        when(propietarioServicePort.findByDni(ordenTrabajoRequestDTO.getPropietarioDni()))
                .thenReturn(Optional.of(propietario));
        when(ordenTrabajoRepository.save(any(OrdenTrabajoEntity.class)))
                .thenReturn(new OrdenTrabajoEntity()); // Asegúrate de que devuelve un objeto válido

        // Cuando se crea una nueva orden de trabajo
        OrdenTrabajoDTO ordenTrabajoDTO = ordenTrabajoService.crearOrdenTrabajo(ordenTrabajoRequestDTO);

        // Then
        assertNotNull(ordenTrabajoDTO);

        // Aquí accedes al vehículo a través del propietario
        assertNotNull(ordenTrabajoDTO.getPropietario()); // Primero, asegúrate de que el propietario no sea nulo
        assertNotNull(ordenTrabajoDTO.getPropietario().getVehiculos()); // Asegúrate de que la lista de vehículos no sea
                                                                        // nula
        assertEquals("ABC123", ordenTrabajoDTO.getPropietario().getVehiculos().get(0).getPatente());
        assertEquals(1L, ordenTrabajoDTO.getEmpleado().getId());
        assertEquals("Carlos", ordenTrabajoDTO.getPropietario().getNombre());
    }

    // @Test
    // void testFinalizarOrdenTrabajo() {
    // // Given
    // Long ordenId = 1L;
    // OrdenTrabajoEntity ordenTrabajoEntity = new OrdenTrabajoEntity();
    // ordenTrabajoEntity.setEstado("ACTIVO");

    // when(ordenTrabajoRepository.findById(ordenId))
    // .thenReturn(Optional.of(ordenTrabajoEntity));

    // // Cuando se finaliza la orden de trabajo
    // OrdenTrabajoDTO ordenTrabajoDTO =
    // ordenTrabajoService.finalizarOrdenTrabajo(ordenId);

    // // Then
    // assertNotNull(ordenTrabajoDTO);
    // assertEquals("finalizado", ordenTrabajoDTO.getEstado());
    // }

    // @Test
    // void testEliminarOrdenTrabajo() {
    // // Given
    // Long ordenId = 1L;

    // // Cuando se elimina la orden de trabajo
    // ordenTrabajoService.eliminarOrdenTrabajo(ordenId);

    // // Then
    // verify(ordenTrabajoRepository, times(1)).deleteById(ordenId);
    // }

    // @Test
    // void testObtenerOrdenTrabajo() {
    // // Given
    // Long ordenId = 1L;
    // OrdenTrabajoEntity ordenTrabajoEntity = new OrdenTrabajoEntity();

    // when(ordenTrabajoRepository.findById(ordenId))
    // .thenReturn(Optional.of(ordenTrabajoEntity));

    // // Cuando se obtiene una orden de trabajo por ID
    // OrdenTrabajoDTO ordenTrabajoDTO =
    // ordenTrabajoService.obtenerOrdenTrabajo(ordenId);

    // // Then
    // assertNotNull(ordenTrabajoDTO);
    // }

    // @Test
    // void testListarOrdenes() {
    // // Given
    // OrdenTrabajoEntity ordenTrabajoEntity = new OrdenTrabajoEntity();
    // when(ordenTrabajoRepository.findByEstado("ACTIVO"))
    // .thenReturn(List.of(ordenTrabajoEntity));

    // // Cuando se listan las ordenes
    // List<OrdenTrabajoDTO> ordenes = ordenTrabajoService.listarOrdenes();

    // // Then
    // assertNotNull(ordenes);
    // assertEquals(1, ordenes.size());
    // }

    // @Test
    // void testFindByPropietarioDniAndEstado() {
    // // Given
    // String dni = "12345678";
    // String estado = "ACTIVO";
    // OrdenTrabajoEntity ordenTrabajoEntity = new OrdenTrabajoEntity();

    // when(ordenTrabajoRepository.findByPropietarioDniAndEstado(dni, estado))
    // .thenReturn(List.of(ordenTrabajoEntity));

    // // Cuando se busca por propietario y estado
    // List<OrdenTrabajoDTO> ordenes =
    // ordenTrabajoService.findByPropietarioDniAndEstado(dni, estado);

    // // Then
    // assertNotNull(ordenes);
    // assertEquals(1, ordenes.size());
    // }

    // @Test
    // void testAgregarHorasAOrdenTrabajo() {
    // // Given
    // Long ordenId = 1L;
    // int horas = 5;
    // OrdenTrabajoEntity ordenTrabajoEntity = new OrdenTrabajoEntity();
    // ordenTrabajoEntity.setHorasTrabajadas(10);

    // when(ordenTrabajoRepository.findById(ordenId))
    // .thenReturn(Optional.of(ordenTrabajoEntity));

    // // Cuando se agregan horas a la orden de trabajo
    // OrdenTrabajoDTO ordenTrabajoDTO =
    // ordenTrabajoService.agregarHorasAOrdenTrabajo(ordenId, horas);

    // // Then
    // assertNotNull(ordenTrabajoDTO);
    // assertEquals(15, ordenTrabajoDTO.getHorasTrabajadas());
    // }
}
