package PhysicalMemory;

import java.io.Serializable;
import java.util.ArrayList;

public class ContiguousAllocation implements AllocationStrategy, Serializable {

    private static final long serialVersionUID = "ContiguousAllocation".hashCode();
    @Override
    public ArrayList<Integer> allocate(int size){
        if(size>MemoryManager.getFreeBlocksCount())return null;

        int memorySize = MemoryManager.getSize();
        boolean[] memoryDisk = MemoryManager.memoryDisk;

        int start = -1, minNumBlocks = Integer.MAX_VALUE;

        int counter;
        for (int i = 0; i < memorySize; i++) {
            counter = 0;
            if(memoryDisk[i]){
                continue;
            }else{
                for (int j = i; j < memorySize && !memoryDisk[j]; j++){
                    counter++;
                }

                if(minNumBlocks>counter && counter>=size){
                    start = i;
                    minNumBlocks = counter;
                }

                i += counter;
            }
        }


        if(minNumBlocks>MemoryManager.getSize()) return null; //no available series of blocks
        ArrayList<Integer> allocatedBlocks = new ArrayList<>();

        for (int j = 0, i = start; j < size ; ++j, ++i) {
            allocatedBlocks.add(i);
        }

        for (var i: allocatedBlocks) {
            memoryDisk[i] = true;
        }

        MemoryManager.memoryDisk = memoryDisk;
        return allocatedBlocks;

    }
}
