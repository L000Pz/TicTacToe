import java.io.*;

public class files {
    private static int gameNum;
    private static String fileLoc = "C:\\file\\";

    public static int getGameNum() {
        return gameNum;
    }
    public static void setGameNum(int gameNum) {
        files.gameNum = gameNum;
    }

    public static File getLastModified(String directoryFilePath) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime)
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }

    public static void Save(){
        if (getLastModified(fileLoc)!= null) {
            try (BufferedReader BFR = new BufferedReader(new FileReader(getLastModified(fileLoc)))) {
                String line = BFR.readLine();
                String next;
                for (boolean first = true, last = (line == null); !last; first = false, line = next) {
                    last = ((next = BFR.readLine()) == null);
                    if (last) {
                        if (!line.isEmpty()) {
                            String[] Num = line.split(" ");
                            files.setGameNum(Integer.parseInt(Num[0]));
                            gameNum++;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("There are no files");
            }
        }else gameNum = 1;
        File file = new File(fileLoc + gameNum);
        try{
            file.createNewFile();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            StringBuilder myBoard = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    myBoard.append(TheGame.getBoard().getBoard()[i][j]).append(",");
                }
            }
            bufferedWriter.append(String.valueOf(gameNum)).append(" ");
            bufferedWriter.append(myBoard).append(" ");
            bufferedWriter.append(TheGame.getDefTurn());
            bufferedWriter.newLine();
            System.out.println("Done"+"\n"+"Your save id is : "+gameNum);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void Load(int num){
        String[] Num ;
        gameNum = num;
        String file = fileLoc + gameNum ;
        int[][] newBoard = new int[0][];
        String turn = null;
        try (BufferedReader BFR = new BufferedReader(new FileReader(file))) {
            String line = BFR.readLine();
            newBoard = new int[3][3];
            int pointer = 0;
            while (line != null) {
                Num = line.split(" ");
                String[] part = Num[1].split(",");
                if (num == Integer.parseInt(Num[0])) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            newBoard[i][j] = Integer.parseInt(part[pointer]);
                            pointer++;
                        }
                    }
                    turn = Num[2];
                }
                pointer = 0;
                line = BFR.readLine();

            }

        } catch (IOException ignored) {
        }
        TheGame.getBoard().setBoard(newBoard);
        TheGame.getBoard().drawer();
        TheGame.TicTacToe(turn);
    }
}
