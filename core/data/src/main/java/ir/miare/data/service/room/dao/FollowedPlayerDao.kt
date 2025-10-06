package ir.miare.data.service.room.dao

import androidx.room.Dao
import androidx.room.Query
import ir.miare.data.service.room.BaseDao
import ir.miare.data.service.room.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowedPlayerDao : BaseDao<PlayerEntity> {

    @Query(value = "SELECT * FROM followed_players ORDER BY name")
    fun getAll(): Flow<List<PlayerEntity>>

    @Query(value = "SELECT id FROM followed_players")
    fun getIds(): Flow<List<String>>

}