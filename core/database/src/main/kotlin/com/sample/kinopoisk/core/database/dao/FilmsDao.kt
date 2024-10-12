package com.sample.kinopoisk.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.sample.kinopoisk.core.database.model.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmsDao {

    @Query("SELECT * FROM Films ORDER BY localized_name")
    fun getFilms(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM Films WHERE film_id = :filmId")
    fun getFilmById(filmId: Long): Flow<FilmEntity?>

    @Upsert
    suspend fun insert(item: FilmEntity)

    @Upsert
    suspend fun insert(items: List<FilmEntity>)

    @Delete
    suspend fun delete(item: FilmEntity)

    @Query("DELETE FROM Films")
    suspend fun delete()

    @Transaction
    suspend fun clearInsert(items: List<FilmEntity>) {
        delete()
        insert(items)
    }
}
