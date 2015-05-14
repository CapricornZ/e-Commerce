package ecommerce.algorithm1.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ecommerce.algorithm1.domain.Record;

public class RecordService {
	
	@Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
    	
        this.sessionFactory = sessionFactory;
    }
    
    protected Session getSession() {
    	
        return sessionFactory.getCurrentSession();
    }
    
    public Record save(Record record){
    	this.getSession().save(record);
    	return record;
    }
    
    public void delete(Record record){
    	this.getSession().delete(record);
    }
    
    public void update(Record record){
    	Record oldRecord = (Record) this.getSession().get(Record.class, record.getId());
    	if(null != oldRecord){
    		oldRecord.setSource(record.getSource());
    		this.getSession().update(oldRecord);
    	}
    }
    
    public Record queryByID(int id){
    	
    	return (Record) this.getSession().get(Record.class, id);
    }
    
    public List<Record> queryByCreateTime(String createTime){
    	
    	Date startTime = null, endTime = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
			startTime = sdf.parse(createTime + " 00:00:00");
			endTime = sdf.parse(createTime + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	
    	String hql = "from Record where createTime >= :startTime and createTime <= :endTime)";
    	Query query = this.getSession().createQuery(hql);
    	query.setParameter("startTime", startTime);
    	query.setParameter("endTime", endTime);
    	return query.list();
    }
}
