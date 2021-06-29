package edu.episen.si.ing1.pds.backend.server.db.orm.eloquent.collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.episen.si.ing1.pds.backend.server.db.orm.eloquent.Models;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.util.*;

public class ICollectionModel<M> implements CollectionModel<M> {
    private List<M> collection;

    public ICollectionModel(List<M> collection) {
        this.collection = collection;
    }

    @Override
    public CollectionModel<M> except(Object key) {
        List<M> bag = new LinkedList<>();
        for (Object o: collection) {
            Models model = (Models) o;
            Map<Object, Models> hm = model.map();
            hm.remove(key);
            M newModel = (M) Utils.jsonMapper.convertValue(hm, Object.class);
            bag.add(newModel);
        }
        return new ICollectionModel<>(bag);
    }

    @Override
    public CollectionModel<M> modelKeys() {
        return null;
    }

    @Override
    public M find(Object id) {
        return null;
    }

    @Override
    public CollectionModel<M> intersection(CollectionModel<M> b) {
        return null;
    }

    @Override
    public CollectionModel<M> diff(CollectionModel<M> b) {
        return null;
    }

    @Override
    public CollectionModel<M> union(CollectionModel<M> b) {
        return null;
    }

    @Override
    public CollectionModel<M> sortBy(Object b) {
        return null;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    public Iterator<M> iterator() {
        return collection.iterator();
    }

    @Override
    public Object[] toArray() {
        return collection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return collection.toArray(ts);
    }

    @Override
    public boolean add(M m) {
        return collection.add(m);
    }

    @Override
    public boolean remove(Object o) {
        return collection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.collection.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends M> collection) {
        return this.collection.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.collection.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.collection.retainAll(collection);
    }

    @Override
    public void clear() {
        collection.clear();
    }


    @Override
    public String toString() {
        try {
            return Utils.jsonMapper.writeValueAsString(collection);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return collection.toString();
        }
    }
}
