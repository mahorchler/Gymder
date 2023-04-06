package Users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class User implements Serializable{
	/**
	 * Username of user
	 */
	private String username;
	
	/**
	 * List of Users Interest
	 */
	private ArrayList<String> interests;
	
	/**
	 * Gym user is subscribed to
	 */
	private String gym;
	
	/**
	 * Age
	 */
	private int age;
	
	/**
	 * Email
	 */
	private String email;
	
	public User(String username, String email, int age) {
		this.username = username;
		this.email = email;
		this.age = age;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void addInterest(String interest) {
		this.interests.add(interest);
	}
	
	public ArrayList<String> getInterests(){
		return this.interests;
	}
	
	public void setGym(String gym) {
		this.gym = gym;
	}
	
	public String getGym() {
		return this.gym;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public String getEmail() {
		return this.email;
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
			fr.write(username+" "+user2.getUsername()+"\n");
			fr.close();
			boolean theyMatched = checkMatch(username, user2.getUsername(), userFile);
			if(theyMatched) {
				//OPEN CHAT
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error writing to file");
		}
	}
	
	public boolean checkMatch(String username1, String username2, File likes) {
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
				if(user1.trim().equals(username1) && user2.trim().equals(username2)) {
					way1 = true;
				}
				if(user1.trim().equals(username2) && user2.trim().equals(username1)) {
					way2 = true;
				}
			}
			if(way1 && way2) {
				System.out.println("There was a match between " + username1 + " and " + username2);
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
