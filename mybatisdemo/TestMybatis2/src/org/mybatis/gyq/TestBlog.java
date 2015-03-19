package org.mybatis.gyq;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TestBlog {

	public static void main(String args[]){
		String resource = "org/mybatis/gyq/mybatis-config.xml";
		InputStream inputStream;
		SqlSession session = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			//System.out.println("session"+session);
			//Menu menu = session.selectOne("org.mybatis.gyq.MenuMapper.selectMenu", 1);
			Person person = session.selectOne("org.mybatis.gyq.MenuMapper.selectcollection", "JCNEP5991");
			System.out.println(person.getPerId());
			System.out.println(person.getDeptName());
			ArrayList<Recruit> recruits = person.getRecruits();
			System.out.println(recruits.size());
			for(int i=0;i<recruits.size();i++){
				System.out.println(recruits.get(i).getrRID());
				System.out.println(recruits.get(i).getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(session!=null){
				session.close();
			}
		}
	}
}
