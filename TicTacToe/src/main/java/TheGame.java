import java.io.*;

import java.util.Scanner;

public class TheGame {
    private static final String gameSave = "C:\\file\\savegame.txt";

    private static board board = new board();
    private static String defTurn;

    public static board getBoard() {
        return board;
    }
    public static void setBoard(board board) {
        TheGame.board = board;
    }
    public static String getDefTurn() {
        return defTurn;
    }
    public static void setDefTurn(String defTurn) {
        TheGame.defTurn = defTurn;
    }

    public static void TicTacToe(String turn) {
        defTurn = turn;
        Scanner s = new Scanner(System.in);
        String[] token = new String[0];

        int pos = 0;
        int count = 0;
        boolean progress = result.gameResult(board.getBoard()) == result.state.In_Progress;
        if (!progress) {
            System.out.println(result.gameResult(board.getBoard()) + " WON!!! ");
            System.out.print(" GAME OVER ");
        }
        else if (count == 9) {
            System.out.println("DRAW");
        }
        do {
            try {
                token = s.nextLine().split(" ");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong entry, try again!");
            }
            switch (token[0]) {
                case "/put" -> {
                    while (progress && !board.isFull()) {
                        try {
                            pos = Integer.parseInt(token[1]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Wrong entry, try again!");
                        }catch (NumberFormatException e){
                            System.out.println("Wrong entry, try again!");
                        }
                        if (defTurn.equals("p1")) {
                            try {
                                if (!board.placed(pos)) {
                                    board.placerP1("p1", pos);
                                    board.drawer();
                                    count++;
                                    defTurn = "p2";
                                    progress = result.gameResult(board.getBoard()) == result.state.In_Progress;
                                    break;
                                } else {
                                    System.out.println("It's already taken, try again!");
                                    break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Wrong entry, try again!");
                            }
                        } else if (defTurn.equals("p2")) {
                            try {
                                if (!board.placed(pos)) {
                                    board.placerP2("p2", pos);
                                    board.drawer();
                                    count++;
                                    defTurn = "p1";
                                    progress = result.gameResult(board.getBoard()) == result.state.In_Progress;
                                    break;
                                } else {
                                    System.out.println("It's already taken, try again!");
                                    break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Wrong entry, try again!");
                            }
                        } else {
                            System.out.println("IT'S NOT YOUR TURN");

                        }
                        progress = result.gameResult(board.getBoard()) == result.state.In_Progress;
                    }
                    if (count == 9 && progress){
                        System.out.println("Draw");
                    }
                    if (!progress) {
                            System.out.println(result.gameResult(board.getBoard()) + " WON!!! ");
                            System.out.print(" GAME OVER ");
                    }

                }
                case "/save" -> {
                    files.Save();
                }
                case "/exit" -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("Wrong command, try again!");
                }
            }
        }while (true);
    }
    public static void load(int num) {
        files.Load(num);
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String[] input;
        do {
            input = s.nextLine().split(" ");
            switch (input[0]){
                case "/start" : {
                    board.defaultBoard();
                    TheGame.TicTacToe("p1");
                }
                    break;
                case "/load": TheGame.load(Integer.parseInt(input[1]));
                case "/exit" : break;
                default:
                    System.out.println("Wrong command, try again!");
            }
        } while (true);
    }
}


//p1,1 p2,3 p1,5 p2,9 p1,6 p2,4 p1,2 p2,8 p1,7 -> DRAW


