package top.keyle.Online_video_learning_system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.repository.query.Param;
import top.keyle.Online_video_learning_system.entry.CrmBanner;
import top.keyle.universal_tool.JsonPage;

import java.util.List;

/**
* @author TMJIE5200
* @description 针对表【crm_banner(首页banner表)】的数据库操作Service
* @createDate 2023-02-07 19:26:43
*/
public interface CrmBannerService extends IService<CrmBanner> {
    /**
     * 查询所有banner
     * @return
     */
    List<CrmBanner> selectAllBanner();

    JsonPage<CrmBanner> paginateToGetListOfBanners(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

}
