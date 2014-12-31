package com.gyq.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class RedisDemo {
	
	private RedisOperator redisOPerator=new RedisOperator();
	
	private void cusSave(){
		long t1 = System.currentTimeMillis();
		for(int i=0;i<2;i++){
			System.out.println("begin....cusSave"+i);
			String sql="select top 1000 a.id,a.PerID,a.JobcnID,a.ProvinceID,a.CityID,a.CusSort from   customer a with(nolock)  where a.jobcnid is not null and a.jobcnid<>'' and  a.id   >   ( select  isnull(max(b.id),'') from (Select top "+i*1000+" id from Customer c with(nolock) where  c.jobcnid is not null and c.jobcnid<>''   order by id) b)";
			List<CustomerDto> datas=redisOPerator.collectionCustomer(sql);
			for(CustomerDto dto:datas){
				String key="JOBCNID"+dto.getJobcnId();
				redisOPerator.setObject(key,dto);
			}
		}    
		System.out.println("cusSave end"+(System.currentTimeMillis() - t1));
	}
	
	private void getCus(){
		CustomerDto dto=(CustomerDto)redisOPerator.getObject("JOBCNID271176");
		System.out.println(dto.getPerId());
	}
	
	
	
	private void addressSave(){
		long t1 = System.currentTimeMillis();
		System.out.println("begin....addressSave");
		String sql="select id,InviteEffectFlag from DicAddress with(nolock)";
		Map<String,String> datas=redisOPerator.collectionAddress(sql);
		Set<String> keys=datas.keySet();
		for(String key:keys){
			redisOPerator.setValue(key, datas.get(key));
		}
		
		System.out.println("addressSave end"+(System.currentTimeMillis() - t1));
	}
	
	private void getAddress(){
		System.out.println("flag:"+redisOPerator.getValue("DICADDRESS3008"));
	}
	
	public static void main(String[] args) {
		RedisDemo demo=new RedisDemo();
		//demo.cusSave();
		//demo.getCus();
		demo.addressSave();
		//demo.getAddress();
	}
	
}



