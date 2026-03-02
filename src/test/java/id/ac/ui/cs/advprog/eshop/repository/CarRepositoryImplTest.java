package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryImplTest {

    @InjectMocks
    CarRepositoryImpl carRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFindAll() {
        Car car = new Car();
        car.setCarName("Avanza");
        car.setCarColor("Black");
        car.setCarQuantity(2);
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertNotNull(savedCar.getCarId());
        assertEquals("Avanza", savedCar.getCarName());
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("car-123");
        car.setCarName("Xenia");
        carRepository.create(car);

        Car savedCar = carRepository.findById("car-123");
        assertNotNull(savedCar);
        assertEquals("car-123", savedCar.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car car = new Car();
        car.setCarId("car-123");
        carRepository.create(car);

        Car result = carRepository.findById("non-existent-id");
        assertNull(result);
    }

    @Test
    void testUpdatePositive() {
        Car car = new Car();
        car.setCarId("car-123");
        car.setCarName("Xenia");
        car.setCarColor("Red");
        car.setCarQuantity(1);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Xenia Facelift");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(5);

        Car result = carRepository.update("car-123", updatedCar);
        assertNotNull(result);
        assertEquals("Xenia Facelift", result.getCarName());
        assertEquals("Blue", result.getCarColor());
        assertEquals(5, result.getCarQuantity());
    }

    @Test
    void testUpdateNegative() {
        Car car = new Car();
        car.setCarId("car-123");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Ghost Car");

        Car result = carRepository.update("ghost-id", updatedCar);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("car-123");
        carRepository.create(car);

        carRepository.delete("car-123");

        Car result = carRepository.findById("car-123");
        assertNull(result);
    }
}