package Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ChatHistory {
    private static final File historyFile = new File("src/main/java/Server/History.txt");


    public void saveHistoryToFile(char[] c) {
        try (FileWriter fileWriter = new FileWriter(historyFile, true)){
            for (char value : c) {
                fileWriter.append(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File not found");
        }
    }

    public String loadLastOneHundredLines () {

        StringBuilder stringBuilder = new StringBuilder();

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(historyFile, "r")) {
            long fileLength = historyFile.length();
            randomAccessFile.seek(fileLength);

            int readedLines = 0;
            int linesToRead = 100;



            for (long p = fileLength; p >= 0; p--) {
                randomAccessFile.seek(p);
                char c = (char) randomAccessFile.read();
                if (c == '\n') {
                    readedLines++;
                    if (readedLines == linesToRead) {
                        break;
                    }
                }
                stringBuilder.append(c);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
     return stringBuilder.reverse().toString();
    }
}
