package top.keyle.Online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.Online_video_learning_system.entry.CrmBanner;

/**
* @author TMJIE5200
* @description 针对表【crm_banner(首页banner表)】的数据库操作Mapper
* @createDate 2023-02-07 19:26:43
* @Entity service/service_cms/src/main/java/.entry.CrmBanner
*/
@Mapper
public interface CrmBannerMapper extends BaseMapper<CrmBanner> {
    Integer selectSort();
}




