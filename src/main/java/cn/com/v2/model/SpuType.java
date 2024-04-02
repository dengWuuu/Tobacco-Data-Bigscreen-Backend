package cn.com.v2.model;

/**
 * @author Wu
 * @date 2024年04月02日 14:47
 */
public enum SpuType {
    TOBACCO(1, "卷烟"),
    ALCOHOL(2, "酒水"),
    SNACK(3, "零食"),
    OTHER(4, "其他");

    private final Integer code;
    private final String name;

    SpuType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
