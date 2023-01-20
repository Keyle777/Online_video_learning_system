package top.keyle.online_video_learning_system.entry.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author TMJIE5200
 * @date 2023-01-20 14:09:50
 * @week 星期五
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
