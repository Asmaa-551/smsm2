
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

        EnvironmentalData foundData = searchByLocation(node.left, location);
        if (foundData != null) {
            return foundData; 
        }

        return searchByLocation(node.right, location); 
    }
}


