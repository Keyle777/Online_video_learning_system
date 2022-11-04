package top.keyle.online_video_learning_system.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.xuyanwu.spring.file.storage.FileInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import top.keyle.online_video_learning_system.mapper.FileDetailMapper;
import top.keyle.online_video_learning_system.pojo.FileDetail;
import top.keyle.online_video_learning_system.service.FileDetailService;

/**
* @author TMJIE5200
* @description 针对表【file_detail(文件记录表)】的数据库操作Service实现
* @createDate 2022-11-04 22:45:07
*/
@Service
public class FileDetailServiceImpl extends ServiceImpl<FileDetailMapper, FileDetail>
    implements FileDetailService {
    /**
     * 保存文件信息到数据库
     */
    @SneakyThrows
    @Override
    public boolean record(FileInfo info) {
        FileDetail detail = BeanUtil.copyProperties(info,FileDetail.class,"attr");

        //这是手动获 取附加属性字典 并转成 json 字符串，方便存储在数据库中
        if (info.getAttr() != null) {
            detail.setAttr(new ObjectMapper().writeValueAsString(info.getAttr()));
        }
        boolean b = save(detail);
        if (b) {
            info.setId(detail.getId());
        }
        return b;
    }

    /**
     * 根据 url 查询文件信息
     */
    @SneakyThrows
    @Override
    public FileInfo getByUrl(String url) {
        FileDetail detail = getOne(new LambdaQueryWrapper<FileDetail>().eq(FileDetail::getUrl,url));
        FileInfo info = BeanUtil.copyProperties(detail,FileInfo.class,"attr");

        //这是手动获取数据库中的 json 字符串 并转成 附加属性字典，方便使用
        if (StrUtil.isNotBlank(detail.getAttr())) {
            info.setAttr(new ObjectMapper().readValue(detail.getAttr(), Dict.class));
        }
        return info;
    }

    /**
     * 根据 url 删除文件信息
     */
    @Override
    public boolean delete(String url) {
        return remove(new LambdaQueryWrapper<FileDetail>().eq(FileDetail::getUrl,url));
    }
}




