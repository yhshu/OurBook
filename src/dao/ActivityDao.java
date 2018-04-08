package dao;

import model.Movement;

public interface ActivityDao {
    /**
     * 根据用户名查找用户的动态
     *@param  name 用户名
     *@return 该用户的动态
     */
    Movement[]  findByName(String name);
    /**
     * 添加动态
     */
    void add (Movement movement);
}
