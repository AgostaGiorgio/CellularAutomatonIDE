package testing_package;

import graph.Cell;

import java.awt.*;
import java.util.ArrayList;

public class ListCell extends Cell {
    public ArrayList<Integer> ngs;
    public int myId;

    public ListCell(int a, Color c, int...V) {
        ngs = new ArrayList<Integer>();
        myId = a;
        super.state = c;
        for(int v : V)
            ngs.add(v);
    }

    @Override
    public int getKthNeighbor(int k) {
        if(k == 0) return myId;
        return ngs.get(k-1);
    }

    @Override
    public int getNumNeighbors() {
        return ngs.size();
    }
}
