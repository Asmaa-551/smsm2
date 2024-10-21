public class testBST {

	public static void main(String[] args) {
		BST_Integer tree1, tree2;
		int[] intArray = {18, 4, 10, 60, 40, 100, 2, 1, 200, 250, 30, 3, 5, 6, 150, 45};
		//Creating the special tree1 as shown in the figure provided in the assignment
		tree1 = new BST_Integer(intArray);
		tree2 = new BST_Integer(intArray);
		tree2.delete(4);
		
		System.out.print("InOrder : ");  tree1.inorder();System.out.print("\n"); 
		System.out.print("postOrder : ");  tree1.postorder();System.out.print("\n"); 
		System.out.print("PreOrder : ");  tree1.preorder();System.out.print("\n"); 
		
		System.out.println("Tree1: "); 
		tree1.print();

	}

}
