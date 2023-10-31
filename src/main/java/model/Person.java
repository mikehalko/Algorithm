package model;

public class Person extends Entity {
    private String name;
    private int age;

    public Person(int id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Entity entity) {
        if (! (entity instanceof Person o)) return super.compareTo(entity);
        int nameCompare = name.compareTo(o.name);
        if (nameCompare != 0) return nameCompare;
        int ageCompare = Integer.compare(age, o.age);
        if (ageCompare != 0) return ageCompare;
        return super.compareTo(entity);
    }

    @Override
    public String toString() {
        String format = "PERSON[id=%05d|age=%d|name=%s]";
        return String.format(format, getId(), age, name);
    }
}