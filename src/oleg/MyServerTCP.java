package oleg;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import static java.sql.DriverManager.getConnection;

public class MyServerTCP {

    // Объявляется ссылка
    // на объект - сокет сервера
    ServerSocket serverSocket = null;

    /**
     * Конструктор по умолчанию
     */
    public MyServerTCP() {
        try {
            // Создается объект ServerSocket, который получает
            // запросы клиента на порт 1500
            serverSocket = new ServerSocket(1500);
            System.out.println("Starting the server ");
            // Запускаем процесс
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {

            Connection connectiono = getConnection("jdbc:mysql://localhost:3306/world?serverTimezone=UTC","root","1111");
            while (true)
            {
               Statement statement = connectiono.createStatement();
                Socket client = serverSocket.accept();
                //Получение данных(ввиде переменной) от клиент
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                ObjectOutputStream out =
                        new ObjectOutputStream(
                                client.getOutputStream());
                PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
                String s = in.readLine();
                System.out.println(s);
                String [] zapros = s.split(" ");
                if (zapros[0].equals("1")){
                    String l ="";
                    ResultSet resultSet =  statement.executeQuery("select * from country where name = '"+zapros[1]+"';");
                    while ( resultSet.next() )
                        l+= resultSet.getString(2) + " " + resultSet.getString(1)+ " "+ resultSet.getString(3)+" "+resultSet.getString(4) + " " + resultSet.getString(5)+ " "+ resultSet.getString(6)+" "+resultSet.getString(7) + " " + resultSet.getString(8)+ " "+ resultSet.getString(9)+" "+resultSet.getString(10) + " " + resultSet.getString(12)+ " "+ resultSet.getString(13)+" "+ resultSet.getString(14)+ " "+ resultSet.getString(15);
                        l+="\n";
                    resultSet.close();
                    printWriter.println(l);
                }
                if (zapros[0].equals("2")){
                    String l ="";
                    ResultSet resultSet =  statement.executeQuery("select * from city where name = '"+zapros[1]+"';");
                    while ( resultSet.next() )
                        l+= resultSet.getString(2) + " " + resultSet.getInt(1)+ " "+  resultSet.getString(3)+" "+ resultSet.getString(4)+" "+resultSet.getInt(5);
                        l+="\n";
                    resultSet.close();
                    printWriter.println(l);
                }
                if (zapros[0].equals("3")){
                    String l ="";
                    ResultSet resultSet =  statement.executeQuery("select co.name from country co, countrylanguage cl where co.code=cl.CountryCode and cl.Language= '"+zapros[1]+ "' and cl.IsOfficial;");
                    while ( resultSet.next() )
                        l= resultSet.getString(1);
                    	l+="\n";
                    	printWriter.println(l);
                    resultSet.close();

                }
                // Ответ клиенту в ввиде переменной
                /*PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
                printWriter.println("Thankue");*/

            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        // Запуск сервера
        new MyServerTCP();
    }
}
// 2 Gaza