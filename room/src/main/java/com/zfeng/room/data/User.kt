package com.zfeng.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,

    //Room 将字段名称用作数据库中的列名称。如果您希望列具有不同的名称，请将 @ColumnInfo 注释添加到字段
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)
