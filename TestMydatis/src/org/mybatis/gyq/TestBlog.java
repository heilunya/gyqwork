package org.mybatis.gyq;

import java.io.IOException;
import java.io.InputStream;

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
			Menu menu = session.selectOne("org.mybatis.gyq.MenuMapper.selectMenu", 1);
			System.out.println(menu.getID());
			System.out.println(menu.getName());
			System.out.println(menu.getParent());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(session!=null){
				session.close();
			}
		}
	}
}
