package education.cs.scu.controller;

import education.cs.scu.entity.HistoryYearData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QueryHistoryController {
    @RequestMapping(value = "queryHistoryYear", method= RequestMethod.GET)
    public List<HistoryYearData> queryHistoryYear(@RequestParam("shop_id") String shop_id){
        return null;
    }
}
