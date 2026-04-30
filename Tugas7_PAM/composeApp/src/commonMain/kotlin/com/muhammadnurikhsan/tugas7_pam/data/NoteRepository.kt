package com.muhammadnurikhsan.tugas7_pam.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.muhammadnurikhsan.tugas7pam.db.NoteEntity
import com.muhammadnurikhsan.tugas7pam.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class NoteRepository(private val database: NotesDatabase) {

    private val queries = database.noteQueries

    fun getAllNotes(): Flow<List<NoteEntity>> =
        queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)

    fun getFavorites(): Flow<List<NoteEntity>> =
        queries.selectFavorites()
            .asFlow()
            .mapToList(Dispatchers.Default)

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        val like = "%$query%"
        return queries.selectByQuery(like, like)
            .asFlow()
            .mapToList(Dispatchers.Default)
    }

    fun getNoteById(id: Long): Flow<NoteEntity?> =
        queries.selectById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)

    suspend fun insertNote(title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.Default) {
            queries.insert(title, content, now, now)
        }
    }

    suspend fun updateNote(id: Long, title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.Default) {
            queries.update(title, content, now, id)
        }
    }

    suspend fun toggleFavorite(id: Long) {
        withContext(Dispatchers.Default) {
            queries.toggleFavorite(id)
        }
    }

    suspend fun deleteNote(id: Long) {
        withContext(Dispatchers.Default) {
            queries.delete(id)
        }
    }
}