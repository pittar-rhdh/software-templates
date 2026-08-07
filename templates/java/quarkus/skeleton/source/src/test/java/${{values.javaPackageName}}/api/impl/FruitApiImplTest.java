package ${{values.groupId}}.${{values.artifactId}}.api.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.TestTransaction;

import ${{values.groupId}}.${{values.artifactId}}.api.model.Fruit;
import ${{values.groupId}}.${{values.artifactId}}.api.model.FruitInput;
import ${{values.groupId}}.${{values.artifactId}}.domain.FruitEntity;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FruitApiImplTest {

    private FruitApiImpl fruitApi;

    @BeforeEach
    public void setUp() {
        fruitApi = new FruitApiImpl();
    }

    @Test
    @TestTransaction
    public void testCreateFruitSuccess() {
        FruitInput fruitInput = new FruitInput();
        fruitInput.setName("Apple");
        fruitInput.setColour("Red");

        Fruit result = fruitApi.createFruit(fruitInput);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Apple", result.getName());
        assertEquals("Red", result.getColour());
    }

    @Test
    @TestTransaction
    public void testCreateFruitWithNullName() {
        FruitInput fruitInput = new FruitInput();
        fruitInput.setName(null);
        fruitInput.setColour("Red");

        // This should still work in basic terms, but would fail validation in real scenario
        Fruit result = fruitApi.createFruit(fruitInput);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNull(result.getName());
        assertEquals("Red", result.getColour());
    }

    @Test
    @TestTransaction
    public void testGetFruitByIdSuccess() {
        // Create a fruit first
        FruitInput fruitInput = new FruitInput();
        fruitInput.setName("Banana");
        fruitInput.setColour("Yellow");
        Fruit created = fruitApi.createFruit(fruitInput);

        // Retrieve it
        Fruit retrieved = fruitApi.getFruitById(created.getId());

        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("Banana", retrieved.getName());
        assertEquals("Yellow", retrieved.getColour());
    }

    @Test
    @TestTransaction
    public void testGetFruitByIdNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            fruitApi.getFruitById(999L);
        });

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Fruit not found with id: 999"));
    }

    @Test
    @TestTransaction
    public void testGetFruitsSuccess() {
        // Clear existing data and create specific test data
        FruitEntity.deleteAll();

        // Create multiple fruits
        FruitInput fruit1 = new FruitInput();
        fruit1.setName("Orange");
        fruit1.setColour("Orange");

        FruitInput fruit2 = new FruitInput();
        fruit2.setName("Grape");
        fruit2.setColour("Purple");

        fruitApi.createFruit(fruit1);
        fruitApi.createFruit(fruit2);

        List<Fruit> fruits = fruitApi.getFruits();

        assertNotNull(fruits);
        assertTrue(fruits.size() >= 2);

        // Check that our created fruits are present
        boolean foundOrange = fruits.stream().anyMatch(f -> "Orange".equals(f.getName()));
        boolean foundGrape = fruits.stream().anyMatch(f -> "Grape".equals(f.getName()));

        assertTrue(foundOrange, "Orange fruit should be found");
        assertTrue(foundGrape, "Grape fruit should be found");
    }

    @Test
    @TestTransaction
    public void testGetFruitsEmptyList() {
        // Clear all data
        FruitEntity.deleteAll();

        List<Fruit> fruits = fruitApi.getFruits();

        assertNotNull(fruits);
        assertEquals(0, fruits.size());
    }

    @Test
    @TestTransaction
    public void testUpdateFruitSuccess() {
        // Create a fruit first
        FruitInput originalInput = new FruitInput();
        originalInput.setName("Strawberry");
        originalInput.setColour("Red");
        Fruit created = fruitApi.createFruit(originalInput);

        // Update it
        FruitInput updateInput = new FruitInput();
        updateInput.setName("Strawberry");
        updateInput.setColour("Pink");

        Fruit updated = fruitApi.updateFruit(created.getId(), updateInput);

        assertNotNull(updated);
        assertEquals(created.getId(), updated.getId());
        assertEquals("Strawberry", updated.getName());
        assertEquals("Pink", updated.getColour());
    }

    @Test
    public void testUpdateFruitNotFound() {
        FruitInput updateInput = new FruitInput();
        updateInput.setName("Mango");
        updateInput.setColour("Yellow");

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            fruitApi.updateFruit(999L, updateInput);
        });

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Fruit not found with id: 999"));
    }

    @Test
    @TestTransaction
    public void testDeleteFruitSuccess() {
        // Create a fruit first
        FruitInput fruitInput = new FruitInput();
        fruitInput.setName("Pear");
        fruitInput.setColour("Green");
        Fruit created = fruitApi.createFruit(fruitInput);

        // Delete it
        Response response = fruitApi.deleteFruit(created.getId());

        assertNotNull(response);
        assertEquals(204, response.getStatus());

        // Verify it's deleted
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            fruitApi.getFruitById(created.getId());
        });

        assertNotNull(exception);
    }

    @Test
    @TestTransaction
    public void testDeleteFruitNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            fruitApi.deleteFruit(999L);
        });

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Fruit not found with id: 999"));
    }

    @Test
    @TestTransaction
    public void testCreateAndRetrieveMultipleFruits() {
        // Create several fruits
        FruitInput[] fruits = {
            createFruitInput("Apple", "Red"),
            createFruitInput("Banana", "Yellow"),
            createFruitInput("Cherry", "Red"),
            createFruitInput("Grape", "Purple")
        };

        // Create all fruits
        for (FruitInput fruitInput : fruits) {
            Fruit created = fruitApi.createFruit(fruitInput);
            assertNotNull(created);
            assertNotNull(created.getId());
        }

        // Retrieve all
        List<Fruit> allFruits = fruitApi.getFruits();

        assertNotNull(allFruits);
        assertTrue(allFruits.size() >= 4);

        // Verify specific fruits exist
        for (FruitInput fruitInput : fruits) {
            boolean found = allFruits.stream()
                .anyMatch(f -> fruitInput.getName().equals(f.getName()) &&
                              fruitInput.getColour().equals(f.getColour()));
            assertTrue(found, "Should find fruit: " + fruitInput.getName());
        }
    }

    @Test
    @TestTransaction
    public void testUpdateNonExistentField() {
        // Create a fruit
        FruitInput fruitInput = new FruitInput();
        fruitInput.setName("Blueberry");
        fruitInput.setColour("Blue");
        Fruit created = fruitApi.createFruit(fruitInput);

        // Update with null colour (partial update simulation)
        FruitInput updateInput = new FruitInput();
        updateInput.setName("Blueberry");
        updateInput.setColour(null);

        Fruit updated = fruitApi.updateFruit(created.getId(), updateInput);

        assertNotNull(updated);
        assertEquals(created.getId(), updated.getId());
        assertEquals("Blueberry", updated.getName());
        assertNull(updated.getColour());
    }

    private FruitInput createFruitInput(String name, String colour) {
        FruitInput fruitInput = new FruitInput();
        fruitInput.setName(name);
        fruitInput.setColour(colour);
        return fruitInput;
    }
}