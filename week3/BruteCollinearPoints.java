import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array cannot be null");
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Point in array cannot be null");
            }
        }

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for (int i = 1; i < pointsCopy.length; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0) {
                throw new IllegalArgumentException("Array contains repeated points");
            }
        }

        lineSegments = new ArrayList<>();

        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        double slopePQ = pointsCopy[p].slopeTo(pointsCopy[q]);
                        double slopePR = pointsCopy[p].slopeTo(pointsCopy[r]);
                        double slopePS = pointsCopy[p].slopeTo(pointsCopy[s]);

                        if (slopePQ == slopePR && slopePR == slopePS) {
                            lineSegments.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
}