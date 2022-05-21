package Persistence;

import java.io.*;

//implements serialization
public class FileDataStreamer implements Serializable{
    private static final long serialVersionUID = "FileDataStreamer".hashCode();

    static void writeToFile(Object obj, String filePath) throws Exception {
        FileOutputStream fOut = new FileOutputStream(new File(filePath));
        ObjectOutputStream objectOut = new ObjectOutputStream(fOut);
        objectOut.writeObject(obj);
        objectOut.close();
        System.out.println("Object successfully written to" + filePath);
    }

    static Object readFromFile(String filePath) throws Exception {
        FileInputStream fIn = new FileInputStream(new File(filePath));
        ObjectInputStream objectIn = new ObjectInputStream(fIn);
        Object readObject = objectIn.readObject();
        fIn.close();

        return readObject;
    }


    //same as write to file????
    //should be inherited from DataStreamer Interface but useless
    public void save(Object object, String filePath) throws Exception {
        FileOutputStream fOut = new FileOutputStream(new File(filePath));
        ObjectOutputStream objectOut = new ObjectOutputStream(fOut);
        objectOut.writeObject(object);
        objectOut.close();
    }

    //same as read from file????
    //should be inherited from DataStreamer Interface but useless
    public Object read(String filePath) throws Exception {
        FileInputStream fIn = new FileInputStream(new File(filePath));
        ObjectInputStream objectIn = new ObjectInputStream(fIn);
        Object readObject = objectIn.readObject();
        fIn.close();

        return readObject;
    }
}
