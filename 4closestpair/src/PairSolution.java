import java.util.*;

public class PairSolution {

    private static List<Point> allPoints = new ArrayList<>();
    private static List<Point> pointsSortedByX = new ArrayList<>();
    private static List<Point> pointsSortedByY = new ArrayList<>();
    private static int size;

    public static void main(String[] args) {
        readIn();
        //testPrint();
        double t = System.currentTimeMillis();
        System.out.printf(Locale.ENGLISH, "%.6f %n",closestPoints());
        System.out.println(System.currentTimeMillis()-t);
    }

    private static void readIn() {
        Scanner scanner = new Scanner(System.in);
        String[] temp = scanner.nextLine().split(" ");
        size = Integer.parseInt(temp[0]);
        //System.out.println(size);
        while (scanner.hasNext()) {
            String[] temp1 = scanner.nextLine().split(" ");
            int x = Integer.parseInt(temp1[0]);
            int y = Integer.parseInt(temp1[1]);
            allPoints.add(new Point(x, y));
            //System.out.println(x + " " + y);
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
        Double dist = allPoints.get(0).distance(allPoints.get(1));
        System.out.printf(Locale.ENGLISH, "%.6f %n", dist);
    }

    //Collections.sort time complexity O(nlogn)
    private static double closestPoints() {
        //Create two sorted arrays Px and Py
        for (Point p : allPoints) {
            pointsSortedByX.add(p);
            pointsSortedByY.add(p);
        }
        pointsSortedByX.sort(Comparator.comparingInt(Point::getX));
        pointsSortedByY.sort(Comparator.comparingInt(Point::getY));
        return closest(pointsSortedByX, pointsSortedByY, allPoints.size());
    }

    private static double closest(List<Point> sortByX, List<Point> sortByY, int size) {

        if(size <= 3){
            return bruteForce(sortByX);
        } else{
            int mid = size/2;
            int midOdd = (size % 2 == 0) ? mid : mid + 1;
            int midx = sortByX.get(mid).getX();

            //Divide Px into two arrays Lx and Rx
            List<Point> leftX = new ArrayList<>();
            List<Point> rightX = new ArrayList<>();
            //Divide Py into two arrays Ly and Ry
            List<Point> leftY = new ArrayList<>();
            List<Point> rightY = new ArrayList<>();

            for(int i=0; i < mid; i++){
                leftX.add(sortByX.get(i));
                leftY.add(sortByY.get(i));
            }

            for(int i = mid; i < sortByX.size(); i++){
                rightX.add(sortByX.get(i));
            }

            for(int i = mid; i < sortByY.size(); i++){
                rightY.add(sortByY.get(i));
            }


            //Solve the two subproblems (Lx, Ly, size/2) and (Rx, Ry, size/2)
            double distLeft = closest(leftX, leftY, mid);
            double distRight = closest(rightX, rightY, midOdd);

            //Compute delta as the minimum from these subproblems
            double d = Math.min(distLeft, distRight);
            //Create the set Sy from Py (Bridge between left and right)
            List<Point> strip = new ArrayList<>();
            for(int i = 0; i < sortByY.size(); i++){
                int x = sortByY.get(i).getX();
                if(midx-((int)d) < x || midx+((int)d) > x) strip.add(sortByX.get(i));
            }

            //Check each point in Sy to see if any nearby points is closer than delta
            double stripd = bruteForce(strip);
            /*
            for(int i = 0; i < strip.size(); i++){
                for(int j = i + 1; j < Math.min(15, strip.size()); j++){
                    double dist = strip.get(i).distance(strip.get(j));
                    if(dist < stripd){
                        stripd = dist;
                    }
                }
            }

             */
            return Math.min(d, stripd);
        }


    }


    private static double bruteForce(List<Point> p){
        int n = p.size();
        double min = Double.MAX_VALUE;
        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < Math.min(n, 15); j++){
                double dist = p.get(i).distance(p.get(j));
                if(dist < min){
                    min = dist;
                }
            }
        }
        return min; 
    }
}



class Point {
    private int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Double distance(Point other) {
        return Math.hypot(x - other.x, y - other.y);
    }

    public String toString() {
        return String.format("(x: %d, y: %d)", x, y);
    }

}


