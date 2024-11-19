package com.example.taller.ordenes.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taller.empleados.domain.EmpleadoDTO;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoEntity;
import com.example.taller.empleados.infrastructure.adapter.EmpleadoRepository;
import com.example.taller.ordenes.application.OrdenTrabajoRequestDTO;
import com.example.taller.ordenes.domain.OrdenTrabajoDTO;
import com.example.taller.ordenes.utilities.OrdenTrabajoMapper;
import com.example.taller.propietarios.domain.PropietarioDTO;
import com.example.taller.propietarios.infrastructure.adapter.PropietarioEntity;
import com.example.taller.propietarios.infrastructure.port.PropietarioServicePort;
import com.example.taller.vehiculos.domain.VehiculoDTO;
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

    @Mock
    private OrdenTrabajoMapper ordenTrabajoMapper;

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
        ordenTrabajoRequestDTO.setPropietarioDni("12345678A");
        ordenTrabajoRequestDTO.setDetalleFalla("Falla en motor");
        ordenTrabajoRequestDTO.setHorasTrabajadas(5);
        ordenTrabajoRequestDTO.setFechaIngreso(LocalDate.parse("2024-11-18"));
    }

  @Test
void testCrearOrdenTrabajoSuccess() {
    // Datos de entrada para la prueba
    Long empleadoId = 1L;
    String propietarioDni = "12345678A";
    String vehiculoPatente = "ABC123";
    String detalleFalla = "Fallo en el motor";
    int horasTrabajadas = 5;
    LocalDate fechaIngreso = LocalDate.parse("2024-11-19");

    // Mock de los servicios involucrados
    VehiculoDTO mockVehiculoDTO = new VehiculoDTO();
    mockVehiculoDTO.setPatente(vehiculoPatente);
    VehiculoEntity mockVehiculo = new VehiculoEntity();
    mockVehiculo.setPatente(vehiculoPatente);

    EmpleadoDTO mockEmpleadoDTO = new EmpleadoDTO();
    mockEmpleadoDTO.setId(empleadoId);
    EmpleadoEntity mockEmpleado = new EmpleadoEntity();
    mockEmpleado.setId(empleadoId);

    PropietarioDTO mockPropietarioDTO = new PropietarioDTO();
    mockPropietarioDTO.setDni(propietarioDni);
    PropietarioEntity mockPropietario = new PropietarioEntity();
    mockPropietario.setDni(propietarioDni);

    // DTO de la orden de trabajo que se pasará como entrada
    OrdenTrabajoRequestDTO mockRequestDTO = new OrdenTrabajoRequestDTO();
    mockRequestDTO.setDetalleFalla(detalleFalla);
    mockRequestDTO.setHorasTrabajadas(horasTrabajadas);
    mockRequestDTO.setFechaIngreso(fechaIngreso);
    mockRequestDTO.setVehiculoPatente(vehiculoPatente);
    mockRequestDTO.setEmpleadoId(empleadoId);
    mockRequestDTO.setPropietarioDni(propietarioDni);

    // Mock de la entidad de la orden de trabajo que se va a crear
    OrdenTrabajoEntity mockOrdenEntity = new OrdenTrabajoEntity();
    mockOrdenEntity.setDetalleFalla(detalleFalla);
    mockOrdenEntity.setHorasTrabajadas(horasTrabajadas);
    mockOrdenEntity.setFechaIngreso(fechaIngreso);
    mockOrdenEntity.setEstado("ACTIVO");
    mockOrdenEntity.setVehiculo(mockVehiculo);
    mockOrdenEntity.setEmpleado(mockEmpleado);
    mockOrdenEntity.setPropietario(mockPropietario);

    // Mock de la entidad de la orden de trabajo guardada
    OrdenTrabajoEntity savedOrdenEntity = new OrdenTrabajoEntity();
    savedOrdenEntity.setId(1L); // Suponemos que el ID es 1
    savedOrdenEntity.setDetalleFalla(detalleFalla);
    savedOrdenEntity.setHorasTrabajadas(horasTrabajadas);
    savedOrdenEntity.setFechaIngreso(fechaIngreso);
    savedOrdenEntity.setEstado("ACTIVO");
    savedOrdenEntity.setVehiculo(mockVehiculo);
    savedOrdenEntity.setEmpleado(mockEmpleado);
    savedOrdenEntity.setPropietario(mockPropietario);

    // DTO esperado después de la creación de la orden de trabajo
    OrdenTrabajoDTO expectedDTO = new OrdenTrabajoDTO();
    expectedDTO.setId(1L);
    expectedDTO.setDetalleFalla(detalleFalla);
    expectedDTO.setHorasTrabajadas(horasTrabajadas);
    expectedDTO.setFechaIngreso(fechaIngreso);
    expectedDTO.setEstado("ACTIVO");
    expectedDTO.setVehiculo(mockVehiculoDTO);
    expectedDTO.setEmpleado(mockEmpleadoDTO);
    expectedDTO.setPropietario(mockPropietarioDTO);

    // Configuración de los mocks
    when(vehiculoServicePort.findByPatente(vehiculoPatente)).thenReturn(Optional.of(mockVehiculo));
    when(empleadoServicePort.findById(empleadoId)).thenReturn(Optional.of(mockEmpleado));
    when(propietarioServicePort.findByDni(propietarioDni)).thenReturn(Optional.of(mockPropietario));
    when(ordenTrabajoRepository.save(any(OrdenTrabajoEntity.class))).thenReturn(savedOrdenEntity);

    // Act
    OrdenTrabajoDTO result = ordenTrabajoService.crearOrdenTrabajo(mockRequestDTO);

    // Assert
    assertNotNull(result, "El resultado no debe ser nulo");
    assertEquals(1L, result.getId(), "El ID de la orden debe ser 1");
    assertEquals(detalleFalla, result.getDetalleFalla(), "El detalle de la falla debe coincidir");
    assertEquals(horasTrabajadas, result.getHorasTrabajadas(), "Las horas trabajadas deben coincidir");
    assertEquals(fechaIngreso, result.getFechaIngreso(), "La fecha de ingreso debe coincidir");
    assertEquals("ACTIVO", result.getEstado(), "El estado de la orden debe ser 'ACTIVO'");
    assertEquals(mockVehiculoDTO, result.getVehiculo(), "El vehículo debe coincidir");
    assertEquals(mockEmpleadoDTO, result.getEmpleado(), "El empleado debe coincidir");
    assertEquals(mockPropietarioDTO, result.getPropietario(), "El propietario debe coincidir");

    // Verificación de las interacciones
    verify(vehiculoServicePort, times(1)).findByPatente(vehiculoPatente);
    verify(empleadoServicePort, times(1)).findById(empleadoId);
    verify(propietarioServicePort, times(1)).findByDni(propietarioDni);
    verify(ordenTrabajoRepository, times(1)).save(any(OrdenTrabajoEntity.class));
}

    @Test
    void testFinalizarOrdenTrabajoSuccess() {
        Long ordenTrabajoId = 1L;
    
        // Mock del propietario
        PropietarioDTO mockPropietarioDTO = new PropietarioDTO();
        mockPropietarioDTO.setDni("12345678A");
        mockPropietarioDTO.setNombre("Juan Perez");
    
        // Mock del empleado
        EmpleadoDTO mockEmpleadoDTO = new EmpleadoDTO();
        mockEmpleadoDTO.setId(6L);
        mockEmpleadoDTO.setNombre("Carlos Sanchez");
    
        // Mock de la orden de trabajo
        OrdenTrabajoEntity mockOrden = new OrdenTrabajoEntity();
        mockOrden.setId(ordenTrabajoId);
        mockOrden.setEstado("ACTIVO");  // Estado inicial "ACTIVO"
        mockOrden.setHorasTrabajadas(10);  // Pueden ser horas previas
        mockOrden.setPropietario(new PropietarioEntity());
        mockOrden.setEmpleado(new EmpleadoEntity());
        
        // Orden finalizada (estado cambiado a "finalizado")
        OrdenTrabajoEntity updatedOrden = new OrdenTrabajoEntity();
        updatedOrden.setId(ordenTrabajoId);
        updatedOrden.setEstado("finalizado");
        updatedOrden.setHorasTrabajadas(10);  // Se mantiene el mismo número de horas
        updatedOrden.setPropietario(new PropietarioEntity());
        updatedOrden.setEmpleado(new EmpleadoEntity());
    
        // Mock del DTO esperado
        OrdenTrabajoDTO mockDTO = new OrdenTrabajoDTO();
        mockDTO.setId(ordenTrabajoId);
        mockDTO.setEstado("finalizado");
        mockDTO.setPropietario(mockPropietarioDTO);  // Se asigna el propietario
        mockDTO.setEmpleado(mockEmpleadoDTO);  // Se asigna el empleado
    
        // Configuración de los mocks
        when(ordenTrabajoRepository.findById(ordenTrabajoId)).thenReturn(Optional.of(mockOrden));
        when(ordenTrabajoRepository.save(any())).thenReturn(updatedOrden);
        
        // Act
        OrdenTrabajoDTO result = ordenTrabajoService.finalizarOrdenTrabajo(ordenTrabajoId);
    
        // Assert
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(ordenTrabajoId, result.getId(), "El ID de la orden debe coincidir");
        assertEquals("finalizado", result.getEstado(), "El estado de la orden debe ser 'finalizado'");
    
        // Verificación de las interacciones
        verify(ordenTrabajoRepository, times(1)).findById(ordenTrabajoId);
        verify(ordenTrabajoRepository, times(1)).save(any(OrdenTrabajoEntity.class));
    }

    @Test
    void testFinalizarOrdenTrabajoNotFound() {
        // Arrange
        Long ordenTrabajoId = 1L;

        // Configuración del mock del repositorio para devolver un Optional vacío
        // (simula que no se encuentra la orden)
        when(ordenTrabajoRepository.findById(ordenTrabajoId)).thenReturn(Optional.empty());

        // Act & Assert
        // Verificamos que se lanza la excepción esperada cuando no se encuentra la
        // orden de trabajo
        assertThrows(RuntimeException.class, () -> {
            ordenTrabajoService.finalizarOrdenTrabajo(ordenTrabajoId);
        }, "Debe lanzar una RuntimeException si la orden no se encuentra");

        // Verificación del repositorio
        verify(ordenTrabajoRepository, times(1)).findById(ordenTrabajoId);
        verify(ordenTrabajoRepository, times(0)).save(any(OrdenTrabajoEntity.class)); // No debe llamar a save si no se
                                                                                      // encuentra la orden
    }

    @Test
    void testObtenerOrdenTrabajoSuccess() {
        // Arrange
        Long ordenTrabajoId = 1L;

        // Mock del Propietario
        PropietarioEntity mockPropietarioEntity = new PropietarioEntity();
        mockPropietarioEntity.setDni("12345678A");
        mockPropietarioEntity.setNombre("Juan Perez");

        // Mock del Empleado
        EmpleadoEntity mockEmpleadoEntity = new EmpleadoEntity();
        mockEmpleadoEntity.setId(6L);
        mockEmpleadoEntity.setNombre("Carlos Sanchez");

        // Mock de la OrdenTrabajoEntity
        OrdenTrabajoEntity mockOrden = new OrdenTrabajoEntity();
        mockOrden.setId(ordenTrabajoId);
        mockOrden.setHorasTrabajadas(10);
        mockOrden.setEstado("ACTIVO");
        mockOrden.setPropietario(mockPropietarioEntity);
        mockOrden.setEmpleado(mockEmpleadoEntity);

        // Configuración del mock del repositorio
        when(ordenTrabajoRepository.findById(ordenTrabajoId)).thenReturn(Optional.of(mockOrden));

        // Act
        OrdenTrabajoDTO result = ordenTrabajoService.obtenerOrdenTrabajo(ordenTrabajoId);

        // Assert
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(ordenTrabajoId, result.getId(), "El ID de la orden debe coincidir");
        assertEquals(10, result.getHorasTrabajadas(), "Las horas trabajadas deben ser correctas");
        assertEquals("ACTIVO", result.getEstado(), "El estado debe ser 'ACTIVO'");
        assertEquals("12345678A", result.getPropietario().getDni(), "El DNI del propietario debe coincidir");

        // Verificación del repositorio
        verify(ordenTrabajoRepository, times(1)).findById(ordenTrabajoId);
    }

    @Test
    void testObtenerOrdenTrabajoNotFound() {
        // Arrange
        Long ordenTrabajoId = 1L;

        // Configuración del mock del repositorio (no encuentra la orden de trabajo)
        when(ordenTrabajoRepository.findById(ordenTrabajoId)).thenReturn(Optional.empty());

        // Act
        OrdenTrabajoDTO result = ordenTrabajoService.obtenerOrdenTrabajo(ordenTrabajoId);

        // Assert
        assertNull(result, "El resultado debe ser nulo cuando no se encuentra la orden de trabajo");

        // Verificación del repositorio
        verify(ordenTrabajoRepository, times(1)).findById(ordenTrabajoId);
    }

    @Test
    void testListarOrdenesSuccess() {
        // Arrange
        String estado = "ACTIVO";

        // Mock de Propietario
        PropietarioEntity mockPropietarioEntity = new PropietarioEntity();
        mockPropietarioEntity.setDni("12345678A");
        mockPropietarioEntity.setNombre("Juan Perez");

        // Mock de Empleado
        EmpleadoEntity mockEmpleadoEntity = new EmpleadoEntity();
        mockEmpleadoEntity.setId(6L);
        mockEmpleadoEntity.setNombre("Carlos Sanchez");

        // Mock de OrdenTrabajoEntity
        OrdenTrabajoEntity mockOrden1 = new OrdenTrabajoEntity();
        mockOrden1.setId(1L);
        mockOrden1.setHorasTrabajadas(10);
        mockOrden1.setEstado(estado);
        mockOrden1.setPropietario(mockPropietarioEntity);
        mockOrden1.setEmpleado(mockEmpleadoEntity);

        OrdenTrabajoEntity mockOrden2 = new OrdenTrabajoEntity();
        mockOrden2.setId(2L);
        mockOrden2.setHorasTrabajadas(15);
        mockOrden2.setEstado(estado);
        mockOrden2.setPropietario(mockPropietarioEntity);
        mockOrden2.setEmpleado(mockEmpleadoEntity);

        List<OrdenTrabajoEntity> mockOrdenes = List.of(mockOrden1, mockOrden2);

        // Configuración del mock del repositorio
        when(ordenTrabajoRepository.findByEstado(estado)).thenReturn(mockOrdenes);

        // Act
        List<OrdenTrabajoDTO> result = ordenTrabajoService.listarOrdenes();

        // Assert
        assertNotNull(result, "La lista de resultados no debe ser nula");
        assertEquals(2, result.size(), "Debe haber dos órdenes de trabajo en la lista");

        // Verificamos que los DTOs de las órdenes tienen los valores correctos
        OrdenTrabajoDTO resultOrden1 = result.get(0);
        assertEquals(1L, resultOrden1.getId(), "El ID de la primera orden debe ser 1");
        assertEquals(10, resultOrden1.getHorasTrabajadas(), "Las horas trabajadas de la primera orden deben ser 10");
        assertEquals(estado, resultOrden1.getEstado(), "El estado de la primera orden debe ser 'ACTIVO'");
        assertEquals("12345678A", resultOrden1.getPropietario().getDni(),
                "El DNI del propietario de la primera orden debe coincidir");

        OrdenTrabajoDTO resultOrden2 = result.get(1);
        assertEquals(2L, resultOrden2.getId(), "El ID de la segunda orden debe ser 2");
        assertEquals(15, resultOrden2.getHorasTrabajadas(), "Las horas trabajadas de la segunda orden deben ser 15");
        assertEquals(estado, resultOrden2.getEstado(), "El estado de la segunda orden debe ser 'ACTIVO'");
        assertEquals("12345678A", resultOrden2.getPropietario().getDni(),
                "El DNI del propietario de la segunda orden debe coincidir");

        // Verificamos que el repositorio fue llamado correctamente
        verify(ordenTrabajoRepository, times(1)).findByEstado(estado);
    }

    @Test
    void testListarOrdenesNoFound() {
        // Arrange
        String estado = "ACTIVO";

        // Configuración del mock del repositorio
        when(ordenTrabajoRepository.findByEstado(estado)).thenReturn(List.of());

        // Act
        List<OrdenTrabajoDTO> result = ordenTrabajoService.listarOrdenes();

        // Assert
        assertNotNull(result, "La lista de resultados no debe ser nula");
        assertTrue(result.isEmpty(), "La lista de resultados debe estar vacía cuando no se encuentran órdenes");

        // Verificamos que el repositorio fue llamado correctamente
        verify(ordenTrabajoRepository, times(1)).findByEstado(estado);
        // lista está vacía
    }

    @Test
    void testFindByPropietarioDniAndEstadoSuccess() {
        // Arrange
        String dni = "12345678A";
        String estado = "Finalizada";

        // Mock de Propietario
        PropietarioEntity mockPropietarioEntity = new PropietarioEntity();
        mockPropietarioEntity.setDni(dni);
        mockPropietarioEntity.setNombre("Juan Perez");

        // Mock de Empleado
        EmpleadoEntity mockEmpleadoEntity = new EmpleadoEntity();
        mockEmpleadoEntity.setId(6L);
        mockEmpleadoEntity.setNombre("Carlos Sanchez");

        // Mock de OrdenTrabajoEntity
        OrdenTrabajoEntity mockOrden1 = new OrdenTrabajoEntity();
        mockOrden1.setId(1L);
        mockOrden1.setHorasTrabajadas(10);
        mockOrden1.setEstado(estado);
        mockOrden1.setPropietario(mockPropietarioEntity);
        mockOrden1.setEmpleado(mockEmpleadoEntity);

        OrdenTrabajoEntity mockOrden2 = new OrdenTrabajoEntity();
        mockOrden2.setId(2L);
        mockOrden2.setHorasTrabajadas(15);
        mockOrden2.setEstado(estado);
        mockOrden2.setPropietario(mockPropietarioEntity);
        mockOrden2.setEmpleado(mockEmpleadoEntity);

        List<OrdenTrabajoEntity> mockOrdenes = List.of(mockOrden1, mockOrden2);

        // Configuración del mock del repositorio
        when(ordenTrabajoRepository.findByPropietarioDniAndEstado(dni, estado)).thenReturn(mockOrdenes);

        // Act
        List<OrdenTrabajoDTO> result = ordenTrabajoService.findByPropietarioDniAndEstado(dni, estado);

        // Assert
        assertNotNull(result, "La lista de resultados no debe ser nula");
        assertEquals(2, result.size(), "Debe haber dos órdenes de trabajo en la lista");

        // Verificamos que los DTOs de las órdenes tienen los valores correctos
        OrdenTrabajoDTO resultOrden1 = result.get(0);
        assertEquals(1L, resultOrden1.getId(), "El ID de la primera orden debe ser 1");
        assertEquals(10, resultOrden1.getHorasTrabajadas(), "Las horas trabajadas de la primera orden deben ser 10");
        assertEquals(dni, resultOrden1.getPropietario().getDni(),
                "El DNI del propietario de la primera orden debe coincidir");

        OrdenTrabajoDTO resultOrden2 = result.get(1);
        assertEquals(2L, resultOrden2.getId(), "El ID de la segunda orden debe ser 2");
        assertEquals(15, resultOrden2.getHorasTrabajadas(), "Las horas trabajadas de la segunda orden deben ser 15");
        assertEquals(dni, resultOrden2.getPropietario().getDni(),
                "El DNI del propietario de la segunda orden debe coincidir");

        // Verificamos que el repositorio fue llamado correctamente
        verify(ordenTrabajoRepository, times(1)).findByPropietarioDniAndEstado(dni, estado);
    }

    @Test
    void testFindByPropietarioDniAndEstadoNotFound() {
        // Arrange
        String dni = "12345678A";
        String estado = "Finalizada";

        // Configuración del mock del repositorio
        when(ordenTrabajoRepository.findByPropietarioDniAndEstado(dni, estado)).thenReturn(List.of());

        // Act
        List<OrdenTrabajoDTO> result = ordenTrabajoService.findByPropietarioDniAndEstado(dni, estado);

        // Assert
        assertNotNull(result, "La lista de resultados no debe ser nula");
        assertTrue(result.isEmpty(), "La lista de resultados debe estar vacía cuando no se encuentran órdenes");

        // Verificamos que el repositorio fue llamado correctamente
        verify(ordenTrabajoRepository, times(1)).findByPropietarioDniAndEstado(dni, estado);
    }

    @Test
    void testAgregarHorasAOrdenTrabajoSuccess() {
        Long ordenTrabajoId = 1L;
        int horasAdicionales = 5;

        PropietarioDTO mockPropietarioDTO = new PropietarioDTO();
        mockPropietarioDTO.setDni("12345678A");
        mockPropietarioDTO.setNombre("Juan Perez");
        EmpleadoDTO mockEmpleadoDTO = new EmpleadoDTO();
        mockEmpleadoDTO.setId(6L);
        mockEmpleadoDTO.setNombre("Carlos Sanchez");

        OrdenTrabajoEntity mockOrden = new OrdenTrabajoEntity();
        mockOrden.setId(ordenTrabajoId);
        mockOrden.setHorasTrabajadas(10);
        mockOrden.setPropietario(new PropietarioEntity());
        mockOrden.setEmpleado(new EmpleadoEntity());
        OrdenTrabajoEntity updatedOrden = new OrdenTrabajoEntity();
        updatedOrden.setId(ordenTrabajoId);
        updatedOrden.setHorasTrabajadas(15);
        updatedOrden.setPropietario(new PropietarioEntity());
        updatedOrden.setEmpleado(new EmpleadoEntity());

        OrdenTrabajoDTO mockDTO = new OrdenTrabajoDTO();
        mockDTO.setId(ordenTrabajoId);
        mockDTO.setHorasTrabajadas(15);
        mockDTO.setPropietario(mockPropietarioDTO);
        mockDTO.setEmpleado(mockEmpleadoDTO);

        when(ordenTrabajoRepository.findById(ordenTrabajoId)).thenReturn(Optional.of(mockOrden));
        when(ordenTrabajoRepository.save(any())).thenReturn(updatedOrden);

        OrdenTrabajoMapper.toDTO(updatedOrden);

        OrdenTrabajoDTO result = ordenTrabajoService.agregarHorasAOrdenTrabajo(ordenTrabajoId, horasAdicionales);

        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(ordenTrabajoId, result.getId(), "El ID de la orden debe coincidir");
        assertEquals(15, result.getHorasTrabajadas(), "Las horas trabajadas deben ser actualizadas correctamente");

        verify(ordenTrabajoRepository, times(1)).findById(ordenTrabajoId);
        verify(ordenTrabajoRepository, times(1)).save(any(OrdenTrabajoEntity.class));
    }

    @Test
    void testAgregarHorasAOrdenTrabajoNotFound() {
        Long ordenTrabajoId = 2L;
        int horasAdicionales = 5;

        when(ordenTrabajoRepository.findById(ordenTrabajoId)).thenReturn(Optional.empty());

        OrdenTrabajoDTO result = ordenTrabajoService.agregarHorasAOrdenTrabajo(ordenTrabajoId, horasAdicionales);

        assertNull(result, "El resultado debe ser nulo cuando la orden de trabajo no existe");

        verify(ordenTrabajoRepository, times(1)).findById(ordenTrabajoId);
        verify(ordenTrabajoRepository, times(0)).save(any(OrdenTrabajoEntity.class));
    }

    @Test
    void testFindByIdFound() {
        Long id = 1L;
        OrdenTrabajoEntity ordenTrabajo = new OrdenTrabajoEntity();
        ordenTrabajo.setId(id);
        ordenTrabajo.setDetalleFalla("Falla en el motor");
        when(ordenTrabajoRepository.findById(id)).thenReturn(Optional.of(ordenTrabajo));
        Optional<OrdenTrabajoEntity> result = ordenTrabajoService.findById(id);
        assertTrue(result.isPresent(), "El resultado debe contener un valor");
        assertEquals(id, result.get().getId(), "El ID del resultado debe coincidir con el ID buscado");
        assertEquals("Falla en el motor", result.get().getDetalleFalla(), "El detalle de la falla debe coincidir");
        verify(ordenTrabajoRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 2L;
        when(ordenTrabajoRepository.findById(id)).thenReturn(Optional.empty());
        Optional<OrdenTrabajoEntity> result = ordenTrabajoService.findById(id);
        assertFalse(result.isPresent(), "El resultado no debe contener un valor");
        verify(ordenTrabajoRepository, times(1)).findById(id);
    }

    @Test
    void testSave() {
        OrdenTrabajoEntity ordenTrabajoMock = new OrdenTrabajoEntity();
        ordenTrabajoMock.setId(1L);
        ordenTrabajoMock.setDetalleFalla("Motor no arranca");
        OrdenTrabajoEntity savedOrdenTrabajoMock = new OrdenTrabajoEntity();
        savedOrdenTrabajoMock.setId(1L);
        savedOrdenTrabajoMock.setDetalleFalla("Motor no arranca");
        when(ordenTrabajoRepository.save(ordenTrabajoMock)).thenReturn(savedOrdenTrabajoMock);
        OrdenTrabajoEntity result = ordenTrabajoService.save(ordenTrabajoMock);
        assertNotNull(result);
        assertEquals(ordenTrabajoMock.getDetalleFalla(), result.getDetalleFalla());
        verify(ordenTrabajoRepository, times(1)).save(ordenTrabajoMock);
    }

    @Test
    void testEliminarOrdenTrabajo() {
        Long ordenId = 1L;
        ordenTrabajoService.eliminarOrdenTrabajo(ordenId);
        verify(ordenTrabajoRepository, times(1)).deleteById(ordenId);
    }

    @Test
    void testExisteOrdenTrabajoPorId() {
        Long id = 1L;
        when(ordenTrabajoRepository.existsById(id)).thenReturn(true);
        boolean exists = ordenTrabajoService.existeOrdenTrabajoPorId(id);
        assertTrue(exists, "El método debe devolver true cuando el ID existe");
        verify(ordenTrabajoRepository, times(1)).existsById(id);
    }

    @Test
    void testExisteOrdenTrabajoPorIdFalse() {
        Long id = 2L;
        when(ordenTrabajoRepository.existsById(id)).thenReturn(false);
        boolean exists = ordenTrabajoService.existeOrdenTrabajoPorId(id);
        assertFalse(exists, "El método debe devolver false cuando el ID no existe");
        verify(ordenTrabajoRepository, times(1)).existsById(id);
    }

    @Test
    void testExisteOrdenTrabajoPorVehiculo() {
        String patente = "ABC123";
        when(ordenTrabajoRepository.existsByVehiculo_Patente(patente)).thenReturn(true);
        boolean exists = ordenTrabajoService.existeOrdenTrabajoPorVehiculo(patente);
        assertTrue(exists, "El método debe devolver true cuando existe una orden de trabajo para la patente");
        verify(ordenTrabajoRepository, times(1)).existsByVehiculo_Patente(patente);
    }

    @Test
    void testExisteOrdenTrabajoPorVehiculoFalse() {
        String patente = "XYZ789";
        when(ordenTrabajoRepository.existsByVehiculo_Patente(patente)).thenReturn(false);
        boolean exists = ordenTrabajoService.existeOrdenTrabajoPorVehiculo(patente);
        assertFalse(exists, "El método debe devolver false cuando no existe una orden de trabajo para la patente");
        verify(ordenTrabajoRepository, times(1)).existsByVehiculo_Patente(patente);
    }

    @Test
    void testFindByVehiculoPatente() {
        String patente = "ABC123";
        OrdenTrabajoEntity orden1 = new OrdenTrabajoEntity();
        orden1.setId(1L);
        orden1.setDetalleFalla("Falla en el motor");
        OrdenTrabajoEntity orden2 = new OrdenTrabajoEntity();
        orden2.setId(2L);
        orden2.setDetalleFalla("Revisión de frenos");
        List<OrdenTrabajoEntity> mockOrdenes = Arrays.asList(orden1, orden2);
        when(ordenTrabajoRepository.findByVehiculoPatente(patente)).thenReturn(mockOrdenes);
        List<OrdenTrabajoEntity> result = ordenTrabajoService.findByVehiculoPatente(patente);
        assertNotNull(result, "La lista de órdenes no debe ser nula");
        assertEquals(2, result.size(), "Debe devolver dos órdenes de trabajo");
        assertEquals(orden1.getDetalleFalla(), result.get(0).getDetalleFalla());
        assertEquals(orden2.getDetalleFalla(), result.get(1).getDetalleFalla());
        verify(ordenTrabajoRepository, times(1)).findByVehiculoPatente(patente);
    }

    @Test
    void testFindByVehiculoPatenteEmpty() {
        String patente = "XYZ789";
        when(ordenTrabajoRepository.findByVehiculoPatente(patente)).thenReturn(List.of());
        List<OrdenTrabajoEntity> result = ordenTrabajoService.findByVehiculoPatente(patente);
        assertNotNull(result, "La lista de órdenes no debe ser nula");
        assertTrue(result.isEmpty(), "La lista de órdenes debe estar vacía");
        verify(ordenTrabajoRepository, times(1)).findByVehiculoPatente(patente);
    }

}
