package com.lsj.ballblast.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("服务器默认返回数据格式")
public class Result<T> implements Serializable {
    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    private int state;
    /**
     * 提示信息
     */
    @ApiModelProperty("提示信息")
    private String msg;
    /**
     * 具体内容
     */
    @ApiModelProperty("返回的具体数据")
    private T data;

}
