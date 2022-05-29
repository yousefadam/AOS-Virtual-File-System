import Persistence.FileDataStreamer;
import PhysicalMemory.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        FileDataStreamer fds  =new FileDataStreamer();
        Scanner scan = new Scanner(System.in);
       
        AllocationStrategy strategy = null;
        VirtualFileSystem vfs = null;
        boolean loaded = true;

        String vfsPath = "";
        String answer = "";

        System.out.println("Load file structure from path? (y/n)");
        answer = scan.nextLine();

        if(answer.equalsIgnoreCase("y")){
            answer = "";
            System.out.println("Load from default path? (y/n)");
            answer = scan.nextLine();

            if(answer.equalsIgnoreCase("y")){
                vfsPath = "./";
            }else{
                System.out.println("Enter file structure path that contains vfs.txt and memory.txt");
                vfsPath = scan.nextLine();
            }

            try{
                boolean[] memory = (boolean[])fds.read(vfsPath+"\\memory.txt");
                vfs = (VirtualFileSystem) fds.read(vfsPath+"\\vfs.txt");
                MemoryManager.setMemoryDisk(memory);
                MemoryManager.setSize(memory.length);

            }catch(Exception e){
                System.out.println("Loading Failed");
                loaded = false;
            }
        }else{
            int diskSize = 0;
            System.out.println("Enter disk size in KBs");
            diskSize = Integer.parseInt(scan.nextLine());

            int stg = 0;
            System.out.println("Select allocation technique\n" +
                    "1. Contiguous Allocation\n2. Linked Allocation\n3. Indexed Allocation");

            stg = Integer.parseInt(scan.nextLine());
            switch (stg){
                case 1:
                    strategy = new ContiguousAllocation();
                    break;
                case 2:
                    strategy = new LinkedAllocation();
                    break;
                case 3:
                    strategy = new IndexedAllocation();
                    break;
                default:
            }

            vfs = new VirtualFileSystem(diskSize, strategy);
        }

        //commands
        System.out.println("Enter commands");

        String input;
        boolean quit = false;

        while (!quit){
            Parser parser = new Parser();
            input = scan.nextLine();
            parser.parse(input);
            String command = parser.getCmd();

            switch(command){
                case "CreateFile": {
                    if (parser.getArgs().size() == 2)
                        vfs.createFile(parser.getArgs().get(0), Integer.parseInt(parser.getArgs().get(1)));
                    else
                        System.out.println("Invalid arguments");
                    break;
                }

                case "CreateFolder":{
                    if (parser.getArgs().size() == 1)
                        vfs.createFolder(parser.getArgs().get(0));
                    else
                        System.out.println("Invalid arguments");
                    break;
                }

                case "DeleteFile":{
                    if (parser.getArgs().size() == 1)
                        vfs.deleteFile(parser.getArgs().get(0));
                    else
                        System.out.println("Invalid arguments");
                    break;
                }

                case "DeleteFolder":{
                    if (parser.getArgs().size() == 1)
                        vfs.deleteFolder(parser.getArgs().get(0));
                    else
                        System.out.println("Invalid arguments");
                    break;
                }

                case "DisplayDiskStatus":{
                    vfs.displayDiskStatus();
                    break;
                }

                case "DisplayDiskStructure":{
                    vfs.displayDiskStructure();
                    break;
                }

                case "quit":{
                    quit = true;
                    break;
                }
                default:
                    System.out.println("Invalid command");
            }
        }

        try {
            fds.save(vfs, "vfs.txt" );
            fds.save(MemoryManager.getMemoryDisk(), "memory.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
