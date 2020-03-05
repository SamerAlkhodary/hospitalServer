package model.entities;

public class Government extends Entity {
    public final  static  String role= "GOVERNMENT";
    public Government(int id, String name, String division, String role) {

        super(id, name, division, role);
    }
}
