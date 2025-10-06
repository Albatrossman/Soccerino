package ir.miare.data.service.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(objs: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg objs: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(objs: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg objs: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: T)

    @Delete
    suspend fun delete(objs: List<T>)

    @Delete
    suspend fun delete(vararg objs: T)

    @Delete
    suspend fun delete(obj: T)

}