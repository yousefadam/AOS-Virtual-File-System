import java.util.ArrayList;

public class Directory {
    private String name, dirPath;
    private ArrayList<MyFile> files;
    private ArrayList<Directory> subDirectories;
    private boolean deleted = false;

    public Directory(String dirPath, String name){
        this.dirPath = dirPath;
        this.name = name;
        files = new ArrayList<>();
        subDirectories = new ArrayList<>();
    }

    private String getName() {
        return name;
    }

    public void addFile(MyFile file){
        files.add(file);
    }

    public MyFile getFile(String Name){
        for (MyFile file: files) {
            if (file.getName() == Name)
                return file;
        }
        return null;
    }
    public void deleteFile(MyFile file){
        files.remove(file);
    }

    public void addDirectory(Directory directory){
        subDirectories.add(directory);
    }

    public Directory getSubDirectory(String dirName){
        for (Directory dir: subDirectories) {
            if (dir.getName() == dirName)
                return dir;
        }
        return null;
    }

    //delete the directory itself
    public void deleteDirectory(){
        this.deleted = true;

        for (MyFile file :files) {
            file.deleteFile();
        }

        //physical memory manager

        files.clear();

        for (Directory dir: subDirectories) {
            dir.deleteDirectory();
        }

        subDirectories.clear();
    }

    //delete internal subdirectories
    public void deleteSubDirectory(Directory dir){
        subDirectories.remove(dir);
    }

    public void displayDirectoryStructure(int level){
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < level; i++) {
            tabs.append('\t');
        }

        System.out.println(tabs.toString() + '<'+name+'>');

        tabs.append('\t');
        for (var file: files) {
            System.out.println(tabs.toString() + file.getName());
        }

        for (var directory: subDirectories) {
            directory.displayDirectoryStructure(level + 1);
        }
    }

    //toString??
}
