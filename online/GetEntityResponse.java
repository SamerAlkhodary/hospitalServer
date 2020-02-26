package online;

import java.util.List;

import model.entities.Entity;

public class GetEntityResponse implements Message{
    private List<Entity>entities;

    public GetEntityResponse(List<Entity> entities) {
        this.entities = entities;
    }
    public  List<Entity>getEntities(){
        return this.entities;
    }





}