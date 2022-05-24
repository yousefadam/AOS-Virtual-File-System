import java.io.Serializable;
import java.util.ArrayList;
import PhysicalMemory.*;

import PhysicalMemory.MemoryManager;

public class Directory implements Serializable {
    private String name, dirPath;
    private ArrayList<MyFile> files;
    private ArrayList<Directory> subDirectories;
    private boolean deleted = false;
    private static final long serialVersionUID = "Directory".hashCode();

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

    public ArrayList<MyFile> getFiles() {
        return files;
    }

    public MyFile getFile(String Name){
        for (MyFile file: files) {
            if (file.getName().equals(Name))
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
            if (dir.getName().equals(dirName))
                return dir;
        }
        return null;
    }

    //delete the directory itself
    public void deleteDirectory(){
        this.deleted = true;

        for (MyFile file : files) {
            file.deleteFile();
            MemoryManager.deAllocateSpace(file.getAllocatedBlocks());
        }
        
        files.clear();

        for (Directory dir: subDirectories) {
            dir.deleteDirectory();
        }

        subDirectories.clear();
    }

    //delete internal subdirectories
    public void deleteSubDirectory(Directory dir){
        dir.deleteDirectory();
        subDirectories.remove(dir);
    }

    public void displayDirectoryStructure(int level){
        String tab = "    ";
        String tabs = "";
        for (int i = 0; i < level; i++) {
            tabs += tab;
        }
        
        System.out.println(tabs + '<'+name+'>');


        for (var file: files) {
            System.out.println(tabs + "    " + file.getName());
        }

        for (var directory: subDirectories) {
            directory.displayDirectoryStructure(level + 1);
        }
    }
}
