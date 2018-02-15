package com.example.team9.flashbackmusic_team9;

import java.util.Stack;

/**
 * Created by cyrusdeng on 13/02/2018.
 */

public class Updateables {
    private static Stack<Updateable> items = new Stack<>();
    public static void addUpdateable(Updateable toAdd) {
        items.add(toAdd);
    }
    public static void updateAll() {
        for( Updateable each : items ) {
            each.update();
        }
    }
    public static void popItem() {
        items.pop();
    }
}
