import java.io.*;
import java.util.*;
/*
Class to get the input lines in which only a word differs.
Approach : Steps to be followed:
1) Read the input from the file.
2) Split the words using  space character " ".
3) For each line, match with the lines below the line to see if the words match and only
    a single word is found with no match.
4) Add the index of the word not matched to the 2-D map containing the row and column
    index as value to the key as the row to which we are computing.
5) Based on the map created, take a StringBuffer and append the output to the buffer.
6) Write output to the output path file and exit.
*/
public class ConsistentPhrasing {
    /* Function to write output to the file */
    private static void writeToFileOutput(Map<Integer, Map<Integer, List<Integer>>> outputMap, String[][] inputLines, List<String> inputList) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("output.txt"));

            StringBuffer buffer = new StringBuffer();
            for (Integer key : outputMap.keySet()) {
                Map<Integer, List<Integer>> integerListMap = outputMap.get(key);
                for (Integer innerKey : integerListMap.keySet()) {
                    List<String> words = new ArrayList<>();
                    List<Integer> rowIndexs = integerListMap.get(innerKey);
                    for (Integer eachRowIndex : rowIndexs) {
                        words.add(inputLines[eachRowIndex][innerKey]);
                        buffer.append(inputList.get(eachRowIndex) + "\n");
                    }
                    buffer.append("The changing word was: " + "\n");
                    for (String word : words) {
                        buffer.append(" " + word + ",");
                    }
                    buffer.deleteCharAt(buffer.length()-1);
                    buffer.append("\n");
                }

            }
            writer.write(buffer.toString());
            writer.close();
        }catch (IOException e) {
            System.out.println("Error while writing to file");
            return;
        }
    }
    /*  Function to get the similar lines with a single word difference */
    private static Map<Integer, Map<Integer, List<Integer>>> getSameLineWithSingleWordChangeMapping(String[][] inputLines, int index) {
        Map<Integer, Map<Integer, List<Integer>>> outputMap = new HashMap<>();
        Set<String> visitedIndex = new HashSet<>();
        for(int i = 0; i < index; i++) {
            for(int j = i+1; j < index; j++) {
                int difference = 0;
                int kIndex = -1;
                if(inputLines[i].length == inputLines[j].length) {
                    for(int k = 2; k < inputLines[j].length; k++) {
                        if(inputLines[i][k].equalsIgnoreCase(inputLines[j][k]))
                            continue;
                        else {
                            difference++;
                            kIndex = k;
                        }
                    }

                    if(difference == 1 && !visitedIndex.contains(String.valueOf(j) + String.valueOf(kIndex))) {
                        visitedIndex.add(String.valueOf(j) + String.valueOf(kIndex));
                        if(outputMap.containsKey(i)) {
                            if(outputMap.get(i).containsKey(kIndex))
                                outputMap.get(i).get(kIndex).add(j);
                            else {
                                List<Integer> indexList = new ArrayList<>();
                                indexList.add(i);
                                indexList.add(j);
                                outputMap.get(i).put(kIndex, indexList);
                            }
                        }
                        else {
                            Map<Integer, List<Integer>> innerMap = new HashMap<>();
                            List<Integer> indexList = new ArrayList<>();
                            indexList.add(i);
                            indexList.add(j);
                            innerMap.put(kIndex, indexList);
                            outputMap.put(i, innerMap);
                        }
                    }
                }
            }
        }
        return outputMap;
    }
    /* Main handler to read input, compute and write back the output */
    private static void getInputAndGenerateOutputFile(String filePath) {
        String[][] inputLines = new String[1000][];
        List<String> inputList = new ArrayList<>();
        int inputLength = prepareInput(filePath, inputLines, inputList);
        Map<Integer, Map<Integer, List<Integer>>> outputMap = getSameLineWithSingleWordChangeMapping(inputLines, inputLength);
        writeToFileOutput(outputMap, inputLines, inputList);
    }

    private static int prepareInput(String filePath, String[][] inputLines, List<String> inputList)  {
        File file = new File(filePath);
        int index = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] inputLine;
            while ((st = br.readLine()) != null) {
                inputList.add(st);
                inputLine = st.split(" ");
                inputLines[index] = inputLine;
                index++;
            }
        } catch (IOException e) {
            System.out.println("File not found or readable");
            return 0;
        }
        return index;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "input.txt";
        getInputAndGenerateOutputFile(filePath);
    }

}
