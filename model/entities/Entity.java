package model.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected int id;
    private String name;
    private String division;
    private String role;

    public Entity(int id, String name, String division, String role) {
        this.role = role;
        this.id = id;
        this.name = name;
        this.division = division;
    }

    public String getName() {
        return name;
    }

    public String getDivision() {
        return division;
    }

    public String getRole() {
        return role;
    }
    public int getId(){
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Entity) {
            return this.id == ((Entity) object).id;
        }
        return false;

    }

    @Override
    public int hashCode() {
        return id;
    }
}

