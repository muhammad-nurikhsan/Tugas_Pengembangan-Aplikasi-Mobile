package com.muhammadnurikhsan.tugas10_pam

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.muhammadnurikhsan.tugas10_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas10pam.db.NotesDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NoteRepositoryTest {

    private lateinit var repository: NoteRepository

    @Before
    fun setup() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        NotesDatabase.Schema.create(driver)
        val database = NotesDatabase(driver)
        repository = NoteRepository(database)
    }

    @Test
    fun `insertNote then getAllNotes returns inserted note`() = runTest {
        repository.insertNote("Judul Test", "Isi konten test")
        val notes = repository.getAllNotes().first()
        assertEquals(1, notes.size)
        assertEquals("Judul Test", notes.first().title)
    }

    @Test
    fun `insertMultipleNotes then getAllNotes returns all notes`() = runTest {
        repository.insertNote("Note 1", "Isi 1")
        repository.insertNote("Note 2", "Isi 2")
        repository.insertNote("Note 3", "Isi 3")
        val notes = repository.getAllNotes().first()
        assertEquals(3, notes.size)
    }

    @Test
    fun `deleteNote then getAllNotes returns empty`() = runTest {
        repository.insertNote("Hapus Ini", "Konten")
        val noteId = repository.getAllNotes().first().first().id
        repository.deleteNote(noteId)
        val notes = repository.getAllNotes().first()
        assertTrue(notes.isEmpty())
    }

    @Test
    fun `updateNote then getNoteById returns updated data`() = runTest {
        repository.insertNote("Judul Lama", "Isi Lama")
        val noteId = repository.getAllNotes().first().first().id
        repository.updateNote(noteId, "Judul Baru", "Isi Baru")
        val updated = repository.getNoteById(noteId).first()
        assertNotNull(updated)
        assertEquals("Judul Baru", updated.title)
        assertEquals("Isi Baru", updated.content)
    }

    @Test
    fun `toggleFavorite then note is_favorite becomes 1`() = runTest {
        repository.insertNote("Favorit", "Konten favorit")
        val noteId = repository.getAllNotes().first().first().id
        repository.toggleFavorite(noteId)
        val note = repository.getNoteById(noteId).first()
        assertEquals(1L, note?.is_favorite)
    }

    @Test
    fun `toggleFavorite twice then is_favorite returns to 0`() = runTest {
        repository.insertNote("Toggle", "Konten")
        val noteId = repository.getAllNotes().first().first().id
        repository.toggleFavorite(noteId)
        repository.toggleFavorite(noteId)
        val note = repository.getNoteById(noteId).first()
        assertEquals(0L, note?.is_favorite)
    }

    @Test
    fun `searchNotes returns matching note`() = runTest {
        repository.insertNote("Kotlin Tips", "Belajar coroutines")
        repository.insertNote("Android UI", "Compose layout")
        val results = repository.searchNotes("Kotlin").first()
        assertEquals(1, results.size)
        assertEquals("Kotlin Tips", results.first().title)
    }
}