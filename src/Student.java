public class Student {
    private int id;
    private String nim, name;

    public Student(int id, String nim, String name) {
        this.id = id; this.nim = nim; this.name = name;
    }
    public int getId() { return id; }
    public String getNim() { return nim; }
    public String getName() { return name; }
}