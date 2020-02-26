 
 package repository;

import java.util.List;


import model.entities.Entity;

public interface Repository{
     List<Entity>getEntities(int id,String role);
     void addEntity(Entity entity);
     void removeEntity(Entity entity);
     
     
    
}