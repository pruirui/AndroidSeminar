package demo.util;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class resType {
    List data = new ArrayList();
    String res = "false";

    //生成默认返回json类型
    public resType(List object, String res) {
        this.res = res;
        this.data = object;
    }
    //返回users构造方法
    public resType(String res) {
        this.res = res;
    }
}
