public class BST<E extends Comparable<E>> extends AbstractTree<E> {
 protected TreeNode<E> root;
 protected int size = 0;
 

 public void print() {
     print("", this.root, true);
 }

 public void print(String prefix, TreeNode<E> n, boolean isLeft) {
     if (n != null) {
         System.out.println(prefix + (isLeft ? "├── " : " └──") + n.element);
         print(prefix + (isLeft ? "│   " : "    "), n.right, true);
         print(prefix + (isLeft ? "│  " : "   "), n.left, false);
     }
 }
 
 
 public BST() { }

 
 public BST(E[] objects) {
	 for (int i = 0; i < objects.length; i++)
        insert(objects[i]);
 }

 @Override 
 public boolean search(E e) {
      TreeNode<E> current = root; 
      while (current != null) {
    	  if (e.compareTo(current.element) < 0) 
    		  current = current.left;
    	  else if (e.compareTo(current.element) > 0) 
    		  	current = current.right;
    	  else 
    		  	return true; 
      }
      return false;
 }

 protected TreeNode<E> createNewNode(E e) {
	 return new TreeNode<>(e);
 }
 
 @Override 
 public boolean insert(E e) {
	 if (root == null)
        root = createNewNode(e); 
	 else {
		 
		 TreeNode<E> parent = null;
		 TreeNode<E> current = root;
		 while (current != null)
			 if (e.compareTo(current.element) < 0) {
				 parent = current;
				 current = current.left;
			 } else if (e.compareTo(current.element) > 0) {
				 parent = current;
				 current = current.right;
			 } else
				 return false; 

		 	
		 if (e.compareTo(parent.element) < 0)
			 parent.left = createNewNode(e);
		 else
			 parent.right = createNewNode(e);
	 }

	size++; 
	return true; 
 }

 @Override 
 public void inorder() {
      inorder(root);
 }

 
 protected void inorder(TreeNode<E> root) {
	 if (root == null) return;
     inorder(root.left);
     System.out.print("(" + root.element + ") ");
     inorder(root.right);
 }

 @Override 
 public void postorder() {
      postorder(root);
 }

  
  protected void postorder(TreeNode<E> root) {
	 if (root == null) return;
	 postorder(root.left);
	 postorder(root.right);
     System.out.print("(" + root.element + ") ");
 }

 @Override 
 public void preorder() {
      preorder(root);
 }

 
 protected void preorder(TreeNode<E> root) {
	 if (root == null) return;
     System.out.print("(" + root.element + ") ");
	 preorder(root.left);
	 preorder(root.right);
 }

 @Override 
 public int getSize() {
	  return size;
 }

  
  public TreeNode<E> getRoot() {
	  return root;
  }

  
  public void clear() {
      root = null;
      size = 0;
   }

  
  public java.util.ArrayList<TreeNode<E>> path(E e) {
	java.util.ArrayList<TreeNode<E>> list = new java.util.ArrayList<>();
	TreeNode<E> current = root; 
	boolean found = false;
	while (current != null && !found) {
		list.add(current); 
		if (e.compareTo(current.element) < 0) 
			current = current.left;
		else if (e.compareTo(current.element) > 0) 
          current = current.right;
        else found = true;
	}

	return list; 
 }

 @Override 
 public boolean delete(E e) {
	 
	TreeNode<E> parent = null;
	TreeNode<E> current = root; 
	boolean found = false;
	while (current != null && !found) {
		if (e.compareTo(current.element) < 0) {
          parent = current;
          current = current.left;
        } else if (e.compareTo(current.element) > 0) {
          parent = current;
          current = current.right;
        } else
        	found = true ; 
	}

	if (found) {
		
		if (current.left == null) {
			
			if (parent == null) 
				root = current.right;
	        else if (e.compareTo(parent.element) < 0)
	        		parent.left = current.right; 
	        	else
	        		parent.right = current.right; 
	     } else {
			
	        TreeNode<E> parentOfRightMost = current; 
	        TreeNode<E> rightMost = current.left; 
	
	        while (rightMost.right != null) {
	        	parentOfRightMost = rightMost;
	        	rightMost = rightMost.right; 
	        }
	
	       
	        current.element = rightMost.element; 
	
	        
	        if (parentOfRightMost.right == rightMost)
	        	parentOfRightMost.right = rightMost.left;
	        else
	        	
	        	parentOfRightMost.left = rightMost.left;
		}
		size--;
	}
	return found; 
 }

 @Override
 public java.util.Iterator<E> iterator() {
	 return new InorderIterator();
    }

 
 private class InorderIterator implements java.util.Iterator<E> {
 
	 private java.util.ArrayList<E> list = new java.util.ArrayList<>();
	 private int current = 0; 

	 public InorderIterator() {
		 inorder(); 
	}

	 
	 private void inorder() {
		 inorder(root); 
	}

	
	private void inorder(TreeNode<E> root) {
		if (root == null) return;
        inorder(root.left);
        list.add(root.element);
        inorder(root.right);
	}
	
 @Override 
 public boolean hasNext() {
	 if (current < list.size())
		 return true;
	 return false;
 }

 @Override 
 public E next() {
	return list.get(current++);
 }

 @Override 
 public void remove() {
	delete(list.get(current)); 
	list.clear(); 
	inorder(); 
	}
 }
}