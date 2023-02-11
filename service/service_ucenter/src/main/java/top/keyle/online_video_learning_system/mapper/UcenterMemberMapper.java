package top.keyle.online_video_learning_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.keyle.online_video_learning_system.entity.UcenterMember;

@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterDay(String day);

}
