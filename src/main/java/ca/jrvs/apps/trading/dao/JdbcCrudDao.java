package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrudRepository<E, ID> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdvcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTalbeName();

    abstract public String getIdName();

    abstract Class getEntityClass();

    @SuppressWarnings("unchecked")
    @Override
    public E save(E entity) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        Number newId = getSimpleJdbcInsert().executeAndReturnKey(sqlParameterSource);
        entity.setId(newId.intValue());
        return entity;
    }

    @Override
    public E findById(ID id) {
        return findById(getIdName(), id, false, getEntityClass());
    }
    public E findByIdForUpdate(ID id){
        return findById(getIdName(), id, true, getEntityClass());
    }

    //Helper method
    public E findById(String idName, ID id, boolean forUpdate, Class clazz) {
        return null;
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    //Helper method
    public boolean existsByid(String idName, ID id) {
        return false;
    }

    @Override
    public void deleteById(ID id) {

    }

    //Helper method
    public void deleteByid(String idName, ID id) {

    }
}
