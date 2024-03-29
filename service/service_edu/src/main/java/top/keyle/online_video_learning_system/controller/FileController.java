package top.keyle.online_video_learning_system.controller;

import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.entry.FileDetail;
import top.keyle.online_video_learning_system.service.impl.FileDetailService;
import top.keyle.universal_tool.RespBean;
import top.keyle.universal_tool.RespBeanEnum;

@SuppressWarnings("all")
@Api(tags = {"文件管理"})
@RestController
@RequestMapping("/fileManagement")
public class FileController {

    // 注入 fileDetailService 实列
    @Autowired
    FileDetailService fileDetailService;
    // 注入 fileStorageService 实列
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 上传文件，成功返回文件 url
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传图片到oss，成功则返回文件url")
    public RespBean upload(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
                .putAttr("role", "admin") //保存一些属性，可以在切面、保存上传记录、自定义存储平台等地方获取使用，不需要可以不写
                .upload();  //将文件上传到对应地方
        if (ObjectUtils.isEmpty(fileInfo)) {
            return RespBean.error(RespBeanEnum.ERROR);
        } else {
            return RespBean.success("imageAddress", fileInfo.getUrl());
        }
    }

    /**
     * 上传图片，成功返回文件信息
     * 图片处理使用的是 https://github.com/coobird/thumbnailator
     */
    @PostMapping("/upload-image")
    @ApiOperation(value = "上传2个图片到oss，一个是1000大小一个是120大小的图片，成功则返回文件详情信息")
    public RespBean uploadImage(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
                .image(img -> img.size(1000, 1000))  //将图片大小调整到 1000*1000
                .thumbnail(th -> th.size(120, 120))  //再生成一张 120*120 的缩略图
                .setPlatform("aliyun-oss-1")
                .upload();
        if (ObjectUtils.isEmpty(fileInfo)) {
            return RespBean.error(RespBeanEnum.ERROR);
        } else {
            return RespBean.success("imgData", fileInfo);
        }
    }

    /**
     * 上传文件到指定存储平台，成功返回文件信息
     */
    @PostMapping("/upload-platform")
    @ApiOperation(value = "上传图片到oss，成功则返回文件详情信息")
    public RespBean uploadPlatform(MultipartFile file) {
        // 使用指定的存储平台
        return RespBean.success("imageFile", fileStorageService.of(file)
                .setPlatform("aliyun-oss-1")
                .upload());
    }

    /**
     * 上传文件到指定存储平台，成功返回文件信息
     */
    @PostMapping("/uploadExcel")
    @ApiOperation(value = "上传Excel到oss，成功则返回文件详情信息")
    public RespBean uploadExcel(MultipartFile file) {
        return RespBean.success("ExcelFile", fileStorageService.of(file)
                .setPlatform("aliyun-oss-2")    //使用指定的存储平台
                .upload());
    }

    /**
     * 上传文件到指定存储平台，成功返回文件信息
     */
    @PostMapping("/uploadCover")
    @ApiOperation(value = "上传课程封面到oss，成功则返回文件详情信息")
    public RespBean uploadCover(MultipartFile file) {
        return RespBean.success("CoverFile", fileStorageService.of(file)
                .setPlatform("aliyun-oss-3")    //使用指定的存储平台
                .upload());
    }

    /**
     * 上传文件到指定存储平台，成功返回文件信息
     */
    @PostMapping("/uploadVideo")
    @ApiOperation(value = "上传视频到oss，成功则返回文件详情信息")
    public RespBean uploadVideo(MultipartFile file) {
        return RespBean.success("VideoFile", fileStorageService.of(file)
                .setPlatform("aliyun-oss-4")    //使用指定的存储平台
                .upload());
    }

    @DeleteMapping("/deleteImageBasedOnUrl")
    @ApiOperation(value = "传入图片文件删除图片")
    public RespBean deleteImageBasedOnUrl(@RequestBody(required = false) FileDetail imageFile) {
        if (fileStorageService.exists(imageFile.getUrl())) {
            //直接通过文件信息中的 url 删除，省去手动查询文件信息记录的过程
            return fileDetailService.delete(imageFile.getUrl()) ? RespBean.success() : RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return RespBean.error(RespBeanEnum.FIND_PICTURES_ERROR);
    }
}
