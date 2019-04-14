package model;

import java.awt.*;
import java.util.LinkedList;

class GameObject
{
    LinkedList<Point> linkedList;

    GameObject()
    {
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

    void add(Point point)
    {
        linkedList.add(point);
    }

    void clear()
    {
        linkedList.clear();
    }
}
