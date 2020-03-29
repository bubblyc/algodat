import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    // Key: Man, Value: His preference list
    private static Map<Integer, Queue<Integer>> mPreferenceList = new HashMap<>();
    // Key: Woman, Value: Her preference list
    private static Map<Integer, List<Integer>> wPreferenceList = new HashMap<>();
    // List of engaged couples. Key: Woman, Value: Her engaged man
    private static Map<Integer, Integer> engaged = new HashMap<>();
    // List of all the available men
    private static Queue<Integer> availableMen = new LinkedList<>();
    // List of all the women
    private static Set<Integer> women = new HashSet<>();
    // Number of men and women
    private static int nrbOfPairs;

    public static void readInFromConsole() {
        Scanner scanner = new Scanner(System.in);
        // First line is number of pairs
        if (scanner.hasNextLine()) {
            String lineOfInput = scanner.nextLine();
            nrbOfPairs = Integer.parseInt(lineOfInput);
        }
        // Adds information to the lists from readin.
        while (scanner.hasNextLine()) {
            String temp[] = scanner.nextLine().split(" ");
            // If the number already exists in women, then add to the list of men.
            if (women.contains(Integer.parseInt(temp[0]))) {
                availableMen.add(Integer.parseInt(temp[0]));
                // Add to preference list
                Queue<Integer> temporaryPrefList = new LinkedList<>();
                for (int i = 1; i < temp.length; i++) {
                    temporaryPrefList.add(Integer.parseInt(temp[i]));
                }
                mPreferenceList.put(Integer.parseInt(temp[0]), temporaryPrefList);
                // Else add woman to the list and add her preference list
            } else {
                women.add(Integer.parseInt(temp[0]));
                List<Integer> temporaryPrefList = new LinkedList<>();
                for (int i = 1; i < temp.length; i++) {
                    temporaryPrefList.add(Integer.parseInt(temp[i]));
                }
                wPreferenceList.put(Integer.parseInt(temp[0]), temporaryPrefList);
            }
        }
        scanner.close();
    }

    // Returns true if woman likes proposer more than current man.
    public static boolean compareFondness(List<Integer> herPrefList, int proposer, int engagedWith) {
        int proposerRank = herPrefList.indexOf(proposer);
        int engagedWithRank = herPrefList.indexOf(engagedWith);
        if (proposerRank < engagedWithRank) {
            return true;
        } else {
            return false;
        }

    }

    // If woman has no partner engage.
    // Else, will she remarry and leave her man?
    // If she remarries, put her old man in the availableMen list.
    public static void stableMarriage() {
    
        addEachManToList();

        while (!availableMen.isEmpty()) {
            int proposer = availableMen.peek();
            int hisFavoriteGal = mPreferenceList.get(proposer).peek();

            if (!engaged.containsKey(hisFavoriteGal)) {
                engaged.put(proposer, hisFavoriteGal);
                availableMen.remove(proposer);
            } else {
                List<Integer> herPrefList = wPreferenceList.get(hisFavoriteGal);
                int oldMan = engaged.get(hisFavoriteGal);
                boolean remarry = compareFondness(herPrefList, proposer, oldMan);
                if (remarry) {
                    engaged.put(hisFavoriteGal, proposer);
                    availableMen.remove(proposer);
                    availableMen.add(oldMan);
                }
            }
        }
    }

    private static void addEachManToList() {
        for (Map.Entry<Integer, Queue<Integer>> men : mPreferenceList.entrySet()) {
            availableMen.add(men.getKey());
        }
    }

    public static void main(String[] args) {
        readInFromConsole();
        stableMarriage();
        printResults();
        printPrefList();
    }

    public static void printResults() {
        for (Map.Entry<Integer, Integer> entry : engaged.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

    public static void printPrefList() {
        for (Map.Entry<Integer, Queue<Integer>> entry : mPreferenceList.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }

        for (Map.Entry<Integer, List<Integer>> entry : wPreferenceList.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
    }

}









// import java.util.HashMap;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.Scanner;

// public class Solution {
//     private static Map<Integer, List<Integer>> menPreference = new HashMap<>();
//     private static Map<Integer, List<Integer>> womenPreference = new HashMap<>(); 
//     private static Map<Integer, Integer> engaged = new HashMap<>();
//     private static List <Integer> freeMen = new ArrayList<>();
//     private static List <Integer> women = new ArrayList<>();
//     private static int nrOfPairs;
    
    
//     public static void in(){
//         Scanner scanner = new Scanner(System.in);
//         if(scanner.hasNextLine()){
//             String lineOfInput = scanner.nextLine();
//             nrOfPairs = Integer.parseInt(lineOfInput);
//         }
        
//         while (scanner.hasNextLine()) {
//             String temp[] = scanner.nextLine().split(" ");
            
//             if(women.contains(Integer.parseInt(temp[0]))) {
//                 freeMen.add(Integer.parseInt(temp[0]));
//                 List<Integer> tempList = new ArrayList<>();
//                 for (int i = 1; i < temp.length; i ++) {
//                     tempList.add(Integer.parseInt(temp[i]));
//                 }
//                 menPreference.put(Integer.parseInt(temp[0]), tempList);
//             }else{
//                 women.add(Integer.parseInt(temp[0]));
//                 List<Integer> tempList = new ArrayList<>();
//                 for (int i = 1; i < temp.length; i ++) {
//                     tempList.add(Integer.parseInt(temp[i]));
//                 }
//                 womenPreference.put(Integer.parseInt(temp[0]), tempList);
//             }         
//         }
//         scanner.close(); 
//     }

    

//     public static void stableMarriage(){
        
        
//         for(Map.Entry<Integer, List<Integer>> men : menPreference.entrySet()){
//             freeMen.add(men.getKey());
//         }
//         while(freeMen.size()!=0){
//             int currentMan = freeMen.get(0);
//             freeMen.remove(0);
//             int hisFavourite = menPreference.get(currentMan).get(0);
//             menPreference.get(currentMan).remove(0);
//             if(!engaged.containsKey(hisFavourite)){
//                 engaged.put(hisFavourite, currentMan);
//             } else {
//                 List<Integer> herList = womenPreference.get(hisFavourite);
//                 int herMan = engaged.get(hisFavourite);
//                 if(herList.indexOf(currentMan) < herList.indexOf(herMan)){
//                     engaged.put(hisFavourite, currentMan);
//                     freeMen.add(herMan);
//                 }
//                 else{
//                     freeMen.add(currentMan);
//                 }
//             }

//         }

//     }
//     /*
//     public static void out(){

//     }
//     */

//     public static void main(String[] args) {
//         /*
//         prefw1.add(1);
//         prefw1.add(2);
//         List<Integer> prefw2 = new ArrayList<>();
//         prefw2.add(2);
//         prefw2.add(1);
//         womenPreference.put(1, prefw1);
//         womenPreference.put(2, prefw2);
//         List<Integer> prefm1 = new ArrayList<>();
//         prefm1.add(2);
//         prefm1.add(1);
//         List<Integer> prefm2 = new ArrayList<>();
//         prefm2.add(1);
//         prefm2.add(2);
//         menPreference.put(1, prefm1);
//         menPreference.put(2, prefm2);
//         */
        
//         in();

//         stableMarriage();

//         for (Map.Entry<Integer, Integer> entry : engaged.entrySet()) {
//             System.out.println( entry.getValue().toString());
//         }

//         for (Map.Entry<Integer, List<Integer>> entry : menPreference.entrySet()) {
//             System.out.println( entry.getKey() + ":" + entry.getValue().toString());
//         }

//         for (Map.Entry<Integer, List<Integer>> entry : womenPreference.entrySet()) {
//             System.out.println( entry.getKey() + ":" + entry.getValue().toString());
//         }
//     }


// }
