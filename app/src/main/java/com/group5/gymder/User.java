package com.group5.gymder;

import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class User implements Serializable{
    private String uid;
    private String email;
    private String password;
    private String name;

    /**
     * Gym user is subscribed to
     */
    private String gym;

    /**
     * Age
     */
    private String age;

    /**
     * Gender
     */
    private String gender;

    /**
     * User's Interests
     */
    private String interests;

    /**
     * pfp URL
     */
    private String pfp;

    public User(){
    }

    public User(String uid, String email, String password) {
        this.uid = uid;
        this.email = email;
        this.password = password;

    }

    public String getUid() { return this.uid; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword(){ return this.password; }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getGym() {
        return this.gym;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() { return this.age; }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getInterests(){
        return this.interests;
    }

    public String getMatches()
    {
        return null;
    }


    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getPfp(){
        return this.pfp;
    }

    public void likeUser(User user2) {
        File userFile = new File("likes.txt");
        if(!userFile.exists())
        {
            System.out.println("creating file likes.txt ");
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating likes file");
            }
            System.out.println("file Report.html exists : "+userFile.exists());
        }
        try {
            FileWriter fr = new FileWriter(userFile, true);
            fr.write(email+" "+user2.getEmail()+"\n");
            fr.close();
            boolean theyMatched = checkMatch(email, user2.getEmail(), userFile);
            if(theyMatched) {
                //OPEN CHAT
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error writing to file");
        }
    }

    public boolean checkMatch(String email1, String email2, File likes) {
        boolean way1 = false;
        boolean way2 = false;
        HashMap<String, ArrayList<String>> userMap = new HashMap<String, ArrayList<String>>();
        try {
            Scanner f = new Scanner(likes);
            while(f.hasNextLine()) {
                String line = f.nextLine();
                String[] users = line.split(" ");
                String user1 = users[0];
                String user2 = users[1];
                if(user1.trim().equals(email1) && user2.trim().equals(email2)) {
                    way1 = true;
                }
                if(user1.trim().equals(email2) && user2.trim().equals(email1)) {
                    way2 = true;
                }
            }
            if(way1 && way2) {
                System.out.println("There was a match between " + email1 + " and " + email2);
                return true;
            }
            return false;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("File Not Found");
            return false;
        }
    }
}
