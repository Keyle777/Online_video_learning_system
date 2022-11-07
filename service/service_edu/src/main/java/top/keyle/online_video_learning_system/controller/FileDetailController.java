package top.keyle.online_video_learning_system.controller;

import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import cn.xuyanwu.spring.file.storage.ProgressListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SuppressWarnings("all")
public class FileDetailController {

    @Autowired
    private FileStorageService fileStorageService;//注入实列

    /**
     * 上传文件，成功返回文件 url
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath("upload/") //保存到相对路径下，为了方便管理，不需要可以不写
                .setObjectId("0")   //关联对象id，为了方便管理，不需要可以不写
                .setObjectType("0") //关联对象类型，为了方便管理，不需要可以不写
                .putAttr("role","admin") //保存一些属性，可以在切面、保存上传记录、自定义存储平台等地方获取使用，不需要可以不写
                .upload();  //将文件上传到对应地方
        return fileInfo == null ? "上传失败！" : fileInfo.getUrl();
    }

    /**
     * 上传图片裁剪大小并生成一张缩略图，成功返回文件信息
     * 图片处理使用的是 https://github.com/coobird/thumbnailator
     */
    @PostMapping("/upload-image")
    public FileInfo uploadImage(MultipartFile file) {
        return fileStorageService.of(file)
                .image(img -> img.size(1000,1000))  //将图片大小调整到 1000*1000
                .thumbnail(th -> th.size(200,200))  //再生成一张 200*200 的缩略图
                .upload();
    }

    /**
     * 上传文件到指定存储平台，成功返回文件信息
     */
    @PostMapping("/upload-platform")
    public FileInfo uploadPlatform(MultipartFile file) {
        return fileStorageService.of(file)
                .setPlatform("aliyun-oss-1")    //使用指定的存储平台
                .upload();
    }

    /**
     * 下载文件
     */
    @PostMapping("/download")
    public void download(FileInfo file) {
        // 下载到文件
        fileStorageService.download(file.getUrl()).file("https://github.com/Keyle777/Online_video_learning_system/blob/59ce718d17209130f5b10953500fc91dd29ec627/src/main/resources/static/savePictures/originalImg/"+file.getFilename());
        // 下载缩略图
        fileStorageService.downloadTh(file.getThUrl()).file("https://github.com/Keyle777/Online_video_learning_system/blob/59ce718d17209130f5b10953500fc91dd29ec627/src/main/resources/static/savePictures/thumbnail/"+file.getThFilename());

    }

    /**
     * 监听下载进度并下载图片
     * @param fileInfo
     */
    @PostMapping("/listenAndDownloadProgress")
    public void listenAndDownloadProgress(FileInfo fileInfo){

        fileStorageService.download(fileInfo).setProgressMonitor(new ProgressListener() {
            @Override
            public void start() {
                System.out.println("下载开始");
            }

            @Override
            public void progress(long progressSize,long allSize) {
                System.out.println("已下载 " + progressSize + " 总大小" + allSize);
            }

            @Override
            public void finish() {
                System.out.println("下载结束");
            }
        }).file("https://github.com/Keyle777/Online_video_learning_system/blob/59ce718d17209130f5b10953500fc91dd29ec627/src/main/resources/static/savePictures/originalImg/"+fileInfo.getFilename());
    }

    /**
     * 删除文件
     * @param url
     */
    public void deleteFile(String url){
        //直接通过文件信息中的 url 判断文件是否存在，省去手动查询文件信息记录的过程
        boolean exists = fileStorageService.exists(url);
        //直接通过文件信息中的 url 删除，省去手动查询文件信息记录的过程
        if (exists) {
            fileStorageService.delete(url);
        }else{
            System.out.println("文件不存在！");
        }
    }
}
