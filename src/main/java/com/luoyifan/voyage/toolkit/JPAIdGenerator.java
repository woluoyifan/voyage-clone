package com.luoyifan.voyage.toolkit;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author EvanLuo
  */
public class JPAIdGenerator implements IdentifierGenerator {
    /**
     * 主机和进程的机器码
     */
    public static final Sequence worker = new Sequence();

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return worker.nextId();
    }

}
