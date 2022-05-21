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
        if(size>MemoryManager.getFreeBlocksCount()) return null;

        int memorySize = MemoryManager.getSize();
        ArrayList<Integer> allocatedData = new ArrayList<>(); //holds allocated data of current allocation
        boolean[] memoryDisk = MemoryManager.memoryDisk;

        for (int i = 0; i < size; i++) {
            if(!memoryDisk[i]) allocatedData.add(i); //allocate if free
        }

        //[1,5,6,33] 4

        for (int i: allocatedData) {
            memoryDisk[i] = true;
        }

        //MemoryManager.memoryDisk = memoryDisk; TEST MEMORY UPDATE!!!!

        //will return null if there is no enough space
        return allocatedData;
    }
}
