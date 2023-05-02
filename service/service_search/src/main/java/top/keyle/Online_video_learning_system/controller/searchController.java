package top.keyle.Online_video_learning_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyle.Online_video_learning_system.core.SearchService;
import top.keyle.Online_video_learning_system.entry.CourseFrontQuery;
import top.keyle.universal_tool.RespBean;

import java.util.HashMap;

@RestController
@RequestMapping("/searchService/search")
public class searchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/index/{page}/{limit}")
    public RespBean search(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, CourseFrontQuery courseFrontQuery){
        String searchText = courseFrontQuery.getSearchText();

        HashMap<String, Object> hashMap = searchService.searchForDocuments(searchText, "edu_course", page, limit);
        return  RespBean.success(hashMap);

    }
}

