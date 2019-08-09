package com.java.bbblockchain.service;

import com.java.bbblockchain.models.CollectionCenter;
import com.java.bbblockchain.models.Customer;
import com.java.bbblockchain.models.DistributionCenter;
import com.java.bbblockchain.models.Order_items;

import java.util.HashMap;

public interface BlockchainService
{
    HashMap<Integer, CollectionCenter> addCollection(int skuId,int quantity);

    HashMap<Integer, DistributionCenter> addDC(DistributionCenter dc);

    HashMap<Integer, Customer> addCustomer(Customer customer);

    HashMap<Integer, Order_items> addOrder_Items(Order_items order_items);





}
