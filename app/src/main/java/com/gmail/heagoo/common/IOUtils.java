package com.gmail.heagoo.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtils {

    public static void writeObjectToFile(String filePath, Object obj) {
        File file = new File(filePath);
        ObjectOutputStream objOut = null;
        try {
            objOut = new ObjectOutputStream(new FileOutputStream(file));
            objOut.writeObject(obj);
            objOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWithoutThrow(objOut);
        }
    }

    public static Object readObjectFromFile(String filePath) {
        Object result = null;
        File file = new File(filePath);
        ObjectInputStream objIn = null;
        try {
            objIn = new ObjectInputStream(new FileInputStream(file));
            result = objIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeWithoutThrow(objIn);
        }
        return result;
    }

    private static void closeWithoutThrow(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
