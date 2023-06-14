package de.hsas.inf.schatzsuchesubactivity;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FileIOScores {

    private AppCompatActivity context;
    public FileIOScores(AppCompatActivity context) {
        this.context = context;
        if (context == null) {
            Log.e("TEST","fio null context");
        }
    }

    public void printFileContent(String fName) {
        ArrayList<ScoreItem> list = new ArrayList<ScoreItem>();
        String content="";
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(context.openFileInput(fName)));
            String line;

            while((line=in.readLine())!= null) {
                Log.e("FILE","read line " + line);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  ArrayList<ScoreItem> readScores(String fName){
        ArrayList<ScoreItem> list = new ArrayList<ScoreItem>();
        String content="";
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(context.openFileInput(fName)));
            String line;
            int [] dateNumbers = new int[3];
            int [] timeNumbers = new int[3];
            while((line=in.readLine())!= null) {
                int score = Integer.parseInt(line);
                String []  dateTime = in.readLine().split(" ");
                String [] dateParts = dateTime[0].split("-");
                String [] timeParts = dateTime[1].split(":");
               ;
                for (int i=0; i<dateNumbers.length; i++) {
                    dateNumbers[i]=Integer.parseInt(dateParts[i]);
                    timeNumbers[i] = Integer.parseInt(timeParts[i]);
                }
                LocalDateTime d =
                 LocalDateTime.of(dateNumbers[0],dateNumbers[1],dateNumbers[2],
                         timeNumbers[0],timeNumbers[1],timeNumbers[2]);
                ScoreItem item = new ScoreItem(score,d);
                list.add(item);


            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public  ArrayList<String> list () {
        ArrayList<String> content = new ArrayList<String>();
        File appdir = context.getDataDir();
        File filesDir = new File(appdir.getAbsolutePath()+"/files");
        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {

                if (name.startsWith("File") && name.endsWith(".txt")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        String[] filenames = filesDir.list();

        if (filenames != null && filenames.length > 0) {
            for(String f:filenames) {
                content.add(f);
            }
        }
        else {

            try {

                PrintWriter p = new PrintWriter(
                        new OutputStreamWriter(
                          context.openFileOutput("File1.txt", Context.MODE_PRIVATE)));
                p.println("");
                p.close();
                content.add("File1.txt");


            } catch (IOException e) {
                String s = e.getMessage();
                content.add(s);
            }
        }
        return content;
    }



    public  void writeFile( String fName, String content){
        try {
            PrintWriter p = new PrintWriter(
                    new OutputStreamWriter(
                            context.openFileOutput(fName, Context.MODE_APPEND)));
            Log.e("FILE","vor write content: " + content);
            p.write(content);
            p.flush();
            p.close();
            Log.e("FILE","nach WRITEFILE");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void eraseContent(String fName) {
        try {
            PrintWriter p = new PrintWriter(
                    new OutputStreamWriter(
                            context.openFileOutput(fName,Context.MODE_PRIVATE)));
            Log.e("FILE","vor erase: ");
            p.write("");
            p.flush();
            p.close();
            Log.e("FILE","nach erase");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
