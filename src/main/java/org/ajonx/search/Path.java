package org.ajonx.search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Path {
	public Point point;
	public List<Point> path;

	public Path(Point current, Point prev, List<Point> path) {
		this.point = current;
		this.path = new ArrayList<>(path);
		if (prev != null) this.path.add(prev);
	}

	public int size() {
		return 1 + path.size();
	}

	public int cost(int ex, int ey) {
		return size() + Math.abs(point.x - ex) + Math.abs(point.y + ey);
	}
}