package PhysicalMemory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class MemoryManager implements Serializable {
    private static int size;
    public static AllocationStrategy strategy;
    public static boolean[] memoryDisk;
    private static final long serialVersionUID = "MemoryManager".hashCode();

    public MemoryManager(int size, AllocationStrategy strategy){
        this.size = size;
        this.strategy = strategy;
        this.memoryDisk = new boolean[size];
    }

    public static int getSize() {
        return size;
    }

    public static int getFreeBlocksCount(){
        int counter = 0;
        for (int i = 0; i< memoryDisk.length; i++) {
            if(!memoryDisk[i])counter++;
        }
        return counter;
    }

    public static void setSize(int size) {
        MemoryManager.size = size;
    }

    public static AllocationStrategy getStrategy() {
        return strategy;
    }

    public static void setStrategy(AllocationStrategy strategy) {
        MemoryManager.strategy = strategy;
    }

    public static boolean[] getMemoryDisk() {
        return memoryDisk;
    }

    public static void setMemoryDisk(boolean[] memoryDisk) {
        MemoryManager.memoryDisk = memoryDisk;
    }

    public ArrayList<Integer> allocateSpace(int size){
        return strategy.allocate(size);
    }

    public static void deAllocateSpace(ArrayList<Integer> toBeDeAllocated){
        for (int blockNum: toBeDeAllocated) {
            memoryDisk[blockNum] = false;
        }
    }





}
