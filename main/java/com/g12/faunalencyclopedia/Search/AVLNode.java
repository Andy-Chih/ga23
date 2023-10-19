package com.g12.faunalencyclopedia.Search;

/**
 * @author UID:u7574003 Name: Andy Chih
 */

public class AVLNode<T> {
    T data;
    int height;
    AVLNode<T> left;
    AVLNode<T> right;

    AVLNode(T data){
        this.data = data;
        height =1;
    }

    public T getData() {
        return data;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode getLeft() {
        return left;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
