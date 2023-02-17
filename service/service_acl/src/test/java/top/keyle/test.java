package top.keyle;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author TMJIE5200
 * @date 2023-02-17 20:50:17
 * @week 星期五
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Test
    public void test1(){
        String s = StringEscapeUtils.unescapeJava("[{\"redirect\":\"noredirect\",\"path\":\"/banner\",\"component\":\"Layout\",\"hidden\":false,\"children\":[{\"path\":\"save\",\"component\":\"/edu/banner/save\",\"hidden\":false,\"meta\":{\"title\":\"添加\"},\"name\":\"name_1195352909401657346\"},{\"path\":\"list\",\"component\":\"/edu/banner/list\",\"hidden\":false,\"meta\":{\"title\":\"Bander列表\"},\"name\":\"name_1195353513549205505\"},{\"path\":\"edit/:id\",\"component\":\"/edu/banner/save\",\"hidden\":true,\"meta\":{\"title\":\"修改\"},\"name\":\"name_1195353051395624961\"}],\"meta\":{\"title\":\"幻灯片管理\"},\"name\":\"name_1195352547621965825\"}]");
        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println(jsonObject);
    }
}
