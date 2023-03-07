package view;

import DAO.domain.Bill;
import DAO.domain.DiningTable;
import DAO.domain.Employee;
import DAO.domain.Menu;
import service.BillService;
import service.DiningTableService;
import service.EmployeeService;
import service.MenuService;
import utils.Utility;

import java.util.List;

/**
 * @author MinZhi
 * @version 1.0
 */
public class View {
    public static void main(String[] args) {
        new View();
    }
    Boolean loop = true;
    EmployeeService employeeService = new EmployeeService();
    DiningTableService diningTableService = new DiningTableService();
    MenuService menuService = new MenuService();
    BillService billService = new BillService();

    public View() {
        while (loop){
            System.out.println("=============满汉楼==============");
            System.out.println("\t\t1 登录满汉楼");
            System.out.println("\t\t2 退出满汉楼");
            System.out.print("请输入你的选择：");
            int key = Utility.readInt();
            if (key == 1){
                System.out.print("请输入员工号：");
                String id = Utility.readString(10);
                System.out.print("请输入密 码：");
                String pwd = Utility.readString(15);
                Employee employee = employeeService.loginCheck(id, pwd);
                if (employee == null){
                    System.out.println("登录失败~");
                    continue;
                }else {
                    System.out.println("============登录成功[ "+employee.getName() +" ]============");
                    while (loop){
                        //二级菜单
                        System.out.println("============满汉楼二级菜单===========");
                        System.out.println("\t\t1 显示菜桌状态");
                        System.out.println("\t\t2 预定餐桌");
                        System.out.println("\t\t3 显示所有菜单");
                        System.out.println("\t\t4 点餐服务");
                        System.out.println("\t\t5 查看账单");
                        System.out.println("\t\t6 结账");
                        System.out.println("\t\t9 退出满汉楼");
                        System.out.print("请输入你的选择：");
                        key = Utility.readInt();
                        switch (key){
                            case 1:
                                showDiningTable();
                                break;
                            case 2:
                                bookDiningTable();
                                break;
                            case 3:
                                showMenu();
                                break;
                            case 4:
                                order();
                                break;
                            case 5:
                                showBill();
                                break;
                            case 6:
                                payBill();
                                break;
                            case 9:
                                loop = false;
                                break;
                            default:
                                System.out.println("输入错误~");
                                break;
                        }
                    }
                }
            }else if (key == 2){
                System.out.println("退出满汉楼~");
                break;
            }else {
                System.out.println("输入错误~");
                continue;
            }
        }
    }
    public void showDiningTable(){
        List<DiningTable> diningTables = diningTableService.showDiningState();
        System.out.println("餐桌编号\t\t餐桌状态\t\t预定人\t\t预定电话");
        for (DiningTable diningTable : diningTables) {
            System.out.println(diningTable.toString());
        }
        System.out.println("================显示完毕================");
    }
    public void bookDiningTable(){
        System.out.print("请输入预定餐桌编号(-1退出)：");
        int tableId = Utility.readInt();
        if (tableId == -1){
            return;
        }
        DiningTable diningTable = diningTableService.checkDiningTable(tableId);
        if (diningTable == null){
            System.out.println("该桌子不可预定");
            return;
        }
        char c = Utility.readConfirmSelection();
        if (c == 'N'){
            return;
        }
        System.out.print("预订人名称：");
        String name = Utility.readString(7);
        System.out.print("预订人电话：");
        String tel = Utility.readString(11);
        if (diningTableService.bookDiningTable(name,tel,tableId)){
            System.out.println("==========预定成功===========");
        }else {
            System.out.println("预定失败~");
        }
    }

    public void showMenu(){
        System.out.println("菜品编号" + "\t\t" + "菜品名称" + "\t\t"+"类别" + "\t\t" + "价格");
        List<Menu> menus = menuService.showMenu();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
        System.out.println("=============显示完毕============");
    }

    public void order(){
        System.out.print("请选择点餐餐桌：");
        int tableId = Utility.readInt();
        System.out.print("请选择菜品：");
        int menuId = Utility.readInt();
        System.out.print("请选择数量：");
        int nums = Utility.readInt();
        char c = Utility.readConfirmSelection();
        if (c == 'N'){
            return;
        }
        if (billService.order(tableId, menuId, nums)){
            System.out.println("============点餐成功=============");
        }else {
            System.out.println("点餐失败~");
        }
    }

    public void showBill(){
        List<Bill> bills = billService.showBill();
        System.out.println("编号\t\t菜单号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t状态");
        for (Bill bill : bills) {
            System.out.println(bill);
        }
        System.out.println("==============展示完成=============");
    }

    public void payBill(){
        System.out.print("请选择结账餐桌编号：");
        int tableId = Utility.readInt();
        System.out.println("请选择支付方式：");
        String payMethod = Utility.readString(5);
        char c = Utility.readConfirmSelection();
        if (c == 'N'){
            return;
        }
        boolean b = billService.payBill(tableId, payMethod);
        if (b){
            System.out.println("=============结账成功===========");
        }else {
            System.out.println("结账失败~");
        }
    }
}
