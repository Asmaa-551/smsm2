public class EnvironmentalBST extends BST<EnvironmentalData>{
    
    public EnvironmentalBST(){}
	public EnvironmentalBST(EnvironmentalData[] array) {
        for (int i = 0; i < array.length; i++)
           insert(array[i]);
    }



}
