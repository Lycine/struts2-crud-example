package model;

import org.apache.log4j.Logger;
import org.apache.struts.example.crud.model.Department;
import org.apache.struts.example.crud.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongyu on 16/12/13.
 */
public class DepartmentDaoImp {
    private static Logger logger = Logger.getLogger(DepartmentDaoImp.class);

    @Test
    public void getAllDepartments() {
        Session session = null;
        List<Department> list = new ArrayList<Department>();
        try {
            session = HibernateUtil.getSession();
            String hql = "from Department";
            Transaction tx = session.beginTransaction();//开启事务
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            for (Department department : list) {
                System.out.println(department.toString());
//                logger.debug(department.toString());
            }
        } catch (Exception e) {
            logger.error("getAllDepartments error");
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Test
    public void getDepartmentsMap(){
        Session session = null;
        List<Department> list = new ArrayList<Department>();
        try {
            session = HibernateUtil.getSession();
            String hql = "from Department";
            Transaction tx = session.beginTransaction();//开启事务
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();//提交事务
            System.out.println("*****result*****");
            for (Department department : list) {
                System.out.println(department.toString());
//                logger.debug(department.toString());
            }
        } catch (Exception e) {
            logger.error("getAllDepartments error");
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        

        Map<Integer, Department> map = new HashMap<Integer, Department>();
        for (Department department : list) {
            logger.debug(department.toString());
            map.put(department.getDepartmentId(),department);
        }
        System.out.println(map.toString());
    }
}
