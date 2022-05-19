package PhysicalMemory;

import java.util.ArrayList;

//implement serialization
public class IndexedAllocation implements AllocationStrategy {

    @Override
    public String toString() {
        return "IndexedAllocation{}";
    }

    @Override
    public ArrayList<Integer> allocate(int size){
        int memorySize = MemoryManager.getSize();
        ArrayList<Integer> allocatedData = new ArrayList<Integer>(); //holds allocated data of current allocation
        boolean[] memoryDisk = MemoryManager.memoryDisk;

        for (int i = 0; i < size; i++) {
            if(allocatedData.size() >= memorySize + 1) break; //size exceeded disk capacity, extra bit for index
            if(!memoryDisk[i]) allocatedData.add(i); //allocate if free
        }

        //if(allocatedData.size()<size + 1) return null; may never happen

        for (int i: allocatedData) {
            memoryDisk[i] = true;
        }

        return allocatedData;
    }

}
