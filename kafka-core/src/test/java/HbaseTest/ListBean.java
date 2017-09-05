package HbaseTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ListBean {
    public static void main(String args[]){
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext-service.xml", "classpath:kafka-beans.xml"});
        String[] beanNames = ctx.getBeanDefinitionNames();
        int allBeansCount = ctx.getBeanDefinitionCount();
        System.out.println("所有beans的数量是：" + allBeansCount);
        for (String beanName : beanNames) {
            Class<?> beanType = ctx.getType(beanName);
            Package beanPackage = beanType.getPackage();
            //Object bean = ctx.getBean(beanName);
            System.out.println("BeanName:" + beanName);
            System.out.println("Bean的类型：" + beanType);
            System.out.println("Bean所在的包：" + beanPackage);
            System.out.println("\r\n");
        }
    }
}
