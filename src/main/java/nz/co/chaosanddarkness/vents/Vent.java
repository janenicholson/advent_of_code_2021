package nz.co.chaosanddarkness.vents;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Vent {

    private final Point start;
    private final Point finish;

    public Vent(String startX, String startY, String finishX, String finishY) {
        start = new Point(Integer.parseInt(startX), Integer.parseInt(startY));
        finish = new Point(Integer.parseInt(finishX), Integer.parseInt(finishY));
    }

    public boolean isVertical() {
        return start.getX().equals(finish.getX());
    }

    public boolean isHorizontal() {
        return start.getY().equals(finish.getY());
    }

    public List<Point> points() {
        int ventLength = Math.max(Math.abs(finish.getX() - start.getX()), Math.abs(finish.getY() - start.getY()));
        int stepX = (finish.getX() - start.getX()) / ventLength;
        int stepY = (finish.getY() - start.getY()) / ventLength;
        List<Point> points = new ArrayList<>(ventLength+1);
        for (int i = 0; i <= ventLength; i++) {
            points.add(new Point(start.getX() + stepX * i, start.getY() + stepY * i));
        }
        return points;
    }

}
