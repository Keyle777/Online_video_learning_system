package top.keyle.universal_tool;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

// 通用的返回各种类型分页结果的信息类
@Data
public class JsonPage<T> implements Serializable {

    // 根据实际需求,定义需要的分页信息
    // 实际开发中可能较多,我们这里就声明4个基本的
    @ApiModelProperty(value = "总页数",name = "totalPages")
    private Integer totalPages;
    @ApiModelProperty(value = "总条数",name = "totalCount")
    private Long totalCount;
    @ApiModelProperty(value = "当前页码",name = "page")
    private Integer page;
    @ApiModelProperty(value = "当前页的数据条数",name = "pageSize")
    private Integer pageSize;
    @ApiModelProperty(value = "前一页的页码",name = "prePage")
    private Integer prePage;
    @ApiModelProperty(value = "下一页的页码",name = "nextPage")
    private Integer nextPage;
    @ApiModelProperty(value = "是否为第一页",name = "isFirstPage")
    private Boolean isFirstPage;
    @ApiModelProperty(value = "是否为最后一页",name = "isLastPage")
    private Boolean isLastPage;
    @ApiModelProperty(value = "是否有前一页",name = "isLastPage")
    private Boolean hasPreviousPage;
    @ApiModelProperty(value = "是否有下一页",name = "isLastPage")
    private Boolean hasNextPage;
    @ApiModelProperty(value = "导航页码数",name = "navigatePages")
    private Integer navigatePages;
    @ApiModelProperty(value = "所有导航页号",name = "navigatePageNums")
    private int[] navigatePageNums;
    @ApiModelProperty(value = "导航条上的第一页",name = "navigateFirstPage")
    private Integer navigateFirstPage;
    @ApiModelProperty(value = "导航条上的最后一页",name = "navigateLastPage")
    private Integer navigateLastPage;

    // 如果需要再添加其它属性即可

    // 除了分页信息,还有查询出的分页数据
    @ApiModelProperty(value = "分页数据",name = "list")
    private List<T> list;

    // 上面定义了所有分页数据需要的属性
    // 下面可以编写一个将PageInfo类型转换为JsonPage类型的方法
    // 如果需要将其它框架的分页对象转换,例如SpringData的Page类,那么就再编写新的方法即可
    public static <T> JsonPage<T> restPage(PageInfo<T> pageInfo){
        // 开始进行转换,基本思路是将pageInfo对象中的数据赋值给JsonPage对象
        JsonPage<T> result=new JsonPage<>();
        // 赋值分页信息
        result.setTotalPages(pageInfo.getPages());
        result.setTotalCount(pageInfo.getTotal());
        result.setPage(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setPrePage(pageInfo.getPrePage());
        result.setNextPage(pageInfo.getNextPage());
        result.setIsFirstPage(pageInfo.isIsFirstPage());
        result.setIsLastPage(pageInfo.isIsLastPage());
        result.setHasPreviousPage(pageInfo.isHasPreviousPage());
        result.setHasNextPage(pageInfo.isHasNextPage());
        result.setNavigatePages(pageInfo.getNavigatePages());
        result.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
        result.setNavigateLastPage(pageInfo.getNavigateLastPage());
        result.setNavigatePageNums(pageInfo.getNavigatepageNums());
        //  赋值分页数据
        result.setList(pageInfo.getList());
        // 别忘了返回
        return result;
    }
}
