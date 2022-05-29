package PhysicalMemory;

import java.io.Serializable;
import java.util.ArrayList;

//implement serialization
public class IndexedAllocation implements AllocationStrategy, Serializable {

    private static final long serialVersionUID = "IndexedAllocation".hashCode();


    @Override
    public ArrayList<Integer> allocate(int size){
        //will return null if there is no enough space
        //Extra bit for the first block that points to all allocated blocks
        if(size + 1 > MemoryManager.getFreeBlocksCount()) return null;

        int memorySize = MemoryManager.getSize();
        ArrayList<Integer> allocatedData = new ArrayList<>(); //holds allocated data of current allocation
        boolean[] memoryDisk = MemoryManager.memoryDisk;

        int counter = 0;
        for (int i = 0; i < memorySize; i++) {
            if(!memoryDisk[i]) {    //allocate if free
                allocatedData.add(i);
                counter++;
            }

            if (counter == size + 1) break; 
        }

        for (int i: allocatedData) {
            memoryDisk[i] = true;
        }
   
        return allocatedData;
    }
}
