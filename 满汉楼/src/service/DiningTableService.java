package service;


import DAO.dao.BasicDAO;
import DAO.domain.DiningTable;

import java.util.List;

/**
 * @author MinZhi
 * @version 1.0
 */
public class DiningTableService {
    BasicDAO<DiningTable> basicDAO = new BasicDAO<>();

    /**
     * 查看预定餐桌信息
     * @return 餐桌列表，用于显示状态
     */
    public List<DiningTable> showDiningState(){
        return basicDAO.queryMulti("select * from diningTable",DiningTable.class);
    }

    /**
     * 检查是否有这张桌子且状态为空
     * @param tableId 桌子的id
     * @return 桌子对象，不为空则有
     */
    public DiningTable checkDiningTable(int tableId){
        // 检查是否有这张桌子
        return (DiningTable) basicDAO.querySingle("select * from diningTable where id = ? and state = '空'",
                DiningTable.class, tableId);
    }

    /**
     * 预定餐桌功能
     * @param name 预订人名称
     * @param tel 预订人电话
     * @param tableId 预定餐桌号
     * @return 成功为true，失败为false
     */
    public boolean bookDiningTable(String name,String tel,int tableId){
        int update = basicDAO.update("update diningTable set state = '预定', orderName = ?,  orderTel = ? where id = ?"
                , name, tel, tableId);
        return update > 0;
    }

    /**
     * 检查桌子是否存在，存在就修改桌子状态
     * @param tableId 餐桌单号
     * @return 餐桌对象
     */
    public DiningTable checkTable(int tableId){
        DiningTable diningTable = (DiningTable) basicDAO.querySingle("select * from diningTable where id = ?"
                , DiningTable.class, tableId);
        if (diningTable == null){
            return null;
        }
        basicDAO.update("update diningTable set state = '用餐中' where id = ?",tableId);
        return diningTable;
    }
    public boolean reSetTable(int tableId){
        return basicDAO.update("update diningTable set state = '空' where id = ?",tableId)>0;
    }
}
