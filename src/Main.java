import java.util.Arrays;
import java.util.Comparator;

public class Main {

    // 2차원 평면에서의 점을 나타내는 클래스
    static class Point {

        int x, y;

        Point(int x, int y) {

            this.x = x;
            this.y = y;
        }
    }

    // 두 점 사이의 거리 제곱을 계산하는 함수
    private static double dist(Point p1, Point p2) {

        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    // 분할 정복을 사용하여 가장 가까운 두 점을 찾는 함수
    public static double closestPair(Point[] points) {
        // x 좌표 기준으로 점들을 정렬
        Arrays.sort(points, Comparator.comparingInt(p -> p.x));
        return closestUtil(points, 0, points.length - 1);
    }

    // 분할 정복 알고리즘: 점들을 분할하고 재귀적으로 가장 가까운 두 점을 찾음
    private static double closestUtil(Point[] points, int left, int right) {
        // 기저 사례: 점이 3개 이하인 경우 브루트포스 방식으로 해결
        if (right - left <= 3) {
            return bruteForce(points, left, right);
        }

        // 중간점 계산
        int mid = (left + right) / 2;
        Point midPoint = points[mid];

        // 왼쪽과 오른쪽에서의 최소 거리 계산
        double distLeft = closestUtil(points, left, mid);
        double distRight = closestUtil(points, mid + 1, right);

        // 왼쪽과 오른쪽의 최소 거리 중 작은 값 선택
        double distMin = Math.min(distLeft, distRight);

        // 경계 근처에서 더 가까운 두 점이 있는지 확인
        return Math.min(distMin, stripClosest(points, left, right, midPoint, distMin));
    }

    // 브루트포스 방식으로 점이 적을 때 가장 가까운 두 점을 찾는 함수
    private static double bruteForce(Point[] points, int left, int right) {

        double minDist = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double dist = dist(points[i], points[j]);
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist;
    }

    // 경계 근처에서의 점들 중에서 가장 가까운 두 점을 찾는 함수
    private static double stripClosest(Point[] points, int left, int right, Point midPoint, double distMin) {
        // 경계 근처의 점들을 저장할 배열
        Point[] strip = new Point[right - left + 1];
        int j = 0;

        // x 좌표가 중간점 근처에 있는 점들만 strip에 추가
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - midPoint.x) < distMin) {
                strip[j] = points[i];
                j++;
            }
        }

        // strip 배열을 y 좌표 기준으로 정렬
        Arrays.sort(strip, 0, j, Comparator.comparingInt(p -> p.y));

        double min = distMin;

        // y 좌표를 기준으로 거리 조건을 만족하는 점들만 비교
        for (int i = 0; i < j; ++i) {
            for (int k = i + 1; k < j && (strip[k].y - strip[i].y) < min; ++k) {
                double dist = dist(strip[i], strip[k]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    public static void main(String[] args) {

        // 예시 입력: 10개의 점
        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4),
                new Point(7, 8),
                new Point(15, 15),
                new Point(9, 2),
                new Point(8, 4)
        };

        // 가장 가까운 두 점의 거리 제곱값 출력
        System.out.printf("가장 가까운 두 점 사이의 거리 제곱 값: %.2f\n", closestPair(points));
    }
}