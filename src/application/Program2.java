package application;

import dao.DaoFactory;
import dao.DepartmentDaoJDBC;
import dao.IDepartmentDao;
import entities.Department;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {
        IDepartmentDao departmentDao = DaoFactory.creatIDepartmentDao();

        System.out.println("***** Test #1 - findById *****");
        Department department = departmentDao.findById(2);
        System.out.println(department);

        System.out.println("***** Test #2 - findAll *****");
        List<Department> list = departmentDao.findAll();
        for (Department dp:list) {
            System.out.println(dp);
        }

        /*
        System.out.println("***** Test #3 - insert *****");
        Department department1 = new Department(null, "GPUs");
        departmentDao.insert(department1);
        list = departmentDao.findAll();
        for (Department dp:list) {
            System.out.println(dp);
        }

        System.out.println("***** Test #4 - update *****");
        System.out.println("Department to be updated: " + departmentDao.findById(1));
        department = departmentDao.findById(1);
        department.setName("TVs");
        departmentDao.update(department);
        System.out.println("Updated department: " + departmentDao.findById(1));
        list = departmentDao.findAll();
        for (Department dp:list) {
            System.out.println(dp);
        }
        */

        System.out.println("***** Test #5 - insert *****");
        Department newDepartment = new Department(null, "Glasses");
        System.out.println("Department to be inserted: " + newDepartment);
        departmentDao.insert(newDepartment);
        list = departmentDao.findAll();
        for (Department dp:list) {
            System.out.println(dp);
        }
    }
}
