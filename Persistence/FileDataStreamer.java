package Persistence;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//implements serialization
public class FileDataStreamer implements Serializable {
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

    public boolean writeUser(String uName, String pwd) throws Exception {
        if (searchUser(uName)) {
            System.out.println("A user already exists with this username!");
            return false;
        }
        FileOutputStream fOut = new FileOutputStream(new File("user.txt"));
        String record = uName + " , " + pwd + "\n";
        byte[] recordBytes = record.getBytes();
        fOut.write(recordBytes);
        fOut.close();
        System.out.println("User added successfully ");
        return true;
    }

    boolean searchUser(String uName) throws Exception {
        String line = "";
        FileInputStream fIn = new FileInputStream(new File("User.txt"));
        Scanner scan = new Scanner(fIn);
        while (scan.hasNextLine()) {
            line = scan.nextLine();

            //split and remove all spaces
            var recordData = line.replaceAll("\\s+", "").split(",");
            if (recordData[0].equals(uName)) {
                return true;
            }
        }
        return false;
    }

    boolean login(String uName, String pwd) throws Exception {
        String line = "";
        FileInputStream fIn = new FileInputStream(new File("User.txt"));
        Scanner scan = new Scanner(fIn);

        String[] recordData = new String[0];
        while (scan.hasNextLine()) {
            line = scan.nextLine();

            //split and remove all spaces
            recordData = line.replaceAll("\\s+", "").split(",");
            if (recordData[0].equals(uName)) {
                break;
            }
        }

        if (recordData == null){
            System.out.println("No user with this username exists");
            return false;
        }

        if (!recordData[1].equals(pwd)){
            System.out.println("Incorrect password!");
            return false;
        }

        return true;

    }
}
