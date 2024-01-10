package ca.gse.guesswho.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Leaderboard {

    File leaderboard = new File("leaderboard.txt");
    ArrayList<GameResult> list;

    public Leaderboard() throws IOException{
        list = leaderList();
    }

    private void write(GameResult newStats) throws IOException {
        try (PrintWriter pw = new PrintWriter(leaderboard)) {
            pw.println(newStats.toCsvRow());
        }
    }



    public ArrayList<GameResult> getList() throws IOException {
        list = new ArrayList<>();
        try (Scanner fileScan = new Scanner(leaderboard)) {
            list = new ArrayList<>();
            while (fileScan.hasNext()) {
                list.add(GameResult.fromCsvRow(fileScan.next()));
            }
        }
        return list;

    }

    private void sort() {
        boolean swap;
        int loopLimiter = 1;
        GameResult tempHolder;
        do{
            swap = false;
            for (int i = 0; i <list.size()-loopLimiter;i++){
                if (list.get(i).getWinTime() > list.get(i+1).getWinTime()){
                    tempHolder = list.get(i);
                    list.set(i,list.get(i+1));
                    list.set(i+1,tempHolder);
                    swap = true;
                }
            }
        } while (swap == true);

        loopLimiter = 1;

        do{
            swap = false;
            for (int i = 0; i <list.size()-loopLimiter;i++){
                if (list.get(i).getTurnCount() > list.get(i+1).getTurnCount()){
                    tempHolder = list.get(i);
                    list.set(i,list.get(i+1));
                    list.set(i+1,tempHolder);
                    swap = true;
                }
            }
        } while (swap == true);

        public 
        
    }

}
