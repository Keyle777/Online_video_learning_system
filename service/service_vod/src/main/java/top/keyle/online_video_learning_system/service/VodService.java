package top.keyle.online_video_learning_system.service;

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


}
