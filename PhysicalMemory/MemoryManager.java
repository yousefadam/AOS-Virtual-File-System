package PhysicalMemory;

import java.util.ArrayList;
import java.util.Arrays;

public class MemoryManager {
    private static int size;
    public static AllocationStrategy strategy;
    public static boolean[] memoryDisk;
    //serialVersionUID

    public MemoryManager(int size, AllocationStrategy strategy){
        this.size = size;
        this.strategy = strategy;
        this.memoryDisk = new boolean[size];
    }

    public static int getSize() {
        return size;
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

    public void deAllocateSpace(ArrayList<Integer> toBeDeAllocated){
        for (int blockNum: toBeDeAllocated) {
            memoryDisk[blockNum] = false;
        }
    }

    @Override
    public String toString(){
        return "MemoryManager {Size = " + size +
                ", Strategy = " + strategy +
                ", MemoryDisk = "+ Arrays.toString(memoryDisk);
    }


}
