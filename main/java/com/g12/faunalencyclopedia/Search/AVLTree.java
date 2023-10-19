package com.g12.faunalencyclopedia.Search;


import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author UID:u7574003 Name: Andy Chih
 */
 
public class AVLTree<T> {
    private AVLNode<T> root;
    private int size;
    //Andrew: Added a comparator for comparisons.
    private final Comparator<T> comparator;

    public AVLTree() {
        this.comparator = null;
    }
    public AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    // Utility method to get the height of the tree
    private int height(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    private AVLNode<T> remove(AVLNode<T> node, T data) {
        if (node == null)
            return null;

        int cmp = compare(data, node.getData());

        if (cmp < 0) {
            node.setLeft(remove(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(remove(node.getRight(), data));
        } else {
            // Node to be removed found

            // Case 1: Node with only one child or no child
            if (node.getLeft() == null || node.getRight() == null) {
                AVLNode<T> temp = (node.getLeft() != null) ? node.getLeft() : node.getRight();

                // No child case
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    // One child case
                    node = temp;
                }
            } else {
                // Case 2: Node with two children
                AVLNode<T> successor = findMin(node.getRight());
                node.setData(successor.getData());
                node.setRight(remove(node.getRight(), successor.getData()));
            }
        }

        // Update height and balance factor
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        int balance = getBalance(node);

        // Balancing cases
        if (balance > 1) {
            if (compare(data, (T) node.getLeft().getData()) < 0) {
                return rightRotate(node);
            } else {
                node.setLeft(leftRotate(node.getLeft()));
                return rightRotate(node);
            }
        }

        if (balance < -1) {
            if (compare(data, (T) node.getRight().getData()) > 0) {
                return leftRotate(node);
            } else {
                node.setRight(rightRotate(node.getRight()));
                return leftRotate(node);
            }
        }

        return node;
    }

    private AVLNode<T> findMin(AVLNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }





    // Utility method to get the balance factor of a node
    private int getBalance(AVLNode node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    // Right rotate
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T3 = x.right;

        x.right = y;
        y.left = T3;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    //Andrew: Added a new add method to insert nodes easier.
    public void add (T data){
        root = insert(root, data);
    }

    // Insert a node
    //Andrew: Changed the insert method to private since we won't use this publicly
    private AVLNode insert(AVLNode<T> node, T data) {
        if (node == null) return new AVLNode(data);

        if (compare(data, (T) node.data) < 0) {
            //if (data.compareTo((T) node.data) < 0) {
            node.left = insert(node.left, data);
            //} else if (data.compareTo((T) node.data) > 0) {
        } else if (compare(data, (T) node.data) > 0){
            node.right = insert(node.right, data);
        } else {
            return node; // Duplicate data is not allowed
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // Left Heavy
        if (balance > 1) {
            if (/*data.compareTo((T) node.left.data) < 0*/compare(data, (T) node.left.data) < 0) {
                return rightRotate(node);
            } else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        // Right Heavy
        if (balance < -1) {
            if (/*data.compareTo((T) node.right.data) > 0*/compare(data, (T) node.right.data) > 0) {
                return leftRotate(node);
            } else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }

    /**
     * @author UID:u7630167 Name: Yihang Zhu
     */
    // Andrew: The compare method
    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else if (a instanceof Comparable) {
            return ((Comparable<T>) a).compareTo(b);
        } else {
            throw new RuntimeException("Cannot compare objects. Neither a Comparator is provided, nor the objects are Comparable.");
        }
    }

    // Andrew: Methods to traverse the tree
    public void inorder(Consumer<T> action) {
        inorderRec(root, action);
    }
    private void inorderRec(AVLNode<T> node, Consumer<T> action) {
        if (node != null) {
            inorderRec(node.left, action);
            action.accept(node.data);
            inorderRec(node.right, action);
        }
    }
    public Optional<T> get(Predicate<T> condition) {
        AVLNode<T> foundNode = findInOrder(root, condition);
        return Optional.ofNullable(foundNode != null ? foundNode.data : null);
    }
    private AVLNode<T> findInOrder(AVLNode<T> node, Predicate<T> condition) {
        if (node == null) return null;
        AVLNode<T> foundNode = findInOrder(node.left, condition);
        if (foundNode != null) return foundNode;

        if (condition.test(node.data)) return node;

        return findInOrder(node.right, condition);
    }
    public boolean isEmpty() {
        return root == null;
    }

}
