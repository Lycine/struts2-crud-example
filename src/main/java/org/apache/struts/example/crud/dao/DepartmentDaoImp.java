package org.apache.struts.example.crud.dao;

import org.apache.log4j.Logger;
import org.apache.struts.example.crud.model.Department;
import org.apache.struts.example.crud.model.Employee;
import org.apache.struts.example.crud.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongyu on 16/12/13.
 */
public class DepartmentDaoImp implements DepartmentDao {
    private static Logger logger = Logger.getLogger(DepartmentDaoImp.class);

    @Override
    public List<Department> getAllDepartments() {
        Transaction tx = null;
        List<Department> list = new ArrayList<Department>();
        String hql = "";
        try {
            Session session = HibernateUtil.getSession();
            tx = session.beginTransaction();//开启事务
            hql = "from Department";
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            for (Department department : list) {
//                logger.debug(department.toString());
                System.out.println(department.toString());
            }
            return list;
        } catch (Exception e) {
            tx.commit();//提交事务
            logger.error("getAllDepartments error");
            e.printStackTrace();
            return list;
        }
    }

    @Override
    public Map<Integer, Department> getDepartmentsMap() {
        Map<Integer, Department> map = new HashMap<Integer, Department>();
        List<Department> list = new ArrayList<Department>();
        try {
            list = getAllDepartments();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Department department : list) {
            logger.debug(department.toString());
            map.put(department.getDepartmentId(), department);
        }
        return map;
    }
}
