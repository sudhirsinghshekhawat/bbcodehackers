package com.java.bbblockchain.serviceimpl;

import com.java.bbblockchain.common.VerticleContext;
import com.java.bbblockchain.models.*;
import com.java.bbblockchain.service.BlockchainService;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockchainServiceImpl implements BlockchainService
{
    public BlockchainServiceImpl(VerticleContext verticleContext)
    {

    }

    @Override
    public HashMap<Integer, CollectionCenter> addCollection(int skuId, int quantity)
    {
        CollectionCenter collectionCenter = new CollectionCenter(1,skuId,quantity,100.0,"PROOF1","CREATE_!");
        VerticleContext.collectionCenterHashMap.put(1,collectionCenter);
        System.out.println("added to collection center ");
        VerticleContext.blockChainArrayList.add(new Block(VerticleContext.blockChainArrayList.get(VerticleContext.blockChainArrayList.size()-1).getCurrHash(),collectionCenter.getHash(),collectionCenter));

        return VerticleContext.collectionCenterHashMap;
    }

    @Override
    public HashMap<Integer,DistributionCenter> addDC(DistributionCenter dc)
    {

        VerticleContext.distributionCenterHashMap.put(1,dc);
        VerticleContext.blockChainArrayList.add(new Block(VerticleContext.blockChainArrayList.get(VerticleContext.blockChainArrayList.size()-1).getCurrHash(),dc.getHash(),dc));
        return VerticleContext.distributionCenterHashMap;

    }

    @Override
    public HashMap<Integer, Customer> addCustomer(Customer customer) {
        VerticleContext.customerHashMap.put(101,customer);
        VerticleContext.blockChainArrayList.add(new Block(VerticleContext.blockChainArrayList.get(VerticleContext.blockChainArrayList.size()-1).getCurrHash(),customer.getHash(),customer));
        return VerticleContext.customerHashMap;
    }

   public HashMap<Integer, Order_items> addOrder_Items(Order_items order_items)
    {
        VerticleContext.order_itemsHashMap.put(order_items.getSku_id(),order_items);
        VerticleContext.blockChainArrayList.add(new Block(VerticleContext.blockChainArrayList.get(VerticleContext.blockChainArrayList.size()-1).getPrevHash(),order_items.getHash(),order_items));
        return  VerticleContext.order_itemsHashMap;
    }
}
