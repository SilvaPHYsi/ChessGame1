package ErrorTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class principal {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	
		try {
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		  System.out.println("Hello. Welcome to Palace Hotel!!!");
		  System.out.println("Insert a room number: ");
		  int numberr = sc.nextInt();
		  System.out.println("Insert a check-In date: ");
		  Date checkIn = sdf.parse(sc.next());
		  System.out.println("Insert a check-Out date: ");
		  Date checkOut = sdf.parse(sc.next());
		  Reservation rs = new Reservation(numberr, checkIn , checkOut);
		  System.out.println(rs);
		}
		catch(ParseException e) {
			System.out.println("invalid date ");
		}
		catch (DomainException e) {
            System.out.println("Error in reservation: " + e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Unexpected error");
		}
		sc.close();
	}
	

}
