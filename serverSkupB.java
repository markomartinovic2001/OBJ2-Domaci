package com.example.domaci;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class serverSkupB {
    int Dmat[][] = new int[3][3];
    int ResMat[][] = new int[3][1];

    serverSkupB() {

        try {
            ServerSocket ss = new ServerSocket(1234);
            while (true) {
                Socket s = ss.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String readerRes = reader.readLine();
                readerRes = readerRes.trim();
                System.out.println(readerRes);

                String[] matArrOfStr = readerRes.split("=");
                String[] leftSide = matArrOfStr[0].split(" ");
                String[] rightSide = matArrOfStr[1].split(" ");
                int k = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Dmat[i][j] = Integer.parseInt(leftSide[k++]);
                    }
                }
                int z = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 1; j++) {
                        ResMat[i][j] = Integer.parseInt(rightSide[z++]);
                    }
                }
                PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
                String REALRESULT = null;
                REALRESULT = "X=" + createWideMatrix(0) + "/" + createWideMatrix() + " Y=" + createWideMatrix(1) + "/" + createWideMatrix() + " Z=" + createWideMatrix(2) + "/" + createWideMatrix();
                writer.println(REALRESULT);
                writer.close();
                reader.close();
                s.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
//
    }


    public static void main(String[] args) {
        new serverSkupB();
    }

    public int createWideMatrix(int pos) {
        int wideDmat[][] = new int[3][5];
        int z = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if(j == pos){
                    wideDmat[i][j] = ResMat[i][0];
                }else if (j >= 3){
                    wideDmat[i][j] = wideDmat[i][z++];
                    if(z>1){
                        z =0;
                    }
                }else{
                    wideDmat[i][j] = Dmat[i][j];
                }
            }
        }
        return calculateDeterminante(wideDmat);
    }

    public int createWideMatrix() {
        int wideDmat[][] = new int[3][5];
        int z = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (j >= 3) {
                    wideDmat[i][j] = Dmat[i][z++];
                    if (z > 1) {
                        z = 0;
                    }
                } else {
                    wideDmat[i][j] = Dmat[i][j];
                }
            }
        }
        return calculateDeterminante(wideDmat);

    }

    public int calculateDeterminante(int[][] wideDmat) {
        int Dleft = 0;
        int cntr = 0;
        while (cntr < 3) {
            int res = 1;
            for (int i = 0; i < 3; i++) {
                res *= wideDmat[i][i + cntr];
            }
            cntr++;
            Dleft += res;

        }
        int Dright = 0;
        int cntr2 = 0;
        while (cntr2 < 3) {
            int res=1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i + j == 4-cntr2) {
                        res *= wideDmat[i][j];
                    }
                }
            }
            cntr2++;
            Dright += res;
        }
        return Dleft-Dright;
    }

}