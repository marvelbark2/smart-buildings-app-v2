package edu.episen.si.ing1.pds.client.swing.cards.models;

import javax.swing.*;
import java.util.*;

public class SortedListModel extends AbstractListModel {
    List model;

    public SortedListModel() {
        model = new ArrayList();
    }

    public int getSize() {
        return model.size();
    }

    public Object getElementAt(int index) {
        return model.toArray()[index];
    }

    public void add(Object element) {
        if (model.add(element)) {
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void addAll(Object elements[]) {
        Collection c = Arrays.asList(elements);
        model.addAll(c);
        fireContentsChanged(this, 0, getSize());
    }

    public void clear() {
        model.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean contains(Object element) {
        return model.contains(element);
    }

    public Object firstElement() {
        return model.get(0);
    }

    public Iterator iterator() {
        return model.iterator();
    }

    public Object lastElement() {
        return model.get(model.size() - 1);
    }

    public boolean removeElement(Object element) {
        boolean removed = model.remove(element);
        if (removed) {
            fireContentsChanged(this, 0, getSize());
        }
        return removed;
    }
}