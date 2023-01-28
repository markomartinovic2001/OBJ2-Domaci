package com.example.domaci;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class serverSkupA {
//povezivanje sa klijentom
    serverSkupA(){
        try {
            ServerSocket ss = new ServerSocket(1234);
            while(true){
                Socket s = ss.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
//string koji cita sta je korisnik uneo, prolazak kroz taj string i provera da li je u pitanju palindrom.
                String rez = reader.readLine();
                String reverse = "";
                for(int i = rez.length() - 1; i >= 0; i--){
                    reverse += Character.toString(rez.charAt(i));
                }
//ispis te reci i ispis da li je u pitanju palindrom ili nije.
                PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
                System.out.println(reverse);
                if(reverse.equals(rez)){
                    writer.println(" Jeste palindrom. ");
                }
                else{
                    writer.println(" Nije palindrom. ");
                }
                reader.close();
                s.close();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new serverSkupA();
    }
}
