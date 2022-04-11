package com.faultaddr.coffeebeggerserver.utils;

import com.faultaddr.coffeebeggerserver.dao.DaoFactory;

import java.util.List;

public class DbHelper<T> {
    public List<T> optionHelper(String sql, T t) {
        DaoFactory<T> daoFactory = new DaoFactory<>();
        List<T> mGameEntityList = null;
        try {
            mGameEntityList = daoFactory.cursor(sql, t.getClass());
            if (mGameEntityList == null) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mGameEntityList;
    }
}
