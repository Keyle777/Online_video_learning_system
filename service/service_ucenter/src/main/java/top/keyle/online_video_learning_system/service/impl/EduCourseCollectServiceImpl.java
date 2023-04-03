package top.keyle.online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.entity.EduCourseCollect;
import top.keyle.online_video_learning_system.entity.vo.collectionVO.CollectDetail;
import top.keyle.online_video_learning_system.entity.vo.collectionVO.NumberOfFavorites;
import top.keyle.online_video_learning_system.entity.vo.courseVO.courseVO;
import top.keyle.online_video_learning_system.mapper.EduCourseCollectMapper;
import top.keyle.online_video_learning_system.service.EduCourseCollectService;
import top.keyle.online_video_learning_system.service.UcenterMemberService;
import top.keyle.universal_tool.JsonPage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【edu_course_collect(课程收藏)】的数据库操作Service实现
* @createDate 2023-02-11 18:14:01
*/
@Service
public class EduCourseCollectServiceImpl extends ServiceImpl<EduCourseCollectMapper, EduCourseCollect>
    implements EduCourseCollectService {
    @Autowired
    EduCourseCollectMapper eduCourseCollectMapper;

    @Autowired
    UcenterMemberService ucenterMemberService;
    @Override
    public Integer selectDayCollectionCount(String memberid) {
        return eduCourseCollectMapper.selectDayCollectionCoutn(memberid);
    }

    @Override
    public Integer SelectNumberOfCollectionsInAWeek(String memberid) {
        return eduCourseCollectMapper.SelectNumberOfCollectionsInAWeek(memberid);
    }

    @Override
    public HashMap<String,Object> SelectFreeVsPaid(String memberid) {
        Integer gratis = 0;
        Integer toll = 0;
        HashMap<String, Object> hashMap = new HashMap<>();
        if (ucenterMemberService.getById(memberid) != null) {
            List<NumberOfFavorites> numberOfFavorites = eduCourseCollectMapper.SelectFreeVsPaid(memberid);
            for (NumberOfFavorites numberOfFavorite : numberOfFavorites) {
                BigDecimal price = numberOfFavorite.getPrice();
                if ("0.00".equals(price.toString())) {
                    // 免费个数
                    gratis += numberOfFavorite.getTotal();
                }else{
                    // 收费个数
                    toll+=numberOfFavorite.getTotal();
                }
            }
            hashMap.put("gratis",gratis);
            hashMap.put("toll",toll);
        }
        return hashMap;
    }

    @Override
    public List<CollectDetail> selectCollectList(String memberid) {
        if (ucenterMemberService.getById(memberid)!=null){
            return eduCourseCollectMapper.selectCollectList(memberid);
        }else{
            return null;
        }
    }

    /**
     分页获取用户收藏列表
     @param page 当前页码
     @param pageSize 每页数据量
     @param id 用户ID
     @return 返回JsonPage对象，包含分页查询结果和相关分页信息
     */
    @Override
    public JsonPage<CollectDetail> paginateToGetAListOfInstructors(Integer page, Integer pageSize,String id) {
        // 设置分页信息，其中page 表示当前页码，pageSize 表示每页条数。
        PageHelper.startPage(page, pageSize);
        // 从数据库中获取指定用户ID的收藏列表
        List<CollectDetail> eduCourseCollectList = eduCourseCollectMapper.selectCollectList(id);
        // 返回一个包含分页信息和查询结果的JsonPage对象
        return JsonPage.restPage(new PageInfo<>(eduCourseCollectList));
    }

    @Override
    public Integer saveEduCourseCollect(EduCourseCollect eduCourseCollect) {
        return eduCourseCollectMapper.saveEduCourseCollect(eduCourseCollect);
    }

    @Override
    public JsonPage<courseVO> selectCourseCollectionTostudy(Integer page, Integer pageSize, String id) {
        PageHelper.startPage(page, pageSize);
        List<courseVO> eduCourseCollectList = baseMapper.selectCourseCollectionTostudy(id);
        return JsonPage.restPage(new PageInfo<>(eduCourseCollectList));
    }
}




