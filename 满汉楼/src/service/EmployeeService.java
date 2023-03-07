package service;

import DAO.dao.BasicDAO;
import DAO.domain.Employee;

/**
 * @author MinZhi
 * @version 1.0
 */
public class EmployeeService {
    BasicDAO<Employee> basicDAO = new BasicDAO<>();
    /**
     * 登录功能
     * @param id 账号
     * @param pwd 密码
     * @return true为成功 ，false则失败
     */
    public Employee loginCheck(String id,String pwd){
        return  (Employee)basicDAO.querySingle("select * from employee where empId = ? and pwd = ?",
                Employee.class, id, pwd);
    }
}
