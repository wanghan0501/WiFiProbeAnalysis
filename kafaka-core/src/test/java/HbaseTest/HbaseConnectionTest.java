package HbaseTest;

import com.cs.scu.service.HBaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HbaseConnectionTest {
    public static void main(String args[]) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext-service.xml", "classpath:kafka-beans.xml"});
        HBaseService hbaseService = (HBaseService) ctx.getBean("HBaseService");
        try {
            System.out.println("show tables");
            hbaseService.showTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
