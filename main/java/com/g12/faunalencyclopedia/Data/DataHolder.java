package com.g12.faunalencyclopedia.Data;

import com.g12.faunalencyclopedia.Search.AVLTree;
/**
 * @author UID:u7630167 Name: Yihang Zhu
 */
public class DataHolder {
    private static final DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private AVLTree<Animal> dataset;

    private DataHolder() {
        dataset = new AVLTree<>();
    }

    public AVLTree<Animal> getDataset() {
        return dataset;
    }

    public void setDataset(AVLTree<Animal> dataset) {
        this.dataset = dataset;
    }

}

