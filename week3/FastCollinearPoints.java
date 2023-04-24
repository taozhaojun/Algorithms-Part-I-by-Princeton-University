import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
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

        for (Point origin : pointsCopy) {
            Point[] pointsBySlope = pointsCopy.clone();
            Arrays.sort(pointsBySlope, origin.slopeOrder());

            int count = 1;
            for (int i = 1; i < pointsBySlope.length; i++) {
                if (origin.slopeTo(pointsBySlope[i]) == origin.slopeTo(pointsBySlope[i - 1])) {
                    count++;
                } else {
                    if (count >= 3 && origin.compareTo(pointsBySlope[i - count]) < 0) {
                        lineSegments.add(new LineSegment(origin, pointsBySlope[i - 1]));
                    }
                    count = 1;
                }
            }

            if (count >= 3 && origin.compareTo(pointsBySlope[pointsBySlope.length - count]) < 0) {
                lineSegments.add(new LineSegment(origin, pointsBySlope[pointsBySlope.length - 1]));
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