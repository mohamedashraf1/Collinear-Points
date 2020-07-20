import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines;
    private final ArrayList<Point> smallests;
    private final ArrayList<Point> largers;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (isNotValid(points))
            throw new IllegalArgumentException();
        smallests = new ArrayList<>();
        largers = new ArrayList<>();
        lines = new ArrayList<>();
        Point[] temparr = new Point[points.length];
        System.arraycopy(points, 0, temparr, 0, temparr.length);
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(temparr, points[i].slopeOrder());
            checkCollinearity(temparr, points[i]);
        }

    }


    private void checkCollinearity(Point[] arr, Point p) {
        if (arr.length < 4) return;
        double slope = p.slopeTo(arr[1]);
        int count = 0;
        Point biggest = p;
        Point smallest = p;
        double newSlope;
        for (int i = 1; i < arr.length; i++) {
            newSlope = p.slopeTo(arr[i]);
            if (newSlope == slope && i != arr.length - 1) {
                if (arr[i].compareTo(biggest) > 0) biggest = arr[i];
                if (arr[i].compareTo(smallest) < 0) smallest = arr[i];
                count++;

            } else {
                if (i == arr.length - 1 && slope == newSlope) {
                    if (arr[i].compareTo(biggest) > 0) biggest = arr[i];
                    if (arr[i].compareTo(smallest) < 0) smallest = arr[i];
                    count++;
                }
                if (count >= 3) {
                    LineSegment line1 = new LineSegment(smallest, biggest);
                    if (!contain(smallest, biggest)) {
                        lines.add(line1);
                        smallests.add(smallest);
                        largers.add(biggest);
                    }
                }
                slope = newSlope;
                count = 1;
                biggest = p;
                smallest = p;
                if (arr[i].compareTo(biggest) > 0) biggest = arr[i];
                if (arr[i].compareTo(smallest) < 0) smallest = arr[i];
            }

        }
    }

    private boolean contain(Point small, Point large) {
        boolean check = false;

        for (int i = 0; i < smallests.size(); i++) {
            if (small.compareTo(smallests.get(i)) == 0 && large.compareTo(largers.get(i)) == 0) {
                check = true;
                break;
            }
        }
        return check;
    }

    private boolean isNotValid(Point[] points) {
        boolean check = false;
        if (points == null)
            return true;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException();
                if (points[i] != null && points[i].compareTo(points[j]) == 0)
                    check = true;
            }
        }
        return check;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lines.size();
    }

    // the line segments
    public LineSegment[] segments() {

        LineSegment[] arr = new LineSegment[lines.size()];
        int i = 0;
        for (LineSegment line : lines) {
            arr[i] = line;
            i++;
        }
        return arr;
    }


}
