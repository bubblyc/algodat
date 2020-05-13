import java.util.*;

public class GorillaSolution {
  static Map<String, HashMap<String, Integer>> costMatrix = new HashMap<>();
  static List<Sequence> sequences = new ArrayList<>();
  static int penalty = -4;

  public static void main(String[] args) {
    readIn();
    printStrings();
  }

  private static void printStrings() {
    for (Sequence s : sequences) {
      int[][] allScores = costMatrix(s.s1, s.s2);
      alignStrings(allScores, s.s1, s.s2);
    }
  }

  public static void readIn() {
    Scanner scanner = new Scanner(System.in);
    String[] s = scanner.nextLine().split(" ");
    for (int i = 0; i < s.length; i++) {
      costMatrix.put(s[i], new HashMap<>());
      for (int j = 0; j < s.length; j++) {
        int val = scanner.nextInt();
        costMatrix.get(s[i]).put(s[j], val);
      }
    }
    scanner.nextInt();
    scanner.nextLine();
    while (scanner.hasNextLine()) {
      String[] str = scanner.nextLine().split(" ");
      sequences.add(new Sequence(str[0], str[1]));
    }
  }

  // The cost matrix is constructed so that the optimal string alignment can be built based on
  // maximized cost.
  public static int[][] costMatrix(String s1, String s2) {
    int cols = s1.length() + 1;
    int rows = s2.length() + 1;
    int[][] costs = new int[rows][cols];

    // Initialize array with penalty cost on rows: 0 and cols: 0
    for (int row = 0; row < rows; row++) {
      costs[row][0] = row * penalty;
    }
    for (int col = 0; col < cols; col++) {
      costs[0][col] = col * penalty;
    }

    // Calculate costs across the whole matrix
    for (int row = 1; row < rows; row++) {
      for (int col = 1; col < cols; col++) {
        String c1 = Character.toString(s1.charAt(col - 1));
        String c2 = Character.toString(s2.charAt(row - 1));
        int cost = costMatrix.get(c1).get(c2);
        costs[row][col] =
            max(
                cost + costs[row - 1][col - 1],
                penalty + costs[row][col - 1],
                penalty + costs[row - 1][col]);
      }
    }
    return costs;
  }

  // Appends either the character or an "*" based on the optimal alignment.
  public static void alignStrings(int[][] costs, String s1, String s2) {
    int row = costs.length - 1;
    int col = costs[0].length - 1;
    StringBuilder sb1 = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();

    // Starts from the last cell and works its way up towards row: 0 col: 0.
    while (row != 0 && col != 0) {
      String c1 = Character.toString(s1.charAt(col - 1));
      String c2 = Character.toString(s2.charAt(row - 1));

      if ((costs[row][col] == (penalty + costs[row][col - 1]))) {
        sb1.append(c1);
        sb2.append("*");
        col--;
      } else if (costs[row][col] == (penalty + costs[row - 1][col])) {
        sb1.append("*");
        sb2.append(c2);
        row--;
      } else {
        sb1.append(c1);
        sb2.append(c2);
        row--;
        col--;
      }
    }

    // If the strings are not of same length pad with "*".
    if (row == 0) {
      for (int k = 0; k < col; k++) {
        sb1.append(s1.charAt(row));
        sb2.append("*");
      }
    } else if (col == 0) {
      for (int k = 0; k < row; k++) {
        sb1.append("*");
        sb2.append(s2.charAt(col));
      }
    }
    System.out.println(sb1.reverse() + " " + sb2.reverse());
  }

  private static int max(int a, int b, int c) {
    return Math.max(Math.max(a, b), c);
  }

  public static class Sequence {
    String s1, s2;

    public Sequence(String s1, String s2) {
      this.s1 = s1;
      this.s2 = s2;
    }
  }
}
