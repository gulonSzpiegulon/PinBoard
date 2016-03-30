package my.vaadin.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.vaadin.data.util.BeanContainer;

//class that allows to communicate with a database which is an txt file of name: nameOfFile
//it provides functions for reading from and writing to this database
public class UserDataBase {
	private final BeanContainer<String, BoardUser> containerOfUsers = 
			new BeanContainer<String, BoardUser>(BoardUser.class);	//a container used for holding whole database during 
																	//execution of this application
	//private final Table tableOfUsers = new Table("Table of users", containerOfUsers);	//a table connected with the caontainer
	private String nameOfFile = "userDataBase.txt";		//name of the txt file which is database
	
	UserDataBase() {
		containerOfUsers.setBeanIdProperty("mail");	//in constructor mail is set as bean id
		readUsersFromFile();	//when the application starts all data are read from the txt file into bean container
	}
	
	private void readUsersFromFile() {	
		try {
			Scanner reader = new Scanner(new File(nameOfFile));
			String singleUser = "";
			int indexWhereWordBegins;
			int indexOfSpace;
			String[] singleUserSplit = new String[4];
			while(reader.hasNextLine()) {	//as long as there is the next line in txt file 
				indexWhereWordBegins = 0;
				singleUser = reader.nextLine();	//it is read and written to singleUser string (without \n character)
				for (int i = 0; i < 3; i++) {	//as there are 3 spaces in every line, for each of them
					indexOfSpace = singleUser.indexOf(' ', indexWhereWordBegins);	//it is calculated it's value
					singleUserSplit[i] = singleUser.substring(indexWhereWordBegins, indexOfSpace);	//and a substring 
					//beetween first and the last letter of a given word is created and written to it's place in string array
					indexWhereWordBegins = indexOfSpace + 1;	//new word starts after the space so...
				}
				singleUserSplit[3] = singleUser.substring(indexWhereWordBegins, singleUser.length());	//the last word, 
				//the password is beetween the indexWhereWordBegins and the last character (not a space character) so it have to
				//be treated differently and the last character is the last letter not the \n character	
				containerOfUsers.addBean(	
						new BoardUser(singleUserSplit[0], singleUserSplit[1], singleUserSplit[2], singleUserSplit[3]));
				//at end the line that was spliited and is hold in string array is a base to create 
				//new bean that will be hold in bean container
			}
		} catch (FileNotFoundException e) {	//if the txt file of a given name doeasn't exist 
			File file = new File(nameOfFile);	//it should be created and left empty
			try {
				file.createNewFile();
			} catch (IOException e1) {
				System.out.println("IOException occured during creation of new file!!!");
			}
		}
	}
	
	public void addNewUser(String name, String surname, String mail, String password) {		//function that is to 
		//be used by together with function contains by MyRegistrationForm class to add new user
		containerOfUsers.addBean(new BoardUser(name, surname, mail, password));	//it adds new user to the container
		writeNewUserToFile(name, surname, mail, password);	//and just after this, adds new user to txt file
	}
	
	private void writeNewUserToFile(String name, String surname, String mail, String password) {
		//it just writes parameters of this function to txt file in correct order
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nameOfFile, true)))) {
		    writer.println(name + " " + surname + " " + mail + " " + password);
		}catch (IOException e) {
		    System.out.println("IOException occored during writing new user to the file!!!");
		}
		//true in file writer constructor parameters sets it to append text to the file instead repleacing it evary time 
		//printwriter makes possible the usage of such simmple functions as println() and buffered writer is reccomended
		//for expensive writerers such as file writer
	}
	
	public boolean contains(String emailAddress) {	//function that is to be used together with addNewUser function
		//by MyRegistrationForm class to add new user
		if (containerOfUsers.containsId(emailAddress)) {	//it checks if a given emailAddress exists already in the data base 
			return true;
		}
		return false;
	}
	
	public boolean checkIfUsernameAndPasswordMatches(String mail, String password) {	//function used by MyLoginForm class
		//to determine if user exists and if the password given is correct
		if (contains(mail)) {	//first of all mail has to exist in database
			if (containerOfUsers.getContainerProperty(mail, "password").getValue().toString().equals(password)) {	//then
				//it is checked if the password givven as teh parameter is the same in value as the password corresponding to
				//mail in the database
				return true;	//if so, then true is returned
			}
		}
		return false;	//else, false is returned
	}
}
