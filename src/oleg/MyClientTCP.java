package oleg;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MyClientTCP {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            int port =1500;
            Socket server = new Socket("localhost",port);
            PrintWriter printWriter = new PrintWriter(server.getOutputStream(), true);
            System.out.println("1 - данные по стране");
            System.out.println("2 - данные по городу");
            System.out.println("3 - Количество стран с официальным введенным языком");
            String s= scanner.nextLine();
            String [] zapros = s.split(" ");
            printWriter.println(s);

            BufferedReader in1 = new BufferedReader(new InputStreamReader(server.getInputStream()));
           
            //ResultSet resultSet = (ResultSet) in.readObject();

            String s1 = in1.readLine();
            System.out.println(s1);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
