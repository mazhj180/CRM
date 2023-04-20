import com.mazhj.crm.commons.utils.ToExcelUtil;
import com.mazhj.crm.workbench.pojo.Activity;
import com.mazhj.crm.workbench.service.ActivityService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ActivityService bean = context.getBean(ActivityService.class);
        List<Activity> list = bean.queryAll();
//        ToExcelUtil.toExcel(list,Activity.class,"123");
    }
    @Test
    public void test(){

    }
}
