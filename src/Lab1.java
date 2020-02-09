import java.io.*;
import java.util.*;

public class Lab1 {
    public static void main (String[] args) {
        Reader reader = null;
        HashMap <String, Integer> hashMap = new HashMap<>();
        int wordsCount = 0;

        try {
            reader = new InputStreamReader(new FileInputStream("in.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            int symbolNum = reader.read();
            while (symbolNum != -1) {
                char symbol = (char) symbolNum;
                if (Character.isLetterOrDigit(symbol)) {
                    stringBuilder.append(symbol);
                }
                else {
                    if (stringBuilder.length() != 0) {
                        String string = stringBuilder.toString();
                        hashMap.putIfAbsent(string, 0);
                        hashMap.replace(string, hashMap.get(string) + 1);
                        stringBuilder.setLength(0);
                        wordsCount++;
                    }
                }
                symbolNum = reader.read();
            }
        }
        catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }

        List <Map.Entry<String, Integer>> list = new LinkedList<>(hashMap.entrySet());

        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return (e2.getValue()).compareTo(e1.getValue());
            }
        });

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out.csv"));
            for (Map.Entry<String,Integer> i : list) {
                writer.write(i.getKey() + ',' + i.getValue() + ',' +
                        String.format("%.2f", (double) (i.getValue()) / wordsCount * 100).replace(',', '.') + '\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
