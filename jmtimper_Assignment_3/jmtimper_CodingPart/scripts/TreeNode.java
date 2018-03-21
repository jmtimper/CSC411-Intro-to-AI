package scripts;

import java.lang.reflect.Array;
import java.util.*;

/**
 * TreeNode Data structure for Taxonomy
 * @author Jeremy Timperio
 */
public class TreeNode {

    /** name of node */
    String nodeName = "";

    /**relationship with parent */
    String relationship = "";

    /** parent node refernce*/
    TreeNode parent = null;

    /** properties hashmap for type and value*/
    Map<String, String> properties = new HashMap<>();

    /**list of children */
    ArrayList<TreeNode> children = new ArrayList<>();

    /**
     * tree node constructor
     */
    public TreeNode(String rootName){
        this.nodeName = rootName;
    }

    /**
     * sets relationship
     * @param relationship string to set
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /**
     * sets parent reference
     * @param parent TreeNode parent
     */
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     * adds child to list of children
     * @param child TreeNode Child
     */
    public void addChild(TreeNode child){
        children.add(child);
    }

    /**
     * adds property to hashmap
     * @param type String
     * @param val String
     */
    public void addProperty(String type, String val){
        properties.put(type, val);
    }

    /**
     * gets property based on type
     * @param type String type
     * @return String of property
     */
    public String getProperty(String type){
        String val = "";
        if(properties != null)
            val = properties.get(type);
        if(val == null || val.equals("")){
            if(parent != null) {
                return parent.getProperty(type);
            }else{
                return "None";
            }
        } else {
            return val;
        }
    }

    /**
     * returns all properties of node
     * @return String of all properties
     */
    public String getAllProperty(){
        String val = "";
        ArrayList<String> types = new ArrayList<>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            types.add(entry.getKey());
            val += entry.getKey() + "-" + entry.getValue() + ". ";
        }
        return val + parent.getAllHelper(types);
    }

    /**
     * recursive helper for get all property inheritance
     * @param types Arraylist of already set properties
     * @return String of properties
     */
    private String getAllHelper(ArrayList<String> types){
        String val = "";
        boolean contains = false;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            contains = false;
            for(int i = 0; i < types.size(); i++){
                if(types.get(i).equals(entry.getKey())){
                    contains = true;
                }
            }
            if(!contains) {
                types.add(entry.getKey());
                val += entry.getKey() + "-" + entry.getValue() + ". ";
            }
        }
        if(parent != null)
            return val + parent.getAllHelper(types);
        return val;
    }

    /**
     * return string for relationship
     * @return String
     */
    public String getRelationship() {
        if(relationship.equals("isa"))
            return "is a";
        else{
            return "is a kind of";
        }
    }
}
