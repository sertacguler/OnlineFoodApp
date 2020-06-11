package com.bilgeadam.onlinefoodapp.dao;

import com.bilgeadam.onlinefoodapp.domain.Employee;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao  {

    private final EntityManager entityManager;

    public EmployeeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    @Override
    public Optional<Employee> findById(Long empId) {
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
        Root<Employee> root = cr.from(Employee.class);
        Query<Employee> query = getSession().createQuery(cr.select(root).where(cb.equal(root.get("empId"),empId)));
        List<Employee> results = query.getResultList();
        return !results.isEmpty() ? Optional.ofNullable(results.get(0)) : Optional.empty();
    }

    @Override
    public Optional<Employee> findByUsername(String username) {
        /*CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
        Root<Employee> root = cr.from(Employee.class);
        Query<Employee> query = getSession().createQuery(cr.select(root).where(cb.equal(root.get("username"),username)));
        List<Employee> results = query.getResultList();
        return !results.isEmpty() ? Optional.ofNullable(results.get(0)) : Optional.empty();*/

        NativeQuery<Employee> query = getSession().createNativeQuery("select * from EMPLOYEE where USERNAME=:username", Employee.class).setParameter("username", username);
        List<Employee> results = query.getResultList();
        return !results.isEmpty() ? Optional.ofNullable(results.get(0)) : Optional.empty();
    }
}
