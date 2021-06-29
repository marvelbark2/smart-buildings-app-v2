package edu.episen.si.ing1.pds.backend.server.db.orm.eloquent.collection;

import java.util.Collection;

public interface CollectionModel <M>  extends Collection<M> {
    CollectionModel<M> except(Object key);
    CollectionModel<M> modelKeys();
    M find(Object id);
    CollectionModel<M> intersection(CollectionModel<M> b);
    CollectionModel<M> diff(CollectionModel<M> b);
    CollectionModel<M> union(CollectionModel<M> b);
    CollectionModel<M> sortBy(Object b);
}
