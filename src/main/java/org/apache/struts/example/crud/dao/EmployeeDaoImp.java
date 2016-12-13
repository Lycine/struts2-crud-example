package org.apache.struts.example.crud.dao;

import org.apache.log4j.Logger;
import org.apache.struts.example.crud.model.Department;
import org.apache.struts.example.crud.model.Employee;
import org.apache.struts.example.crud.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyu on 16/12/13.
 */
public class EmployeeDaoImp implements EmployeeDao {
    private static Logger logger = Logger.getLogger(EmployeeDaoImp.class);

    @Override
    public List getAllEmployees() {
        Transaction tx = null;
        List<Employee> list = new ArrayList<Employee>();
        String hql = "";
        try {
            Session session = HibernateUtil.getSession();
            tx = session.beginTransaction();//开启事务
            hql = "from Employee";
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            for (Employee employee : list) {
//                logger.debug(employee.toString());
                System.out.println(employee.toString());
            }
            logger.debug("getAllEmployees success");
            return list;
        } catch (Exception e) {
            tx.commit();//提交事务
            logger.error("getAllEmployees error");
            e.printStackTrace();
            return list;
        }
    }

    @Override
    public Employee getEmployee(Integer id) {
        Transaction tx = null;
        Employee employee = new Employee();
        List<Employee> list = new ArrayList<Employee>();
        String hql = "";
        try {
            Session session = HibernateUtil.getSession();
            tx = session.beginTransaction();//开启事务
            hql = "FROM Employee E WHERE E.id = ?";
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            for (Employee e : list) {
//                logger.debug(employee.toString());
                System.out.println(e.toString());
            }
            employee = list.get(0);
            if (list.size() > 1) {
                logger.error("getEmployee error, not unique result.");
            } else {
                logger.debug("getEmployee success");
            }
            return employee;
        } catch (Exception e) {
            tx.commit();//提交事务
            logger.error("getEmployee error");
            e.printStackTrace();
            return employee;
        }
    }

    @Override
    public void update(Employee emp) {
        Transaction tx = null;
        String hql = "";
        try {
            Session session = HibernateUtil.getSession();
            tx = session.beginTransaction();//开启事务
            hql = "update Employee set age = :age, firstName = :firstName, lastName = :lastName where id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("age", emp.getAge());
            query.setParameter("firstName", emp.getFirstName());
            query.setParameter("lastName", emp.getLastName());
            query.setParameter("id", emp.getEmployeeId());
            int i = query.executeUpdate();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            if (i != 1) {
                logger.error("update emp error, i=" + i);
            } else {
                logger.debug("update emp success");
            }
        } catch (Exception e) {
            logger.error("update emp error");
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Employee emp) {
        Transaction tx = null;
        String hql = "";
        try {
            Session session = HibernateUtil.getSession();
            tx = session.beginTransaction();//开启事务
            Employee employee = new Employee();
            employee.setAge(123456);
            employee.setDepartment(new Department(3, "test-departmentName"));
            employee.setFirstName("first");
            employee.setLastName("last");
            session.save(employee);
            tx.commit();//提交事务
            System.out.println("*****result*****");
            logger.debug("insert emp success");
        } catch (Exception e) {
            logger.error("insert emp error");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        Transaction tx = null;
        String hql = "";
        try {
            Session session = HibernateUtil.getSession();
            tx = session.beginTransaction();//开启事务
            hql = "DELETE FROM Employee WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            int i = query.executeUpdate();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            if (i != 1) {
                logger.error("delete emp error, i=" + i);
            } else {
                logger.debug("delete emp success");
            }
        } catch (Exception e) {
            logger.error("delete emp error");
            e.printStackTrace();
        }
    }
}
