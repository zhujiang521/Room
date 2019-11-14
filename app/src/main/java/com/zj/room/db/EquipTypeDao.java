package com.zj.room.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * sql语句
 *
 * @author jiang zhu on 2019/11/12
 */
//注解配置sql语句
@Dao
public interface EquipTypeDao {

    //所有的CURD根据primary key进行匹配

    
    //------------------------query------------------------
    //简单sql语句，查询user表所有的column
    @Query("SELECT * FROM equip_type")
    List<EquipType> getAll();

//    //根据条件查询，方法参数和注解的sql语句参数一一对应
//    @Query("SELECT * FROM equip_type WHERE id IN (:userIds)")
//    List<EquipType> loadAllByIds(int[] userIds);

    //同上
    @Query("SELECT * FROM equip_type WHERE id = :cid")
    LiveData<EquipType> findByUid(int cid);



    //-----------------------insert----------------------

    // OnConflictStrategy.REPLACE表示如果已经有数据，那么就覆盖掉
    //数据的判断通过主键进行匹配，也就是uid，非整个EquipType对象
    //返回Long数据表示，插入条目的主键值（uid）
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(EquipType equipType);
    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(EquipType... equipTypes);
    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<EquipType> equipTypeList);




    //---------------------update------------------------
    //更新已有数据，根据主键（uid）匹配，而非整个user对象
    //返回类型int代表更新的条目数目，而非主键uid的值。
    //表示更新了多少条目
    @Update()
    int update(EquipType equipType);
    //同上
    @Update()
    int updateAll(EquipType... equipTypes);
    //同上
    @Update()
    int updateAll(List<EquipType> equipTypeList);

    //-------------------delete-------------------
    //删除user数据，数据的匹配通过主键uid实现。
    //返回int数据表示删除了多少条。非主键uid值。
    @Delete
    int delete(EquipType equipType);
    //同上
    @Delete
    int deleteAll(List<EquipType> equipTypeList);
    //同上
    @Delete
    int deleteAll(EquipType... equipTypes);
    

}
