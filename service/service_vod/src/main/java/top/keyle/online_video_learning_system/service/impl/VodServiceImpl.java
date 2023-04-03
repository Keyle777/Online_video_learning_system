package top.keyle.online_video_learning_system.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import top.keyle.online_video_learning_system.service.VodService;
import top.keyle.online_video_learning_system.utils.AliyunVodSDKUtil;
import top.keyle.online_video_learning_system.utils.ConstantVodUtils;
import top.keyle.universal_tool.GlobalException;
import top.keyle.universal_tool.RespBeanEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
@SuppressWarnings("all")
public class VodServiceImpl implements VodService {
    @Override
    public String getPlayAuth(String videoId) {
        try {
            // 创建初始化对象
            IAcsClient client = AliyunVodSDKUtil.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建对象获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向request设置视频id
            request.setVideoId(videoId);
            // 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (ClientException e) {
            // 获取凭证失败
            throw new GlobalException(RespBeanEnum.FAILED_TO_GET_CREDENTIALS);
        }
    }

    // 上传视频到阿里云
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                System.out.println(errorMessage);
                if (ObjectUtils.isEmpty(videoId)) {
                    throw new GlobalException(RespBeanEnum.THE_VIDEO_ID_IS_EMPTY);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new GlobalException(RespBeanEnum.ERROR);
        }
    }

    /**
     根据视频ID删除阿里云上的视频
     @param videoSourceId 需要删除的视频ID
     @return 删除是否成功，成功返回true，失败返回false
     */
    @Override
    public Boolean deleteVodById(String videoSourceId) {
        // 创建阿里云客户端对象
        IAcsClient client = AliyunVodSDKUtil.initVodClient(
                ConstantVodUtils.ACCESS_KEY_ID,
                ConstantVodUtils.ACCESS_KEY_SECRET);
        // 创建删除视频请求对象，并设置要删除的视频ID
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoSourceId);
        try {
            // 获取删除视频的响应对象
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            // 打印出异常信息
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        // 返回删除操作是否成功
        return true;
    }

    @Override
    public Boolean removeMoreVideo(List<String> videoIdList) {
        // 初始化对象
        IAcsClient client = AliyunVodSDKUtil.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        // 创建删除视频request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        // videoIdList值转成 1,2,3,一次最多删除20个视频
        String videoIds="";
        for (int i = 0; i < videoIdList.size(); i++) {
            if (i + 1 == videoIdList.size()){
                videoIds+=videoIdList.get(i);
            }else{
                videoIds+=videoIdList.get(i)+',';
            }
        }
        // 向request设置视频id
        request.setVideoIds(videoIds);
        try {
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return true;
    }

    /**
     根据视频ID查询视频信息
     @param videoID 视频ID
     @return 返回GetVideoInfoResponse类型的视频信息
     */
    @Override
    public GetVideoInfoResponse queryDetailsBasedOnVideoID(String videoID) {
        // 设置默认配置信息
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        // 获取Acs客户端
        IAcsClient client = new DefaultAcsClient(profile);
        // 创建请求实例
        GetVideoInfoRequest request = new GetVideoInfoRequest();
        request.setVideoId(videoID);
        try {
            // 发送请求并获取响应
            GetVideoInfoResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return null;
    }

    @Override
    public CreateAuditResponse CreateAudit(String AuditContent) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditContent("[{\"VideoId\":\"" +
                AuditContent +
                "\"" +
                ",\"" +
                "Status" +
                "\":\"" +
                "Normal" +
                "\"}]");
        try {
            CreateAuditResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return null;
    }

    @Override
    public GetMezzanineInfoResponse GetMezzanineInfo(String AuditContent) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        GetMezzanineInfoRequest request = new GetMezzanineInfoRequest();
        request.setVideoId(AuditContent);

        try {
            GetMezzanineInfoResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return null;
    }


}
