package top.keyle.online_video_learning_system.entry.vo.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectNestedVo {
 private String id;
 private String title;
 private List<SubjectVo> children = new ArrayList<>();
}
