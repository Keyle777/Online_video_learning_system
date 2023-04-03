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

    int updateLoginCountById(@Param("id") String id);
    /**
     * 注册用户
     * @param registerVo
     */
    RespBean register(RegisterVo registerVo);

    /**
     * 统计某一天注册的人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);

    /**
     * 统计视频播放总次数
     * @param day
     * @return
     */
    Integer countVideoPlay(String day);

    /**
     * 统计当天新上传的课程数
     * @param day
     * @return
     */
    Integer countCourseInsert(String day);
    /**
     * 学员登录数统计
     */
    Integer countLogin(String day);

    /**
     * @return 进行数据的注销操作
     */
    RespBean loginOut();

    JsonPage<courseVO> selectCourseTostudy(@Param("page") Integer page, @Param("pageSize") Integer pageSize, String id);


}
