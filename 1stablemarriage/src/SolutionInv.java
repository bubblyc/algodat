import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class SolutionInv {
    // K: Man, V: His pref list
    private static Map<Integer, Queue<Integer>> mPreferenceList = new HashMap<>();
    // K: Woman, V: Her preference list
    private static Map<Integer, int[]> wPreferenceList = new HashMap<>();
    // List of engaged couples. K: Woman, V: Her man
    private static Map<Integer, Integer> engaged = new HashMap<>();
    // List of all the available men
    private static Queue<Integer> availableMen = new LinkedList<>();
    // List of all the women
    private static Set<Integer> women = new HashSet<>();
    // Number of men and women
    private static int nrbOfPairs;

    public static void readInFromConsole() {
        Scanner scanner = new Scanner(System.in);
        // First line of the list is number of pairs, filter it out from the rest of the readin.
        if (scanner.hasNextLine()) {
            nrbOfPairs = Integer.parseInt(scanner.nextLine());
        }
        // Adds information to the lists from readin, int by int.
        while (scanner.hasNextLine() && scanner.hasNextInt()) {
            int[] temp = new int[nrbOfPairs + 1];
            //temp[0] contains either the man or the woman.
            for (int i = 0; i <= nrbOfPairs; i++) {
                temp[i] = scanner.nextInt();
            }
            // If temp[0] already exists in women, then add to the list of men.
            if (women.contains(temp[0])) {
                Queue<Integer> temporaryPrefList = new LinkedList<>();
                for (int i = 1; i < temp.length; i++) { //O(n)
                    temporaryPrefList.add(temp[i]);
                }
                mPreferenceList.put(temp[0], temporaryPrefList);
            }
            // Else add woman to the list and add her preference list
            else {
                int[] temporaryPrefList = new int[nrbOfPairs + 1];
                for (int i = 1; i < temporaryPrefList.length; i++) { //O(n)
                    temporaryPrefList[temp[i]] = i;
                    women.add(temp[0]);
                }
                wPreferenceList.put(temp[0], temporaryPrefList);
            }
        }
        scanner.close();
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
                int[] herPrefList = wPreferenceList.get(hisFavoriteGal);
                int oldMan = engaged.get(hisFavoriteGal);
                //System.out.println("but "+hisFavoriteGal+" is engaged");
                if (herPrefList[oldMan] > herPrefList[proposer]) {
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
