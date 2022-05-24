package PhysicalMemory;

import java.io.Serializable;
import java.util.ArrayList;

//implement serialization
public class IndexedAllocation implements AllocationStrategy, Serializable {

    private static final long serialVersionUID = "IndexedAllocation".hashCode();

    @Override
    public String toString() {
        return "IndexedAllocation{}";
    }

    @Override
    public ArrayList<Integer> allocate(int size){
        //will return null if there is no enough space
        if(size + 1 > MemoryManager.getFreeBlocksCount())return null;

        int memorySize = MemoryManager.getSize();
        ArrayList<Integer> allocatedData = new ArrayList<>(); //holds allocated data of current allocation
        boolean[] memoryDisk = MemoryManager.memoryDisk;

        for (int i = 0; i < size; i++) {
            if(allocatedData.size() >= memorySize + 1) break; //size exceeded disk capacity, extra bit for index
            if(!memoryDisk[i]) allocatedData.add(i); //allocate if free
        }


        for (int i: allocatedData) {
            memoryDisk[i] = true;
        }

        //will return null if there is no enough space
        return allocatedData;
    }

}
