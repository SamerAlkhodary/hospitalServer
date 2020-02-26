 
 package repository;

import java.util.List;


import model.entities.Entity;

public interface Repository{
     List<Entity>getEntities(int id,String division);
     void addEntity(Entity entity);
     void removeEntity(Entity entity);
     
     
    
}