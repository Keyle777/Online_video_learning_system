package top.keyle.online_video_learning_system.service;

import com.aliyuncs.vod.model.v20170321.CreateAuditResponse;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface VodService {
    String uploadVideo(MultipartFile file);

    /**
     * 根据视频资源获取ID删除云端视频
     * @param videoSourceId
     * @return
     */
    Boolean deleteVodById(String videoSourceId);

    /**
     * 删除多个阿里云视频的方法
     * @param videoIdList
     */
    Boolean removeMoreVideo(List<String> videoIdList);

    GetVideoInfoResponse queryDetailsBasedOnVideoID(String videoID);
    // 人工审核
    CreateAuditResponse CreateAudit(String AuditContent);


    GetMezzanineInfoResponse GetMezzanineInfo(String AuditContent);


}
