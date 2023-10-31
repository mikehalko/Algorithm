package model;

public abstract class Entity implements Comparable<Entity> {
    private int id;

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Entity o) {
        return Integer.compare(this.getId(), o.getId());
    }
}