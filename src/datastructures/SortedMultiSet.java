/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author PC
 * @param <T>
 */
public class SortedMultiSet<T extends Comparable> implements Iterable<T>{
    private SortedMap<T,Integer> map;
    private int size;
    
    public SortedMultiSet(){
        map = new TreeMap<>();
        size = 0;
    }
    
    public String toString(){
        return map.toString();
    }
    
    public void add( T e ){
        map.put( e, (map.containsKey(e) ? map.get(e) + 1 : 1) );
        size++;
    }
    
    public boolean contains( T e ){
        return map.containsKey(e);
    }
    
    public T first(){
        return map.firstKey();
    }
    
    public T last(){
        return map.lastKey();
    }
    
    public int size(){
        return size;
    }
    
    public void clear(){
        map.clear();
        size = 0;
    }
    
    public void addAll( SortedMultiSet<T> c ){
        for( T e : c ){
            add( e );
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedMultiSetIterator();
    }
    
    class SortedMultiSetIterator implements Iterator<T>{
        
        Iterator<T> mapIterator;
        int count;
        T aux = null;
        
        public SortedMultiSetIterator(){
            
            mapIterator = map.keySet().iterator();
            aux = mapIterator.next();
            count = map.get(aux);
        }

        @Override
        public boolean hasNext() {
            return mapIterator.hasNext() ? true : count > 0;
        }
        
        public void remove(){}

        @Override
        public T next() {
            
            if( count <= 0 ){
                aux = mapIterator.next();
                count = map.get(aux);
            }
                
            count--;
            return aux;
        }
    }
}
