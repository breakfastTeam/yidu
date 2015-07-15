package com.smartbean.util.fastjson;

public class JsonResult {

    private String message;//返回成功或失败的提示信息

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    private Object obj;//返回的实体数据
    private boolean success;//返回是否成功的标示
    private String code;//报文编码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
