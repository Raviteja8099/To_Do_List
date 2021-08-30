package com.example.todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class FileHelper {
    public static final String FILENAME="listinfo.dat";
    public static void writeData(ArrayList<String> list,Context context){
        try (ObjectOutputStream obst = new ObjectOutputStream(context.openFileOutput(FILENAME, context.MODE_PRIVATE))) {
            obst.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> readData(Context context){
        ArrayList<String> res=null;
        try(ObjectInputStream obit=new ObjectInputStream(context.openFileInput(FILENAME))){
           res= (ArrayList<String>) obit.readObject();

        } catch (FileNotFoundException e) {
            res=new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }
}
