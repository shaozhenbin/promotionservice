package common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 11:00.
 * Desc:
 * ==================================
 */
@Data
public class ObjectResponse<T> implements Serializable{
    private T t;
    private Integer code;
    private String msg;

}
