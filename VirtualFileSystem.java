import Persistence.FileDataStreamer;
import PhysicalMemory.AllocationStrategy;
import PhysicalMemory.MemoryManager;

public class VirtualFileSystem {
    Directory root;
    MemoryManager memoryManager;
    AllocationStrategy allocationStrategy;
    String vfsPath;
    FileDataStreamer fds;
    //serialversionUID "VirtualFileSystem"


    public void setMemoryManager(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    //in case of creating a new vfs
    public VirtualFileSystem(String vfsPath, int diskSize, AllocationStrategy strategy){
        this.vfsPath = vfsPath;
        this.allocationStrategy = strategy;
        this.fds = new FileDataStreamer();
        memoryManager = new MemoryManager(diskSize, strategy);
        root  = new Directory(vfsPath, "root");
    }

    //loading an existing vfs
    public VirtualFileSystem(VirtualFileSystem vfs){
        this.vfsPath = vfs.vfsPath;
        this.allocationStrategy = vfs.allocationStrategy;
        this.fds = vfs.fds;
        memoryManager = vfs.memoryManager;
        root  = new Directory(vfsPath, "root");
    }

    //initial directory????
    public VirtualFileSystem(int diskSize, AllocationStrategy strategy) {
        fds = new FileDataStreamer();
        memoryManager = new MemoryManager(diskSize, strategy);
        root = new Directory("/", "root");
        this.allocationStrategy = strategy;
    }

    boolean createFolder(String folderPath){
        String[] directories = folderPath.split("/");

        //different root
        if(!directories[0].equals(root)){
            System.out.println("Invalid path");
            return false;
        }

        //invalid path
        Directory currentDir = root;
        int i;
        for (i = 1; i <directories.length-1 ; i++) {
            currentDir = currentDir.getSubDirectory(directories[i]);
            if (currentDir == null){
                System.out.println("Invalid path");
                return false;
            }
        }

        //folder already exists
        if(currentDir.getSubDirectory(directories[i]) != null){
            System.out.println("A directory already exists with this name!");
            return false;
        }

        currentDir.addDirectory(new Directory(folderPath, directories[i]));
        return true;
    }


}
