package org.apache.struts.example.crud.service;

import org.apache.struts.example.crud.dao.EmployeeDao;
import org.apache.struts.example.crud.dao.EmployeeDaoImp;
import org.apache.struts.example.crud.dao.InMemoryEmployeeDao;
import org.apache.struts.example.crud.model.Employee;

import java.util.List;

public class DefaultEmployeeService implements EmployeeService {

    private EmployeeDao dao;

    public DefaultEmployeeService() {
//        this.dao = new InMemoryEmployeeDao();
        this.dao = new EmployeeDaoImp();
    }

    @Override
    public List getAllEmployees() {
        return dao.getAllEmployees();
    }

    @Override
    public void updateEmployee(Employee emp) {
        dao.update(emp);
    }

    @Override
    public void deleteEmployee(Integer id) {
        dao.delete(id);
    }

    @Override
    public Employee getEmployee(Integer id) {
        return dao.getEmployee(id);
    }

    @Override
    public void insertEmployee(Employee emp) {
        dao.insert(emp);
    }
}
