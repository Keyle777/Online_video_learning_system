package top.keyle.msm_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.keyle.msm_service.service.MsmService;

import java.util.Map;

/**
 * @author TMJIE5200
 * @date 2023-01-10 14:43:49
 * @week 星期二
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String PhoneNumbers, String templateCode, Map<String, Object> param) {
            if (ObjectUtils.isEmpty(PhoneNumbers)) {
                return false;
            }
            DefaultProfile profile =
                    DefaultProfile.getProfile("cn-hangzhou", "LTAI5tErf5nrY2jvQbLphVvn",
                            "UlnXLmesJUeeEL514otNmldNzrIyVx");
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            // 设置相关固定的参数
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            // 设置相关的参数
            // 手机号
            request.putQueryParameter("PhoneNumbers", PhoneNumbers);
            // 申请阿里云 签名失败
            request.putQueryParameter("SignName", "阿里云短信测试");
            //申请阿里云 模板code
            request.putQueryParameter("TemplateCode", templateCode);
            //验证码数据，转换json数据传递
            request.putQueryParameter("TemplateParam",
                    JSONObject.toJSONString(param));
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
                return response.getHttpResponse().isSuccess();
            } catch (ClientException e) {
                e.printStackTrace();
            }
            return false;

    }
    /**
     * 发送短信
     */

}
