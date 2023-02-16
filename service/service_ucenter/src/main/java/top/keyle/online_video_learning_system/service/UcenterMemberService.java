package top.keyle.online_video_learning_system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.keyle.online_video_learning_system.entity.UcenterMember;
import top.keyle.online_video_learning_system.entity.vo.LoginVo;
import top.keyle.online_video_learning_system.entity.vo.RegisterVo;
import top.keyle.online_video_learning_system.entity.vo.courseVO.courseVO;
import top.keyle.universal_tool.JsonPage;
import top.keyle.universal_tool.RespBean;

public interface UcenterMemberService extends IService<UcenterMember> {
    /**
     * 登录获取token令牌
     * @param member
     * @return
     */
    String login(LoginVo member);

    /**
     * 注册用户
     * @param registerVo
     */
    RespBean register(RegisterVo registerVo);

    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);

    /**
     * @return 进行数据的注销操作
     */
    RespBean loginOut();

    JsonPage<courseVO> selectCourseTostudy(@Param("page") Integer page, @Param("pageSize") Integer pageSize, String id);
}
