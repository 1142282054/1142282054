package service;

import DAO.dao.BasicDAO;
import DAO.domain.Bill;
import DAO.domain.DiningTable;
import DAO.domain.Menu;

import java.util.List;
import java.util.UUID;

/**
 * @author MinZhi
 * @version 1.0
 */
public class BillService {
    BasicDAO<Bill> basicDAO = new BasicDAO<>();
    MenuService menuService = new MenuService();
    DiningTableService diningTableService = new DiningTableService();

    /**
     * 点餐功能
     * @param tableId 座位号
     * @param menuId 菜品号
     * @param nums 数量
     * @return 成功为true，失败则false
     */
    public boolean order(int tableId,int menuId,int nums){
        String billId = UUID.randomUUID().toString();
        Menu menu = menuService.checkMenu(menuId);
        DiningTable diningTable = diningTableService.checkTable(tableId);
        if (menu == null){
            System.out.println("菜品不存在~");
            return false;
        }
        if (diningTable == null){
            System.out.println("餐桌不存在~");
            return false;
        }
        double money = menu.getPrice() * nums;
        int update = basicDAO.update("insert into bill values(null,?,?,?,?,?,now(),'未支付')"
                , billId, menuId, nums, money, tableId);
        return update>0;
    }
    public List<Bill> showBill(){
        return basicDAO.queryMulti("select * from bill",Bill.class);
    }
    public boolean payBill(int tableId,String payMethod){
        if (!diningTableService.reSetTable(tableId)){
            return false;
        }
        int update = basicDAO.update("update bill set state = ? where diningTable = ?", payMethod, tableId);
        return update > 0;
    }
}
