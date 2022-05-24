import Persistence.FileDataStreamer;
import PhysicalMemory.AllocationStrategy;
import PhysicalMemory.MemoryManager;

import java.io.Serializable;
import java.util.ArrayList;

public class VirtualFileSystem implements Serializable {
    Directory root;
    MemoryManager memoryManager;
    AllocationStrategy allocationStrategy;
    String vfsPath;
    FileDataStreamer fds;
    private static final long serialVersionUID = "VirtualFileSystem".hashCode();

    public void setMemoryManager(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    // in case of creating a new vfs
    public VirtualFileSystem(String vfsPath, int diskSize, AllocationStrategy strategy) {
        this.vfsPath = vfsPath;
        this.allocationStrategy = strategy;
        this.fds = new FileDataStreamer();
        memoryManager = new MemoryManager(diskSize, strategy);
        root = new Directory(vfsPath, "root");
    }

    // loading an existing vfs
    public VirtualFileSystem(VirtualFileSystem vfs) {
        this.vfsPath = vfs.vfsPath;
        this.allocationStrategy = vfs.allocationStrategy;
        this.fds = vfs.fds;
        memoryManager = vfs.memoryManager;
        root = new Directory(vfsPath, "root");
    }

    // initial directory????
    public VirtualFileSystem(int diskSize, AllocationStrategy strategy) {
        fds = new FileDataStreamer();
        memoryManager = new MemoryManager(diskSize, strategy);
        root = new Directory("/", "root");
        this.allocationStrategy = strategy;
    }

    void displayDiskStructure() {
        root.displayDirectoryStructure(0);
    }

    boolean createFolder(String folderPath) {
        String[] directories = folderPath.split("/");

        // different root
        if (!directories[0].equals("root")) {
            System.out.println("Invalid path");
            return false;
        }

        // invalid path
        Directory currentDir = root;
        int i;
        for (i = 1; i < directories.length - 1; i++) {
            currentDir = currentDir.getSubDirectory(directories[i]);
            if (currentDir == null) {
                System.out.println("Invalid path");
                return false;
            }
        }

        // folder already exists
        if (currentDir.getSubDirectory(directories[i]) != null) {
            System.out.println("A directory already exists with this name!");
            return false;
        }

        currentDir.addDirectory(new Directory(folderPath, directories[i]));

        displayDiskStructure();
        return true;
    }

    boolean createFile(String filePath, int fileSize) {
        String[] directories = filePath.split("/");

        // different root
        if (!directories[0].equals("root")) {
            System.out.println("Invalid path");
            return false;
        }

        // invalid path
        Directory currentDir = root;
        int i;
        for (i = 1; i < directories.length - 1; i++) {
            currentDir = currentDir.getSubDirectory(directories[i]);
            if (currentDir == null) {
                System.out.println("Invalid path");
                return false;
            }
        }

        // file already exists
        if (currentDir.getFile(directories[i]) != null) {
            System.out.println("A File already exists with this name!");
            return false;
        }

        // check if enough size is available
        ArrayList<Integer> allocatedData = allocationStrategy.allocate(fileSize);
        if (allocatedData == null) {
            System.out.println("There is no enough space in Disk");
            return false;
        }

        currentDir.addFile(new MyFile(filePath, allocatedData));

        displayDiskStructure();
        return true;
    }

    boolean deleteFolder(String folderPath) {
        String[] directories = folderPath.split("/");

        if (directories.length == 1) {
            System.out.println("Invalid command"); // must send full path
            return false;
        }

        if (!directories[0].equals("root")) {
            System.out.println("Invalid Path"); // not matching root
            return false;
        }

        Directory currentDir = root;
        int i;
        for (i = 1; i < directories.length - 1; i++) {
            currentDir = currentDir.getSubDirectory(directories[i]);
            if (currentDir == null) {
                System.out.println("Invalid path");
                return false;
            }
        }

        Directory target = currentDir.getSubDirectory(directories[i]);

        if (target == null) {
            System.out.println("No such file or directory");
            return false;
        }

        target.deleteDirectory();
        currentDir.deleteSubDirectory(target);
        displayDiskStructure();
        return true;
    }

    boolean deleteFile(String filePath) {
        String[] directories = filePath.split("/");

        if (!directories[0].equals("root")) {
            System.out.println("Invalid Path"); // not matching root
            return false;
        }

        Directory currentDir = root;
        int i;
        for (i = 1; i < directories.length - 1; i++) {
            currentDir = currentDir.getSubDirectory(directories[i]);
            if (currentDir == null) {
                System.out.println("Invalid path");
                return false;
            }
        }

        MyFile target = currentDir.getFile(directories[directories.length - 1]);

        if (target == null) {
            System.out.println("No such file or directory");
            return false;
        }

        currentDir.deleteFile(target);
        memoryManager.deAllocateSpace(target.getAllocatedBlocks());

        target.deleteFile();
        displayDiskStructure();
        return true;

    }

    void displayDiskStatus() {
        boolean[] memoryDisk = memoryManager.memoryDisk;
        int mSize = memoryDisk.length;

        System.out.println("Used Blocks: ");

        int counter = 0;
        for (int i = 0; i < mSize; i++) {
            if (memoryDisk[i]) {
                counter++;
                System.out.print(i + ", ");

            }
        }
        System.out.println("\nTotal Used Blocks: " + counter);

        System.out.println("Free Blocks: ");

        counter = 0;
        for (int i = 0; i < mSize; i++) {
            if (!memoryDisk[i]) {
                counter++;
                System.out.print(i + ", ");
            }
        }
        System.out.println("\nTotal Free Blocks: " + counter);
    }

}
