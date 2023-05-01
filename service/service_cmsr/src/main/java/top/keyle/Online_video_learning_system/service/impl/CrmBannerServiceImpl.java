package top.keyle.Online_video_learning_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 从数据库中获取前两条按 id 字段降序排列的 CrmBanner 对象列表，并将结果存储在缓存中。
     *
     * @return 前两条按 id 字段降序排列的 CrmBanner 对象列表
     */
    @Override
    public List<CrmBanner> selectAllBanner() {
        // 创建 QueryWrapper 对象，用于构建带有查询条件的 SQL 查询
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        // 按 id 字段进行降序排列
        wrapper.orderByDesc("id");
        // 使用 last 方法拼接 SQL 语句，将查询结果限制为前两条记录
        wrapper.last("limit 2");
        // 执行查询，并将结果存储在一个 List 对象中
        List<CrmBanner> list = baseMapper.selectList(null);
        // 返回查询结果列表
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

    @Override
    public Integer getMaxSort() {
        return crmBannerMapper.selectSort();
    }
}




