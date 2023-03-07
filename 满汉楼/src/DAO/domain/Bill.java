package DAO.domain;

import java.sql.Timestamp;

/**
 * @author MinZhi
 * @version 1.0
 */
public class Bill {
    /**
     * CREATE TABLE bill(
     * 		id INT PRIMARY KEY auto_increment,
     * 		billId VARCHAR(50) not null DEFAULT '',
     * 		menuId int NOT null DEFAULT 0,
     * 		nums int NOT null DEFAULT 0,
     * 		money DOUBLE not null DEFAULT 0,
     * 		diningTable int not null DEFAULT 0,
     * 		billDate datetime not null,
     * 		state VARCHAR(50) not null DEFAULT ''
     * )charset=utf8;
     */
    private int id;
    private String billId;
    private int menuId;
    private int nums;
    private double money;
    private int diningTable;
    private Timestamp billDate;
    private String state;

    public Bill() {
    }

    public Bill(int id, String billId, int menuId, int nums, double money, int diningTable, Timestamp billDate, String state) {
        this.id = id;
        this.billId = billId;
        this.menuId = menuId;
        this.nums = nums;
        this.money = money;
        this.diningTable = diningTable;
        this.billDate = billDate;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(int diningTable) {
        this.diningTable = diningTable;
    }

    public Timestamp getBillDate() {
        return billDate;
    }

    public void setBillDate(Timestamp billDate) {
        this.billDate = billDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return  id +
                "\t\t\t" + menuId +
                "\t\t\t" + nums +
                "\t\t\t" + money +
                "\t\t\t" + diningTable +
                "\t\t\t" + billDate +
                "\t\t\t" + state;
    }
}
