package PhysicalMemory;

import java.io.Serializable;
import java.util.ArrayList;

public class ContiguousAllocation implements AllocationStrategy, Serializable {

    private static final long serialVersionUID = "ContiguousAllocation".hashCode();
    @Override
    public ArrayList<Integer> allocate(int size){
        int memorySize = MemoryManager.getSize();
        boolean[] memoryDisk = MemoryManager.getMemoryDisk();

        //start of best block series, first and last block;
        int bestPos = -1, first = -1, last = -1;

        for (int i = 0; i < memorySize; i++) {
            if(first == -1 && memoryDisk[i])
                continue; //did not find an empty block

            if(!memoryDisk[i] && first == -1)
                first = i; //found first empty block

            if(!memoryDisk[i]){
                if(last < i-first+1){ //if last is < dist btw i and first
                    last = i-first+1; //update last
                    bestPos = first; //update best start
                }
            }
            else{
                first = -1; // search again
            }
        }

        if(last<size)return null; //if last exceeds file size

        ArrayList<Integer> allocatedBlocks = new ArrayList<>();

        for (int j = 0, i = bestPos; j < size ; ++j, ++i) {
            allocatedBlocks.add(i);
        }

        for (var i: allocatedBlocks) {
            memoryDisk[i] = true;
        }

        return allocatedBlocks;

    }
}
