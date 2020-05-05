import java.util.*;

public class PairSolution {

    private static List<Point> allPoints = new ArrayList<>();
    private static List<Point> pointsSortedByX = new ArrayList<>();
    private static List<Point> pointsSortedByY = new ArrayList<>();
    private static int size;

    public static void main(String[] args) {
        readIn();
        //testPrint();
        Pair p = bruteForce(allPoints);
        System.out.printf(Locale.ENGLISH, "%.6f%n", p.distance);

    }

    private static void readIn() {
        Scanner scanner = new Scanner(System.in);
        String[] temp = scanner.nextLine().split(" ");
        size = Integer.parseInt(temp[0]);
        while (scanner.hasNext()) {
            String[] temp1 = scanner.nextLine().split(" ");
            int x = Integer.parseInt(temp1[0]);
            int y = Integer.parseInt(temp1[1]);
            allPoints.add(new Point(x, y));
        }
        scanner.close();
    }

    private static void testPrint() {
        closestPoints();
        System.out.println("Sort by X: ");
        for (Point point : pointsSortedByX) {
            System.out.println(point.toString());
        }
        System.out.println("Sort by Y: ");
        for (Point point : pointsSortedByY) {
            System.out.println(point.toString());
        }
        //Test distance function and print out in right format x.xxxxxx
        Double dist = allPoints.get(0).distanceTo(allPoints.get(1));
        System.out.printf(Locale.ENGLISH, "%.6f %n", dist);

    }

    //Collections.sort time complexity O(nlogn)
    private static void closestPoints() {
        //Create two sorted arrays Px and Py
        for (Point p : allPoints) {
            pointsSortedByX.add(p);
            pointsSortedByY.add(p);
        }
        pointsSortedByX.sort(Comparator.comparingInt(Point::getX));
        pointsSortedByY.sort(Comparator.comparingInt(Point::getY));
        //return closest(pointsSortedByX, pointsSortedByY, allPoints.size());
    }

    /*private static double closest(List<Point> sortByX, List<Point> sortByY, int size) {
        if (size <= 3) {
            return bruteForce(sortByX);
        }

        int mid = size / 2;
        int midOdd = (size % 2 != 0) ? mid : mid + 1;

        //Divide Px into two arrays Lx and Rx
        List<Point> leftX = new ArrayList<>();
        List<Point> rightX = new ArrayList<>();
        //Divide Py into two arrays Ly and Ry
        List<Point> leftY = new ArrayList<>();
        List<Point> rightY = new ArrayList<>();

        for (int i = 0; i < mid; i++) {
            leftX.add(sortByX.get(i));
            leftY.add(sortByY.get(i));
        }

        for (int i = mid; i < size; i++) {
            rightX.add(sortByX.get(i));
            rightY.add(sortByY.get(i));
        }

        //Solve the two subproblems (Lx, Ly, size/2) and (Rx, Ry, size/2)
        double distLeft = closest(leftX, leftY, mid);
        double distRight = closest(rightX, rightY, mid);

        //Compute delta as the minimum from these subproblems
        double d = (distLeft < distRight) ? distLeft : distRight;
        //Create the set Sy from Py (Bridge between left and right)
        List<Point> strip = new ArrayList<>();

        for (int i = mid - (int) d; i < mid + d; i++) {
            strip.add(sortByX.get(i));
        }

        //Check each point in Sy to see if any nearby points is closer than delta

        double stripd = bruteForce(strip);
        return (d < stripd) ? d : stripd;
    }
*/

    /*  static double bruteForce(List<Point> p) {
          double min = Double.MAX_VALUE;
          int n = p.size();
          for (int i = 0; i < n; i++) {
              for (int j = i + 1; i < n; j++) {
                  double dist = p.get(i).distanceTo(p.get(j));
                  if (dist < min) {
                      min = dist;
                  }
              }
          }
          return min;
      }
     */

    public static Pair bruteForce(List<Point> p) {
        int n = p.size();
        Pair pair = new Pair(p.get(0), p.get(1));
        if (n > 2) {
            for (int i = 0; i < n - 1; i++) {
                Point p1 = p.get(i);
                for (int j = i + 1; j < n; j++) {
                    Point p2 = p.get(j);
                    double dist = p1.distanceTo(p2);
                    if (dist < pair.distance) {
                        pair.update(p1, p2, dist);
                    }
                }
            }
        }
        return pair;
    }


    public static class Pair {
        private Point p1;
        private Point p2;
        private double distance;

        public Pair(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
            distance = p1.distanceTo(p2);
        }

        public void update(Point p1, Point p2, double distance) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = distance;
        }

    }
}


class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Double distanceTo(Point other) {
        double deltaX = other.x - x;
        ;
        double deltaY = other.y - y;
        return Math.hypot(deltaX, deltaY);
    }

    public String toString() {
        return String.format("(x: %d, y: %d)", x, y);
    }

}


