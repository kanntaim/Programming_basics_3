package n.galeev;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Main{
    final static int ROWS_TO_TAKE_DEFAULT_NUM = 10;

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String initialFile = scanner.nextLine();
        if(scanner.hasNextLine()){
            int rowsToTake = scanner.nextInt();
            System.out.println(ChooseTestCases(initialFile, rowsToTake));
        }else {
            System.out.println(ChooseTestCases(initialFile));
        }



    }

    private static String ChooseTestCases(String initialFile) throws IOException{
        return ChooseTestCases(initialFile, ROWS_TO_TAKE_DEFAULT_NUM);
    }

    private static String ChooseTestCases(String initialFile, int rowsToTake) throws IOException {
        File allTestCases = new File(initialFile);
        allTestCases.setWritable(true);
        Scanner counter = new Scanner(allTestCases);
        int linesInFile = 0;
        while (counter.hasNextLine()){
            counter.nextLine();
            linesInFile++;
        }
        counter.close();

        int[] randomNums = new Random().ints(1,linesInFile).distinct().limit(rowsToTake).toArray();
        Arrays.sort(randomNums);

        Scanner scanner = new Scanner(allTestCases);
        String resultFileName = (allTestCases.getPath() + allTestCases.getName()).split("[.]")[0] + "_res" + ".txt";

        LinkedList<String> rowsToWriteNewFile = new LinkedList<>();
        LinkedList<String> rowsToWriteOldFile = new LinkedList<>();
        String tableColumnNames = scanner.nextLine();
        rowsToWriteNewFile.add(tableColumnNames);
        rowsToWriteOldFile.add(tableColumnNames);
        int i = 1, k = 0;
        while (scanner.hasNextLine() && k<randomNums.length){
            if (i==randomNums[k]){
                rowsToWriteNewFile.add(scanner.nextLine());
                k++;
                i++;
            } else {
                rowsToWriteOldFile.add(scanner.nextLine());
                i++;
            }
        }
        while (scanner.hasNextLine()){
            rowsToWriteOldFile.add(scanner.nextLine());
        }
        File chosenTestCases = new File(resultFileName);
        chosenTestCases.setWritable(true);
        allTestCases.delete();
        Files.write(chosenTestCases.toPath(),rowsToWriteNewFile);
        Files.write(allTestCases.toPath(), rowsToWriteOldFile);
        return resultFileName;
    }
}
