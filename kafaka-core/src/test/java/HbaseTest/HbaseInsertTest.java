package HbaseTest;

import com.cs.scu.hbase.dao.GroupDataDao;
import com.cs.scu.hbase.dataObject.GroupData;
import com.cs.scu.service.HBaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HbaseInsertTest {
    public static void main(String args[]){
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext-service.xml"});
        HBaseService hbaseService = (HBaseService) ctx.getBean("HBaseService");
        GroupDataDao groupDatadao = (GroupDataDao) ctx.getBean("groupDataDao");
        GroupData groupData = new GroupData();
        try{
            groupData.setTime_id("test");
            groupData.setRecord_time("test");
            groupData.setAddr("test");
            groupData.setDataList("test");
            groupData.setProbe_id("test");
            groupData.setLat("test");
            groupData.setLon("test");
            groupData.setMmac("test");
            groupData.setWmac("test");
            groupData.setWssid("test");
            groupData.setRate("test");
            groupDatadao.save(groupData);
            System.out.println("Finish");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
