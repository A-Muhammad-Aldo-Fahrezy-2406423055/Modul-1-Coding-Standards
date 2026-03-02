package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-123");
        car.setCarName("Avanza");
        car.setCarColor("Black");
        car.setCarQuantity(10);
    }

    @Test
    void testCreate() {
        when(carRepository.create(car)).thenReturn(car);
        Car createdCar = carService.create(car);
        assertEquals(car.getCarId(), createdCar.getCarId());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        when(carRepository.findAll()).thenReturn(carList.iterator());

        List<Car> result = carService.findAll();
        assertEquals(1, result.size());
        assertEquals(car.getCarId(), result.get(0).getCarId());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carRepository.findById("car-123")).thenReturn(car);
        Car foundCar = carService.findById("car-123");
        assertEquals(car.getCarId(), foundCar.getCarId());
        verify(carRepository, times(1)).findById("car-123");
    }

    @Test
    void testUpdate() {
        when(carRepository.update("car-123", car)).thenReturn(car);

        carService.update("car-123", car);

        verify(carRepository, times(1)).update("car-123", car);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete("car-123");
        carService.deleteCarById("car-123");
        verify(carRepository, times(1)).delete("car-123");
    }
}