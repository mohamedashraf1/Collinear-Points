import java.util.ArrayList;


public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lines;
    private final ArrayList<Point> smallests;
    private final ArrayList<Point> largers;
    private final ArrayList<Double> slopes;

    // finds all line segments containing 4 points

    public BruteCollinearPoints(Point[] points) {
        if (containNull(points) || duplicate(points))
            throw new IllegalArgumentException();
        double slope1, slope2, slope3;
        smallests = new ArrayList<>();
        largers = new ArrayList<>();
        lines = new ArrayList<>();
        slopes = new ArrayList<>();
        Point biggest;
        Point smallest;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                slope1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length; k++) {
                    slope2 = points[i].slopeTo(points[k]);
                    for (int m = k + 1; m < points.length; m++) {
                        slope3 = points[i].slopeTo(points[m]);
                        if (slope1 == slope2 && slope1 == slope3) {
                            biggest = points[i];
                            smallest = points[i];

                            if (points[j].compareTo(biggest) > 0)
                                biggest = points[j];
                            if (points[j].compareTo(smallest) < 0)
                                smallest = points[j];

                            if (points[k].compareTo(biggest) > 0)
                                biggest = points[k];
                            if (points[k].compareTo(smallest) < 0)
                                smallest = points[k];

                            if (points[m].compareTo(biggest) > 0)
                                biggest = points[m];
                            if (points[m].compareTo(smallest) < 0)
                                smallest = points[m];
                            LineSegment line = new LineSegment(smallest, biggest);
                            int indx = contain(smallest, biggest, slope1)[0];
                            int found = contain(smallest, biggest, slope1)[1];
                            if (indx != -1) {
                                Point mybeSmaller = smallests.get(indx);
                                Point mybeBigger = largers.get(indx);
                                if (smallest.compareTo(mybeSmaller) < 0) {//if the smallest is really the smallest ever
                                    lines.remove(lines.get(indx));// remove the fake small(mybeSmaller)
                                    smallests.remove(smallests.get(indx));
                                    largers.remove(largers.get(indx));
                                    slopes.remove(slopes.get(indx));
                                }
                                if (biggest.compareTo(mybeBigger) > 0) {
                                    lines.remove(lines.get(indx));// remove the fake big(mybaeBigger)
                                    smallests.remove(smallests.get(indx));
                                    largers.remove(largers.get(indx));
                                    slopes.remove(slopes.get(indx));
                                }
                            }
                            if (found == 0) {
                                lines.add(line);
                                smallests.add(smallest);
                                largers.add(biggest);
                                slopes.add(slope1);
                            }

                        }
                    }
                }
            }
        }
    }

    private int[] contain(Point small, Point large, double slope) {
        boolean checkX = true;
        boolean checky = true;
        int[] arr = new int[2];
        arr[0] = -1;// for the index of large
        //arr[1] = 0;// found or not the pair(small , large)

        for (int i = 0; i < smallests.size(); i++) {
            if (small.compareTo(smallests.get(i)) == 0 && slope == slopes.get(i)) {
                checkX = false;
                arr[0] = i;
                break;
            }
        }
        for (int i = 0; i < largers.size(); i++) {
            if (large.compareTo(largers.get(i)) == 0 && slopes.get(i) == slope) {
                checky = false;
                arr[0] = i;
                break;
            }
        }
        if (!checkX && checky && slope == slopes.get(arr[0])) {// found the small with the same slope but not the big
            arr[1] = 1;
        } else if (checkX && !checky && slope == slopes.get(arr[0])) {//found the big with the same slope but not the small
            arr[1] = 1;
        } else if (!checkX && !checky)//for the repeated line
            arr[1] = 1;

        return arr;
    }

    private boolean containNull(Point[] points) {
        if (points == null)
            return true;
        for (Point x : points) {
            if (x == null)
                return true;
        }

        return false;

    }

    private boolean duplicate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] != null && points[i].compareTo(points[j]) == 0)
                    return true;
            }
        }
        return false;

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

