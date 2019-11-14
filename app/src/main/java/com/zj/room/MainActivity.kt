package com.zj.room

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Room
import com.zj.room.db.AppDatabase
import com.zj.room.db.EquipType
import com.zj.room.db.EquipTypeDao
import com.zj.room.utils.ThreadManager
import com.zj.room.utils.utilcode.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    //得到AppDatabase 对象
    private var db : AppDatabase? = null
    //得到userDao对象
    private var equipTypeDao : EquipTypeDao? = null
    //线程池
    private var threadPool = ThreadManager.getThreadPool()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initListener()
    }

    private fun initData() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "room-database"
        )
            //添加数据库变动迁移
            //.addMigrations(AppDatabase.MIGRATION_1_2)
            //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
            //他可能造成主线程lock以及anr
            //所以我们的操作都是在新线程完成的
            // .allowMainThreadQueries()
            .build()
        equipTypeDao = db!!.equipTypeDao()
    }

    private fun initListener() {
        mainBtnAddOne.setOnClickListener(this)
        mainBtnAddSome.setOnClickListener(this)
        mainBtnDeleteOne.setOnClickListener(this)
        mainBtnDeleteSome.setOnClickListener(this)
        mainBtnUpdateOne.setOnClickListener(this)
        mainBtnUpdateSome.setOnClickListener(this)
        mainBtnQueryOne.setOnClickListener(this)
        mainBtnQuerySome.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.mainBtnAddOne -> {
                insertOne()
            }
            R.id.mainBtnAddSome -> {
                insertSome()
            }
            R.id.mainBtnDeleteOne -> {
                deleteOne()
            }
            R.id.mainBtnDeleteSome -> {
                deleteSome()
            }
            R.id.mainBtnUpdateOne -> {
                updateOne()
            }
            R.id.mainBtnUpdateSome -> {
                updateSome()
            }
            R.id.mainBtnQueryOne -> {
                queryOne()
            }
            R.id.mainBtnQuerySome -> {
                querySome()
            }
        }
    }

    //查询多条
    @SuppressLint("SetTextI18n")
    private fun querySome() {
        threadPool.execute{
            val equipTypeList =  equipTypeDao!!.all
            runOnUiThread {
                mainTvResult.text = "${equipTypeList.size}  $equipTypeList"
            }
        }
    }

    //查询一条
    private fun queryOne() = threadPool.execute{
        val equipType =  equipTypeDao!!.findByUid(1)
        runOnUiThread {
            equipType.observe(this, Observer {
                mainTvResult.text =it.toString()
            })
        }
    }

    //修改多条
    private fun updateSome() {

    }

    //修改一条
    private fun updateOne() {

    }

    //删除一条
    private fun deleteOne() {
        threadPool.execute{
            equipTypeDao!!.deleteAll()
        }
    }

    //删除一条
    private fun deleteSome() {

    }

    //增加一条
    private fun insertOne() = threadPool.execute {
        val result = equipTypeDao!!.insert(
            EquipType(
                1,
                "朱江",
                2,
                "你猜",
                3,
                "http://192.168.8.18:81/zentao/file-read-4910.png",
                "呵呵"
            )
        )
        runOnUiThread {
            if (result > 0)
                ToastUtils.showShort("新增成功")
        }
    }

    //增加多条
    private fun insertSome() {
        val equipTypeList = arrayListOf<EquipType>()
        for (index in 2..100) {
            equipTypeList.add(
                EquipType(
                    index,
                    "朱江",
                    index + 1,
                    "你猜",
                    index + 2,
                    "http://192.168.8.18:81/zentao/file-read-4910.png",
                    "呵呵"
                )
            )

        }
        threadPool.execute {
            val result = equipTypeDao!!.insertAll(equipTypeList)
            runOnUiThread {
                if (result[0] > 0)
                    ToastUtils.showShort("新增成功")
            }
        }
    }


}
