package repository;

import model.entities.Entity;
import model.entities.Patient;
import repository.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class  Record<E extends  Entity> {
    private Map<Integer, E> entities = new HashMap<>();

    public boolean add(E entity){
        if(!entities.containsKey(entity.getId())){
            entities.put(entity.getId(),entity);
            return true;
        }
        return  false;

    }
    public  E get(int id){
        return entities.get(id);
    }
    public void remove(E patient){
        entities.remove(patient);
    }
    public List<E>toList(){
        return new LinkedList<>(entities.values());
    }

}
