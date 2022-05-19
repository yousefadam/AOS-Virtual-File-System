import java.util.ArrayList;

//implement serializable
public class MyFile {
    private String name, filePath;
    private boolean deleted;
    //serialversionUID "MyFile"
    private ArrayList<Integer> allocatedBlocks;

    public MyFile(ArrayList<Integer> allocatedBlocks, String filePath) {
        String[] directories = filePath.split("/");
        this.name = directories[directories.length-1];
        this.filePath = filePath;
        this.deleted = false;
        this.allocatedBlocks = allocatedBlocks;
    }

    @Override
    public String toString() {
        return "MyFile{" +
                "name='" + name + '\'' +
                ", filePath='" + filePath + '\'' +
                ", deleted=" + deleted +
                ", allocatedBlocks=" + allocatedBlocks +
                '}';
    }

    public String getFilePath(){
        return filePath;
    }

    public String getName() {
        return name;
    }

    public void deleteFile() {
        this.deleted = true;
    }
}
