package top.keyle.service.impl

/service_cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import service/service_cms.entry.CrmBanner;
import service/service_cms.service.CrmBannerService;
import service/service_cms.mapper.CrmBannerMapper;
import org.springframework.stereotype.Service;

/**
* @author TMJIE5200
* @description 针对表【crm_banner(首页banner表)】的数据库操作Service实现
* @createDate 2023-02-07 19:16:02
*/
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner>
    implements CrmBannerService{

}




