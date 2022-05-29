package Persistence;

import java.io.*;

//implements serialization
public class FileDataStreamer implements Serializable{
    private static final long serialVersionUID = "FileDataStreamer".hashCode();
    
    public void save(Object object, String filePath) throws Exception {
        FileOutputStream fOut = new FileOutputStream(new File(filePath));
        ObjectOutputStream objectOut = new ObjectOutputStream(fOut);
        objectOut.writeObject(object);
        objectOut.close();
    }

    public Object read(String filePath) throws Exception {
        FileInputStream fIn = new FileInputStream(new File(filePath));
        ObjectInputStream objectIn = new ObjectInputStream(fIn);
        Object readObject = objectIn.readObject();
        fIn.close();

        return readObject;
    }
}
