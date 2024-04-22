package com.rookie.oss.starter.common.domain.req;

import com.rookie.oss.starter.common.exception.ErrorEnum;
import lombok.Data;

/**
 * @author eumenides
 * @description
 * @date 2024/4/22
 */
@Data
public class ApiResult<T> {

    private Boolean success;

    private Integer errCode;

    private String errMsg;

    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(null);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(data);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> fail(Integer code, String msg) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(code);
        result.setErrMsg(msg);
        return result;
    }

    public static <T> ApiResult<T> fail(ErrorEnum errorEnum) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(errorEnum.getErrorCode());
        result.setErrMsg(errorEnum.getErrorMsg());
        return result;
    }

    public boolean isSuccess() {
        return this.success;
    }




}
