package dao;

public interface ActivityDao {
    /**
     * 根据用户名查找用户的动态
     *@param  name 用户名
     *@return 该用户的动态
     */
    movement[]  findByName(String name);
}
