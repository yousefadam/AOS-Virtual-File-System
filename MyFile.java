public class MyFile {
    private String name;
    private boolean deleted;

    public String getName() {
        return name;
    }

    public void deleteFile() {
        this.deleted = true;
    }
}
