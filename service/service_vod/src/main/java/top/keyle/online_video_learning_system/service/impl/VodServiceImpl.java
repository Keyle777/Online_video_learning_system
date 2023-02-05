package top.keyle.online_video_learning_system.service.impl;

import com.aliyun.oss.common.utils.StringUtils;
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
public class VodServiceImpl implements VodService {
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

    @Override
    public Boolean deleteVodById(String videoSourceId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoSourceId);
            client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Boolean removeMoreVideo(List<String> videoIdList) {
        try {
            // 初始化对象
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // videoIdList值转成 1,2,3
            String videoIds = StringUtils.join(String.valueOf(videoIdList.toArray()), ",");
            // 向request设置视频id
            request.setVideoIds(videoIds);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return true;
        } catch (ClientException e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public GetVideoInfoResponse queryDetailsBasedOnVideoID(String videoID) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        GetVideoInfoRequest request = new GetVideoInfoRequest();
        request.setVideoId(videoID);
        try {
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
