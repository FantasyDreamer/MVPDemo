package com.example.exercise.utils.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.exercise.modle.LocalDatabase;

import com.example.exercise.utils.greendao.LocalDatabaseDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig localDatabaseDaoConfig;

    private final LocalDatabaseDao localDatabaseDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        localDatabaseDaoConfig = daoConfigMap.get(LocalDatabaseDao.class).clone();
        localDatabaseDaoConfig.initIdentityScope(type);

        localDatabaseDao = new LocalDatabaseDao(localDatabaseDaoConfig, this);

        registerDao(LocalDatabase.class, localDatabaseDao);
    }
    
    public void clear() {
        localDatabaseDaoConfig.clearIdentityScope();
    }

    public LocalDatabaseDao getLocalDatabaseDao() {
        return localDatabaseDao;
    }

}
