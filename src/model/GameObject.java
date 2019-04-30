package model;

import java.awt.*;
import java.util.LinkedList;

class GameObject
{
    LinkedList<Point> linkedList;
    LinkedList<Point> freePoints;

    Color borderColor;

    int getSize()
    {
        return linkedList.size();
    }

    Point get(int index)
    {
        return linkedList.get(index);
    }

    boolean contains(Point point)
    {
        return linkedList.contains(point);
    }

    GameObject(Color borderColor, Color backgroundColor, LinkedList freePoints)
    {
        this.freePoints = freePoints;
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        linkedList = new LinkedList<>();
    }

    void clear()
    {
        linkedList.clear();
    }

    Color backgroundColor;

    void add(Point point)
    {
        linkedList.add(point);
        freePoints.remove(point);
    }
}
