package cn.edu.dule.dao;

import java.util.List;

import cn.edu.dule.beans.BookInfo;
import cn.edu.dule.beans.User;

public interface BookInfoDao extends SuperDao<BookInfo>{

	List<User> getFollowers(int bookInfoId);
}
