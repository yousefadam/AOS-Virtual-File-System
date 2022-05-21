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
        int memorySize = MemoryManager.getSize();
        ArrayList<Integer> allocatedData = new ArrayList<>(); //holds allocated data of current allocation
        boolean[] memoryDisk = MemoryManager.memoryDisk;

        for (int i = 0; i < size; i++) {
            if(allocatedData.size() >= memorySize) break; //size exceeded disk capacity
            if(!memoryDisk[i]) allocatedData.add(i); //allocate if free
        }

        //if(allocatedData.size()<size) return null; may never happen

        for (int i: allocatedData) {
            memoryDisk[i] = true;
        }

        //will return null if there is no enough space
        return allocatedData;
    }
}
