package education.cs.scu.controller;

import education.cs.scu.entity.ShopInfo;
import education.cs.scu.entity.TaskBean;
import education.cs.scu.javautils.ExcelUtil;
import education.cs.scu.service.ShopService;
import education.cs.scu.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author lch
 * @Create on 2017/09/03 14:02
 **/
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "addTask", method = RequestMethod.GET)
    public String addTask(HttpServletRequest request,
                          @RequestParam("task_id") Long taskId,
                          @RequestParam("task_name") String taskName,
                          @RequestParam("create_time") Long createTime,
                          @RequestParam("start_time") Long startTime,
                          @RequestParam("finish_time") Long finishTime,
                          @RequestParam("task_type") String taskType,
                          @RequestParam("task_status") String taskStatus,
                          @RequestParam("task_param") String taskParam) throws Exception {
        TaskBean taskBean = new TaskBean(taskId, taskName, createTime,
                startTime, finishTime, taskType, taskStatus, taskParam);


        //localhost:8080/addTask.action?task_id=10000&task_name=test&create_time=201709031425&start_time=201709031426&finish_time=201709031427&task_type=test&task_status=stay&task_param=test
        int res = taskService.addTask(taskBean);
       //System.out.println("---------" + res);
        if (res > 0) {
            return "{\"success\":1}";
        } else {
            return "{\"failed\":0}";
        }

    }


    @RequestMapping(value = "exportExcel", method = RequestMethod.GET)
    public String addTask(HttpServletRequest request,
                          @RequestParam("user_name") String userName) throws Exception {

        ExcelUtil<ShopInfo> excelUtil = new ExcelUtil<ShopInfo>();
        String[] header = {"","商店ID", "拥有者", "商店地址", "商店名称", "商店管理者", "电话", "描述"};
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShop_owner(userName);

        List<ShopInfo> shopInfoList = shopService.queryShopInfos(shopInfo);
        for (ShopInfo si:shopInfoList) {
            System.out.println(si.getShop_describe());
        }

        if (shopInfoList != null && shopInfoList.size() > 0) {
            OutputStream outputStream = new FileOutputStream("/Users/maicius/Desktop/" + userName + ".xls");
            excelUtil.exportExcel(header, shopInfoList, outputStream);
            outputStream.close();
        } else {
            return "{\"failed\":0}";
        }
        return "{\"success\":1}";

    }
}
