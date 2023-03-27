package com.mygdx.game.helpers;

import com.mygdx.game.nodes.Node;

import java.util.ArrayList;

public class GroupHandler {

    public static final String QUEUEFREE = "queueFree";

    public static ArrayList<String> names = new ArrayList<>();
    public static  ArrayList<ArrayList<Node>> nodes = new ArrayList<ArrayList<Node>>();

    public static void addToGroup(String group, Node obj){
        if (!names.contains(group)){
            names.add(group);
            nodes.add(new ArrayList<Node>());
        }

        nodes.get(names.indexOf(group)).add(obj);
    }

    public static boolean removeFromGroup(String group, Node obj) {
        int pos = names.indexOf(group);
        if (pos == -1){
            System.out.println("Group does not exist");
            System.exit(0);
        }

        return nodes.get(pos).remove(obj);

    }

    public static boolean isInGroup(String group, Node obj){
        int pos = names.indexOf(group);

        if (pos == -1){
            return false;
        }

        return nodes.get(pos).contains(obj);
    }

    public static ArrayList<Node> getNodesInGroup(String group){

        if (!names.contains(group)){
            names.add(group);
            nodes.add(new ArrayList<Node>());
        }

        return nodes.get(names.indexOf(group));
    }

    public static void clearGroup(String group){
        int pos = names.indexOf(group);

        if (pos == -1){
            return;
        }
        nodes.get(names.indexOf(group)).clear();
    }




}
