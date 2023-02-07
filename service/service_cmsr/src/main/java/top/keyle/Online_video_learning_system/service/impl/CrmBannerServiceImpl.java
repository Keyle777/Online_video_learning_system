package top.keyle.Online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.keyle.Online_video_learning_system.entry.CrmBanner;
import top.keyle.Online_video_learning_system.entry.vo.CrmBannerVo;
import top.keyle.Online_video_learning_system.mapper.CrmBannerMapper;
import top.keyle.Online_video_learning_system.service.CrmBannerService;
import top.keyle.universal_tool.JsonPage;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【crm_banner(首页banner表)】的数据库操作Service实现
* @createDate 2023-02-07 19:26:43
*/
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner>
    implements CrmBannerService {

    @Autowired
    CrmBannerMapper crmBannerMapper;

    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        // 根据id 进行降序排列，显示排列之后前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 使用last方法，拼接sql语句
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }

    @Override
    public JsonPage<CrmBanner> paginateToGetListOfBanners(Integer page, Integer pageSize, CrmBannerVo query) {

        PageHelper.startPage(page, pageSize);
        LambdaQueryWrapper<CrmBanner> wrapper = new LambdaQueryWrapper<>();
        String title = query.getTitle();
        String begin = query.getBegin();
        String end = query.getEnd();
        wrapper.like(!ObjectUtils.isEmpty(title), CrmBanner::getTitle, title)
                .ge(!ObjectUtils.isEmpty(begin), CrmBanner::getGmtCreate, begin)
                .le(!ObjectUtils.isEmpty(end), CrmBanner::getGmtModified, end);
        List<CrmBanner> eduBannerList = crmBannerMapper.selectList(wrapper);
        return JsonPage.restPage(new PageInfo<>(eduBannerList));
    }
}




