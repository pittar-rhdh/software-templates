package ${{values.groupId}}.${{values.artifactId}}.api.impl;

import java.util.List;
import java.util.stream.Collectors;

import ${{values.groupId}}.${{values.artifactId}}.api.model.Fruit;
import ${{values.groupId}}.${{values.artifactId}}.api.model.FruitInput;
import ${{values.groupId}}.${{values.artifactId}}.api.resources.FruitsResource;
import ${{values.groupId}}.${{values.artifactId}}.domain.FruitEntity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.NotFoundException;

public class FruitApiImpl implements FruitsResource {

    @Override
    public Fruit createFruit(FruitInput fruitInput) {
        FruitEntity entity = new FruitEntity();
        entity.name = fruitInput.getName();
        entity.colour = fruitInput.getColour();

        entity.persist();

        return mapEntityToFruit(entity);
    }

    @Override
    public Response deleteFruit(Long id) {
        FruitEntity entity = FruitEntity.findById(id);

        if (entity == null) {
            throw new NotFoundException("Fruit not found with id: " + id);
        }

        entity.delete();

        return Response.status(Status.NO_CONTENT).build();
    }

    @Override
    public Fruit getFruitById(Long id) {
        FruitEntity entity = FruitEntity.findById(id);

        if (entity == null) {
            throw new NotFoundException("Fruit not found with id: " + id);
        }

        return mapEntityToFruit(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Fruit> getFruits() {
        return ((List<FruitEntity>) (List<?>) FruitEntity.listAll()).stream()
                .map(this::mapEntityToFruit)
                .collect(Collectors.toList());
    }

    @Override
    public Fruit updateFruit(Long id, FruitInput fruitInput) {
        FruitEntity entity = FruitEntity.findById(id);

        if (entity == null) {
            throw new NotFoundException("Fruit not found with id: " + id);
        }

        entity.name = fruitInput.getName();
        entity.colour = fruitInput.getColour();

        entity.persist();

        return mapEntityToFruit(entity);
    }

    private Fruit mapEntityToFruit(FruitEntity entity) {
        Fruit fruit = new Fruit();
        fruit.setId(entity.id);
        fruit.setName(entity.name);
        fruit.setColour(entity.colour);
        return fruit;
    }

}
