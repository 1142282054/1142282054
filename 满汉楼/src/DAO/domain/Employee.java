package DAO.domain;

/**
 * @author MinZhi
 * @version 1.0
 */

/**
 * CREATE TABLE employee(
 * 		id INT PRIMARY KEY AUTO_INCREMENT,
 * 		empId VARCHAR(50) NOT NULL DEFAULT '',
 * 		pwd CHAR(32) NOT NULL DEFAULT '',
 * 		NAME VARCHAR(50) NOT NULL DEFAULT '',
 * 		job VARCHAR(50) NOT NULL DEFAULT ''
 * )CHARSET=utf8;
 */
public class Employee {
    private int id;
    private String empId;
    private String pwd;
    private String name;
    private String job;

    public Employee() {
    }

    public Employee(int id, String empId, String pwd, String name, String job) {
        this.id = id;
        this.empId = empId;
        this.pwd = pwd;
        this.name = name;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
