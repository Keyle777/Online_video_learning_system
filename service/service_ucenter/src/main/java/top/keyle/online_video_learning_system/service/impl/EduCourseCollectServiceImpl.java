package top.keyle.online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.entity.EduCourseCollect;
import top.keyle.online_video_learning_system.entity.vo.collectionVO.CollectDetail;
import top.keyle.online_video_learning_system.entity.vo.collectionVO.NumberOfFavorites;
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

    @Override
    public JsonPage<CollectDetail> paginateToGetAListOfInstructors(Integer page, Integer pageSize,String id) {
        PageHelper.startPage(page, pageSize);
        List<CollectDetail> eduCourseCollectList = eduCourseCollectMapper.selectCollectList(id);
        return JsonPage.restPage(new PageInfo<>(eduCourseCollectList));
    }
}




