package com.zj.room.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author jiang zhu on 2019/11/12
 */
//注解指定了database的表映射实体数据以及版本等信息
@Database(entities = {EquipType.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //RoomDatabase提供直接访问底层数据库实现，我们通过定义抽象方法返回具体Dao
//然后进行数据库增删该查的实现。
    public abstract EquipTypeDao equipTypeDao();


    //数据库变动添加Migration
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //数据库的具体变动
            //类型是integer，不为空，默认值是0
            database.execSQL("ALTER TABLE equip_type "
                    + " ADD COLUMN age INTEGER NOT NULL DEFAULT 0");
        }
    };

}