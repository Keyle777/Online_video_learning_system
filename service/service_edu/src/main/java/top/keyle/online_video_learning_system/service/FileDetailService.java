package top.keyle.online_video_learning_system.service;


import cn.xuyanwu.spring.file.storage.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.online_video_learning_system.pojo.FileDetail;

/**
* @author TMJIE5200
* @description 针对表【file_detail(文件记录表)】的数据库操作Service
* @createDate 2022-11-04 22:45:07
 *
 * 用来将文件上传记录保存到数据库，这里使用了 MyBatis-Plus 和 Hutool 工具类
 *
*/
@SuppressWarnings("ALL")
public interface FileDetailService extends IService<FileDetail> {
    /**
     * 保存文件信息到数据库
     */
    public boolean record(FileInfo info) ;
    /**
     * 根据 url 查询文件信息
     */
    public FileInfo getByUrl(String url);
    /**
     * 根据 url 删除文件信息
     */
    public boolean delete(String url);

}
