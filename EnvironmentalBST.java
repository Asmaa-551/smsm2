
public class EnvironmentalBST extends BST<EnvironmentalData>{

    public EnvironmentalBST(){}
	public EnvironmentalBST(EnvironmentalData[] array) {
        for (int i = 0; i < array.length; i++)
           insert(array[i]);
    }

    public EnvironmentalData searchByLocation(String location) {
        return searchByLocation(root, location);
    }

    private EnvironmentalData searchByLocation(TreeNode<EnvironmentalData> node, String location) {
        if (node == null) {
            return null; 
        }

        EnvironmentalData data = node.element;

        if (data.getLocationName().equals(location)) {
            return data; 
        }

        if (location.compareTo(data.getLocationName()) < 0) {
            return searchByLocation(node.left, location);
        } else { 
            return searchByLocation(node.right, location);
        }


    }

    
    public boolean delete(String location) {
        root = deleteRec(root, location);
        return root != null; // Return true if deletion was successful
    }
    
    private TreeNode<EnvironmentalData> deleteRec(TreeNode<EnvironmentalData> node, String location) {
        if (node == null) {
            return null; // Base case: not found
        }
    
        EnvironmentalData data = node.element;
    
        if (location.compareTo(data.getLocationName()) < 0) {
            node.left = deleteRec(node.left, location); // Go left
        } else if (location.compareTo(data.getLocationName()) > 0) {
            node.right = deleteRec(node.right, location); // Go right
        } else {
            // Node to be deleted found
            if (node.left == null) {
                return node.right; // No left child
            } else if (node.right == null) {
                return node.left; // No right child
            }
    
            // Node with two children: Get the inorder successor (smallest in the right subtree)
            node.element = minValue(node.right);
            node.right = deleteRec(node.right, node.element.getLocationName()); // Delete the inorder successor
        }
        return node;
    }
    
    private EnvironmentalData minValue(TreeNode<EnvironmentalData> node) {
        EnvironmentalData minValue = node.element;
        while (node.left != null) {
            minValue = node.left.element;
            node = node.left;
        }
        return minValue;
    }
    
    public EnvironmentalBST cloneTree() {
        EnvironmentalBST clone = new EnvironmentalBST();
        clone.root = cloneRec(root);
        return clone;
    }
    
    private TreeNode<EnvironmentalData> cloneRec(TreeNode<EnvironmentalData> node) {
        if (node == null) {
            return null;
        }
    
        TreeNode<EnvironmentalData> newNode = new TreeNode<>(node.element);
        newNode.left = cloneRec(node.left);
        newNode.right = cloneRec(node.right);
        return newNode;
    }
    
    public void restoreFromBackup(EnvironmentalBST backup) {
        this.root = backup.cloneTree().root;
    }    
  
        public void reverseInOrderTraversal() {
            reverseInOrderTraversalRec(root);
        }
    
        void reverseInOrderTraversalRec(TreeNode<EnvironmentalData> node) {
            if (node != null) {
                reverseInOrderTraversalRec(node.right);
                node.element.displayInfo(); 
                reverseInOrderTraversalRec(node.left);
            }
        }
        public void reverseInorder(TreeNode<EnvironmentalData> root) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'reverseInorder'");
        }
        public void reverseInOrder(TreeNode<EnvironmentalData> root) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'reverseInOrder'");
        }
    }


