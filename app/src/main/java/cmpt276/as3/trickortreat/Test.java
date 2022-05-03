package cmpt276.as3.trickortreat;

import cmpt276.as3.trickortreat.model.*;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        TrickOrTreat tot = new TrickOrTreat(6,4,6);
        GameBoard game = GameBoard.getInstance();
        game.setTot(tot);

        int option;
/*
        // print menu and choose
        do {
            System.out.println("1 : print location\n2 : print scan\n3 : print count\n4 : scan cell\n5 : out");
            option = in.nextInt();
            if(option == 1 ){
                game.printLocation();
            }else if (option == 2 ){
                game.printScanned();
            }else if (option == 3 ){
                game.printCount();
            }else if (option == 4 ) {
                System.out.println("row: ");
                int r = in.nextInt();
                System.out.println("col: ");
                int c = in.nextInt();
                if (game.isGridScanned(r, c)) {
                    System.out.println("Scanned already");
                } else {
                    System.out.println("\nNot scan\n\t\t...\n");
                    System.out.println("\nScanning\n");
                    game.scanCell(r, c);
                    game.printScanned();
                    System.out.println("Scanned");
                    game.printCount();
                }
                if(game.checkWin()) {
                    System.out.println("\n\n\t\t You Win!!!!!!\nNewGame\n");
                    tot = new TrickOrTreat(6, 4, 6);
                    game.setTot(tot);
                    game.printLocation();
                    game.printScanned();
                    game.printCount();
                }

            }
        }while(option !=5);*/
        System.out.println("BYE!!!!");
    }
}