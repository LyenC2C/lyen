package mul_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyen on 16-9-9.
 */
public class ManyTreeNode {
    /** 树节点*/
    private TreeNode node;
    /** 子树集合*/
    private List<ManyTreeNode> childList;

    /**
     * 构造函数
     *
     * @param node 树节点
     */
    public ManyTreeNode(TreeNode node)
    {
        this.node = node;
        this.childList = new ArrayList<ManyTreeNode>();
    }

    /**
     * 构造函数
     *
     * @param node 树节点
     * @param childList 子树集合
     */
    public ManyTreeNode(TreeNode node, List<ManyTreeNode> childList)
    {
        this.node = node;
        this.childList = childList;
    }

    public TreeNode getNode() {
        return node;
    }

    public void setNode(TreeNode node) {
        this.node = node;
    }

    public List<ManyTreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<ManyTreeNode> childList) {
        this.childList = childList;
    }
}
