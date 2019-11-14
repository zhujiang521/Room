package com.zj.room.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 设备类（外部数据库）
 *
 * @author jiang zhu on 2019/10/25
 */
//entity声明定义，并且指定了映射数据表明
@Entity(tableName = "equip_type")
public class EquipType {

    //设置主键，并且定义自增增
    @PrimaryKey(autoGenerate = true)
    private int id;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "name")
    private String name;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "component")
    private int component;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "code")
    private String code;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "default_line_type")
    private int defaultLineType;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "icon")
    private String icon;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "remark")
    private String remark;


    //必须指定一个构造方法，room框架需要。并且只能指定一个
    //，如果有其他构造方法，则其他的构造方法必须添加@Ignore注解
    public EquipType() {
    }

    //其他构造方法要添加@Ignore注解
    @Ignore
    public EquipType(int id, String name, int component, String code, int defaultLineType, String icon, String remark) {
        this.id = id;
        this.name = name;
        this.component = component;
        this.code = code;
        this.defaultLineType = defaultLineType;
        this.icon = icon;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "EquipType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", component=" + component +
                ", code='" + code + '\'' +
                ", defaultLineType=" + defaultLineType +
                ", icon='" + icon + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getComponent() {
        return component;
    }

    public void setComponent(int component) {
        this.component = component;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDefaultLineType() {
        return defaultLineType;
    }

    public void setDefaultLineType(int defaultLineType) {
        this.defaultLineType = defaultLineType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
