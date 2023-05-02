package top.keyle.Online_video_learning_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.Online_video_learning_system.core.SearchService;
import top.keyle.universal_tool.RespBean;

import java.util.HashMap;

@RestController
@RequestMapping("/searchService/search")
public class searchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/index")
    public RespBean search(){
        HashMap<String, Object> hashMap = searchService.searchForDocuments("Java", "edu_course", 1, 10);
        return  RespBean.success(hashMap);
    }
}
