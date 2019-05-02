package me.debugjoker.sell.form;

import lombok.Data;

/**
 *
 * @author: ZhangMengwei
 * @create: 2019-05-02 18:39
 **/
@Data
public class CategoryForm {
    private Integer categoryId;
    //类目名
    private String categoryName;
    //类目编号
    private Integer categoryType;
}