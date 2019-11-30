package otherutil;


import java.util.List;
import java.util.Map;

/**
 * Mybatis模板基类
 */
public interface MybatisDao <E, Q, Id>{


    /**
     *  根据id查询
     * @param id
     * @return
     */
    E selectModelById(Id id);

    /**
     * 查询全部
     * @return
     */
    List<E> selectList();

    /**
     * 根据指定条件查询
     * Map内可选参数如下:
     * ids (集合/数组): 传入多个id; in查询
     * id : 主键查询
     * sqlWhere : 自定义条件的sql语句,类似于" and 1=1 "
     * sqlOrderBy : 排序条件
     * @param param
     * @return
     */
    List<E> selectListByMap(Map param);

    /**
     * 完全自定义查询
     * Map内可选参数如下:
     * sqlWhere : 自定义条件的sql语句,类似于" and 1=1 "
     * sqlOrderBy : 排序条件
     * @param param
     * @return
     */
    List<E> selectListByWhere(Map param);

    /**
     * 指定id查询
     * @param idList
     * @return
     */
    List<E> selectListByIds(List<Id> idList);

    /**
     * 根据查询对象,分页查询
     * @param q
     * @return
     */
    List<E> selectListByQuery(Q q);

    /**
     * 根据查询对象,查询总记录
     * @param q
     * @return
     */
    int selectCountByQuery(Q q);

    /**
     * 查询全部数量
     * @return
     */
    int selectCount();

    /**
     * 新增一条数据
     * @param entity
     * @return
     */
    int insert(E entity);

    /**
     * 新增多个记录
     * @param entityList
     * @return
     */
    int insertList(List<E> entityList);

    /**
     * 修改一个记录
     * @param entity
     * @return
     */
    int update(E entity);

    /**
     * 修改多个记录
     * @param entityList
     * @return
     */
    int updateList(List<E> entityList);

    /**
     * 修改一个非空记录
     * @param entity
     * @return
     */
    int updateNotNull(E entity);

    /**
     * 修改多个非空记录
     * @param entityList
     * @return
     */
    int updateListNotNull(List<E> entityList);

    /**
     * 根据条件修改
     * @param map
     * @return
     */
    int updateByMap(Map map);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(Id id);

    /**
     * 根据多个id删除
     * @param idList
     * @return
     */
    int deleteByIds(List<Id> idList);

    /**
     * 根据条件删除
     * @param map
     * @return
     */
    int deleteByMap(Map map);

    /**
     * 判断一个id是否存在
     * @param id
     * @return
     */
    boolean selectExistsById(Id id);

    /**
     * 判断多个id是否存在
     * @param idList
     * @return
     */
    int selectAllExistsByIds(List<Id> idList);


}
