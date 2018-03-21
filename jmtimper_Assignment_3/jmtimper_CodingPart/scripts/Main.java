package scripts;

import java.io.*;
import java.util.Scanner;

/**
 * runs program
 * @author Jeremy Timperio
 */
public class Main {

    /** first node of tree */
    TreeNode root;

    /**
     * main that runs program and takes in command line arguments
     * @param args command line arguements
     */
    public static void main(String[] args) {
        Main main = new Main();
        if(args[0].equals("read"))
            main.read(args[1]);
        else if(args[0].substring(0, 5).equals("edge(")){
            String line = args[0].substring(5,args[0].length()-1);
            String vals[] = line.split(",");
            main.edge(vals[0].trim(), vals[1].trim(), vals[2].trim());
        } else if(args[0].substring(0, 4).equals("rel(")){
            String line = args[0].substring(4,args[0].length()-1);
            String vals[] = line.split(",");
            System.out.println(main.rel(vals[0].trim(), vals[1].trim()));
        } else if(args[0].substring(0, 9).equals("property(")) {
            String line = args[0].substring(9, args[0].length() - 1);
            String vals[] = line.split(",");
            System.out.println(main.property(vals[0].trim(), vals[1].trim(), vals[2].trim()));
        } else if(args[0].substring(0, 4).equals("get(")) {
            String line = args[0].substring(4, args[0].length() - 1);
            String vals[] = line.split(",");
            System.out.println(main.getProperty(vals[0].trim(), vals[1].trim()));
        } else if(args[0].substring(0, 7).equals("getAll(")) {
            String line = args[0].substring(7, args[0].length() - 1);
            String vals[] = line.split(",");
            System.out.println(main.getAllProperty(vals[0].trim()));
        } else if(args[0].substring(0, 5).equals("info(")) {
            String line = args[0].substring(5, args[0].length() - 1);
            String vals[] = line.split(",");
            System.out.println(main.info(vals[0].trim()));
        } else if(args[0].equals("help")){
            System.out.println("Commands:");
            System.out.println("read <filepath> - reads file of commands");
            System.out.println("edge(<sourceNode>, <linkType>, <destinationNode>) - adds edge with linkType");
            System.out.println("rel(<sourceNode>, <destinationNode>) - prints relationship between nodes");
            System.out.println("property(<node>, <type>, <value>) - adds property to node");
            System.out.println("get(<node>, <type>) - returns property value based on type");
            System.out.println("getAll(<node>) - returns all properties including inherited properties");
            System.out.println("info(<node>) - returns all information of the node");
        } else {
            System.out.println("Invalid Command: " + args[0]);
        }
    }

    /**
     * reads file and performs commands from the textfile
     * @param filepath path to file
     */
    public void read(String filepath){
        try {
            Scanner in = new Scanner(new FileReader(filepath));
            while(in.hasNextLine()) {
                String line = in.nextLine();
                if (line.substring(0, 5).equals("edge(")) {
                    line = line.substring(5, line.length() - 1);
                    String vals[] = line.split(",");
                    edge(vals[0].trim(), vals[1].trim(), vals[2].trim());
                } else if (line.substring(0, 4).equals("rel(")) {
                    line = line.substring(4, line.length() - 1);
                    String vals[] = line.split(",");
                    System.out.println(rel(vals[0].trim(), vals[1].trim()));
                } else if (line.substring(0, 9).equals("property(")) {
                    line = line.substring(9, line.length() - 1);
                    String vals[] = line.split(",");
                    System.out.println(property(vals[0].trim(), vals[1].trim(), vals[2].trim()));
                } else if(line.substring(0, 4).equals("get(")) {
                    line = line.substring(4, line.length() - 1);
                    String vals[] = line.split(",");
                    System.out.println(getProperty(vals[0].trim(), vals[1].trim()));
                } else if(line.substring(0, 7).equals("getAll(")) {
                    line = line.substring(7, line.length() - 1);
                    String vals[] = line.split(",");
                    System.out.println(getAllProperty(vals[0].trim()));
                } else if(line.substring(0, 5).equals("info(")) {
                    line = line.substring(5, line.length() - 1);
                    String vals[] = line.split(",");
                    System.out.println(info(vals[0].trim()));
                } else {
                    System.out.println("Invalid Command: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Entered File:" + filepath + " Not Found");
        }
    }

    /**
     * adds edge to tree based on params
     * @param sourceNode Node to be added
     * @param linkType relationship between the two
     * @param destinationNode parent node
     */
    private void edge(String sourceNode, String linkType, String destinationNode){
        if(root == null){
            root = new TreeNode(destinationNode);
        }
        TreeNode des = getNode(root, destinationNode);
        TreeNode src = getNode(root, sourceNode);
        if(src == null){
            src = new TreeNode(sourceNode);
        }
        src.setParent(des);
        src.setRelationship(linkType);

        des.addChild(src);
    }

    /**
     * Prints out the relationship between two nodes
     * @param srcNode start node
     * @param desNode end node
     * @return returns string of relationship
     */
    private String rel(String srcNode, String desNode){
        TreeNode src = getNode(root, srcNode);
        if(src == null){
            return "Source Node does not exist!";
        }
        TreeNode temp = src;
        String str = "";
        while(!temp.nodeName.equals(desNode) && temp != root){
            str += src.nodeName + " " + temp.getRelationship() + " " + temp.parent.nodeName + ". ";
            temp = temp.parent;
        }
        return str.trim();
    }

    /**
     * adds property to node
     * @param node TreeNode
     * @param type type of property
     * @param value property added
     * @return String of property added
     */
    private String property(String node, String type, String value){
        TreeNode src = getNode(root, node);
        if(src != null) {
            src.addProperty(type, value);
            if(type.equals("travel")){
                return src.nodeName + " travels by " + src.getProperty(type) + ".";
            } else {
                return src.nodeName + " " + type + ": " + src.getProperty(type) + ".";
            }
        } else {
            return "Source Node does not exist!";
        }
    }

    /**
     * Returns property value of node
     * @param srcNode String name of src node
     * @param type String property type
     * @return String
     */
    private String getProperty(String srcNode, String type){
        TreeNode src = getNode(root, srcNode);
        if(src != null) {
            if(type.equals("travel")){
                return src.nodeName + " travels by " + src.getProperty(type) + ".";
            } else {
                return src.nodeName + " " + type + "- " + src.getProperty(type) + ".";
            }
        } else {
            return "Source Node does not exist!";
        }
    }

    /**
     * returns all properties of given node
     * @param srcNode String name of node to get
     * @return String of properties
     */
    private String getAllProperty(String srcNode){
        TreeNode src = getNode(root, srcNode);
        if(src != null) {
            return src.nodeName + " properties: " + src.getAllProperty();
        } else {
            return "Source Node does not exist!";
        }
    }

    private String info(String srcNode){
        TreeNode src = getNode(root, srcNode);
        if(src == null) return "Node does not exist.";
        String val = "Name: " + src.nodeName + "\n";
        val += "Relationship: " + rel(src.nodeName, root.nodeName) + "\n";
        val += getAllProperty(srcNode);
        return val;
    }

    /**
     * returns the node in the tree
     * @param cur starting node to search from
     * @param name name being searched for
     * @return node of name or Null if not found
     */
    private TreeNode getNode(TreeNode cur, String name){
        if(cur.nodeName.equals(name)){
            return cur;
        }
        if(cur.children.size() != 0){
            for(int i = 0; i < cur.children.size(); i++){
                TreeNode temp = getNode(cur.children.get(i), name);
                if(temp != null){
                    return temp;
                }
            }
        }
        return null;
    }
}