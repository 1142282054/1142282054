package DAO.domain;

/**
 * @author MinZhi
 * @version 1.0
 */

/**
 * CREATE TABLE diningTable(
 * 		id INT PRIMARY KEY auto_increment,
 * 		state VARCHAR(50) not null DEFAULT '',
 * 		orderName VARCHAR(50) NOT null DEFAULT '',
 * 		orderTel VARCHAR(50) NOT null DEFAULT ''
 * )charset=utf8;
 */
public class DiningTable {
    private int id;
    private String state;
    private String orderName;
    private String orderTel;

    public DiningTable() {
    }

    public DiningTable(int id, String state, String orderName, String orderTel) {
        this.id = id;
        this.state = state;
        this.orderName = orderName;
        this.orderTel = orderTel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderTel() {
        return orderTel;
    }

    public void setOrderTel(String orderTel) {
        this.orderTel = orderTel;
    }

    @Override
    public String toString() {
        return id + "\t\t\t" + state + "\t\t\t" + orderName + "\t\t\t" + orderTel;
    }
}
