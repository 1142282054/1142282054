package service;

import DAO.dao.BasicDAO;
import DAO.domain.Menu;

import java.util.List;

/**
 * @author MinZhi
 * @version 1.0
 */
public class MenuService {
    BasicDAO<Menu> basicDAO = new BasicDAO<>();

    /**
     * 查看菜单
     * @return 菜单集合
     */
   public List<Menu> showMenu(){
       return basicDAO.queryMulti("select * from menu",Menu.class);
   }

    /**
     * 通过菜单编号查找Menu对象
     * @return 菜单对象
     */
   public Menu checkMenu(int menuId){
       return (Menu)basicDAO.querySingle("select * from menu where id = ?"
               ,Menu.class,menuId);
   }
}
