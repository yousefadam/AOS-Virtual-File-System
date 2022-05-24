package PhysicalMemory;

import java.io.Serializable;
import java.util.ArrayList;

//implement serialization
public class LinkedAllocation implements AllocationStrategy, Serializable {

    private static final long serialVersionUID = "IndexedAllocation".hashCode();

    @Override
    public String toString() {
        return "LinkedAllocation{}";
    }

    @Override
    public ArrayList<Integer> allocate(int size){
        //will return null if there is no enough space
        if(size > MemoryManager.getFreeBlocksCount()) return null;

        int memorySize = MemoryManager.getSize();
        ArrayList<Integer> allocatedData = new ArrayList<>(); //holds allocated data of current allocation
        boolean[] memoryDisk = MemoryManager.memoryDisk;

        int counter = 0;
        for (int i = 0; i < memorySize; i++) {
            if(!memoryDisk[i]) {
                allocatedData.add(i);
                counter++;
            }

            if (counter == size) break; 
        }

        for (int i: allocatedData) {
            memoryDisk[i] = true;
        }

        MemoryManager.memoryDisk = memoryDisk;
        return allocatedData;
    }
}
