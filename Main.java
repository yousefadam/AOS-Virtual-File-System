import PhysicalMemory.ContiguousAllocation;

public class Main {
    public static void main(String[] args) {

        int diskSize = 100;
        VirtualFileSystem vfs = new VirtualFileSystem(diskSize, new ContiguousAllocation());

        vfs.createFile("root/haha.txt", 20);
        vfs.createFile("root/haha1.txt", 30);
        vfs.displayDiskStatus();
  

       
        //vfs.createFolder("root/folder1/folder2/folder3/folder4/folder5");





      









    }
}
