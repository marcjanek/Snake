package model;

import java.awt.*;
import java.util.LinkedList;

class GameObject
{
    LinkedList<Point> linkedList;
    LinkedList<Point> freePoints;

    Image image;

    GameObject(LinkedList freePoints)
    {
        this.freePoints = freePoints;
        linkedList = new LinkedList<>();
    }

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

    void clear()
    {
        linkedList.clear();
    }

    void add(Point point)
    {
        linkedList.add(point);
        freePoints.remove(point);
    }

    void setImage(Image image)
    {
        this.image = image;
    }
}
