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
            nrbOfPairs = Integer.parseInt(scanner.nextLine());
        }
        // Adds information to the lists from readin.
        while (scanner.hasNextLine() && scanner.hasNextInt()) {
            int[] temp = new int[nrbOfPairs + 1];
            for (int i = 0; i <= nrbOfPairs; i++) {
                temp[i] = scanner.nextInt();
            }
            // If the number already exists in women, then add to the list of men.
            if (women.contains(temp[0])) {
                Queue<Integer> temporaryPrefList = new LinkedList<>();
                for (int i = 1; i < temp.length; i++) { //O(n)
                    temporaryPrefList.add(temp[i]);
                }
                mPreferenceList.put(temp[0], temporaryPrefList);
            }
            // Else add woman to the list and add her preference list
            else {
                women.add(temp[0]);
                List<Integer> temporaryPrefList = new LinkedList<>();
                for (int i = 1; i < temp.length; i++) {
                    temporaryPrefList.add(temp[i]);
                }
                wPreferenceList.put(temp[0], temporaryPrefList);
            }
        }
        scanner.close();
    }

    // Returns true if woman likes proposer more than current man.
    public static boolean compareFondness(List<Integer> herPrefList, int proposer, int engagedWith) {
        int proposerRank = herPrefList.indexOf(proposer);
        int engagedWithRank = herPrefList.indexOf(engagedWith);
        return proposerRank < engagedWithRank;
    }

    // If woman has no partner engage.
    // Else, will she remarry and leave her man?
    // If she remarries, put her old man in the availableMen list.
    public static void stableMarriage() {
        addEachManToList(); //O(n)
        while (availableMen.size() != 0) { // W = O(n^2)
            int proposer = availableMen.poll();
            int hisFavoriteGal = mPreferenceList.get(proposer).poll();
            //System.out.println(proposer+" is asking "+hisFavoriteGal);

            if (!engaged.containsKey(hisFavoriteGal)) {
                engaged.put(hisFavoriteGal, proposer);
                //System.out.println(proposer+" engaged to " +hisFavoriteGal);
            }
            else {
                List<Integer> herPrefList = wPreferenceList.get(hisFavoriteGal);
                int oldMan = engaged.get(hisFavoriteGal);
                //System.out.println("but "+hisFavoriteGal+" is engaged");
                if (compareFondness(herPrefList, proposer, oldMan)) {
                    engaged.put(hisFavoriteGal, proposer);
                    availableMen.add(oldMan);
                    //System.out.println(hisFavoriteGal+" dumped her man for "+ proposer);
                }
                else {
                    availableMen.add(proposer);
                    //System.out.println(hisFavoriteGal+" doesn't want "+ proposer);
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
        double t1 = System.currentTimeMillis();
        readInFromConsole();
        double t2 = System.currentTimeMillis();
        stableMarriage();
        double t3 = System.currentTimeMillis();
        printResults();
        System.out.println("Reading took: " + (t2 - t1) / 1000 + " seconds");
        System.out.println("Stable Marriage took: " + (t3 - t2) / 1000 + " seconds");
        System.out.println("Total: " + (t3 - t1) / 1000 + " seconds");

    }

    public static void printResults() {
        for (Map.Entry<Integer, Integer> entry : engaged.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

}
