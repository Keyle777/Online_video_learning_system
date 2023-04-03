package top.keyle.online_video_learning_system.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.online_video_learning_system.entry.EduChapter;
import top.keyle.online_video_learning_system.entry.vo.chapter.ChapterVo;
import top.keyle.online_video_learning_system.service.EduChapterService;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

import java.util.List;

@RestController
@RequestMapping("/eduService/chapter")
@Api(tags = {"课程章节管理"})
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    /**
     * TODO 根据课程id进行查询
     * @param courseId
     * @return
     */
    @ApiOperation(value = "课程大纲列表")
    @GetMapping("/getChapterVideo/{courseId}")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "课程ID", name = "courseId")
    })
    public RespBean getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return RespBean.success("items",list);
    }

    @ApiOperation(value = "根据课程ID查询其下章节记录中sort最大的数字并返回")
    @GetMapping("/selectMaxSortByCourseId/{courseId}")
    public RespBean getTheMaximumSort(
            @PathVariable String courseId){
        Integer result = eduChapterService.selectMaxSortByCourseId(courseId);
        return RespBean.success("maxSort",result);
    }

    @ApiOperation(value = "新增章节")
    @PostMapping("/addedChapters")
    public RespBean save(
            @ApiParam(name = "chapterVo", value = "章节对象", required = true)
            @RequestBody EduChapter chapter){
        //  将章节对象保存到数据库中
        eduChapterService.save(chapter);
        return RespBean.success();
    }

    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("{id}")
    public RespBean getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){
        EduChapter chapter = eduChapterService.getById(id);
        return RespBean.success("item", chapter);
    }

    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("{id}")
    public RespBean updateById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody EduChapter chapter){
        chapter.setId(id);
        eduChapterService.updateById(chapter);
        return RespBean.success();
    }

    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public RespBean removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){
        boolean result = eduChapterService.removeChapterById(id);
        if(result){
            return RespBean.success();
        }else{
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
    }



}
