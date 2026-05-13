package com.muhammadnurikhsan.tugas10_pam

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.cash.turbine.test
import com.muhammadnurikhsan.tugas10_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas10_pam.data.NotesUiState
import com.muhammadnurikhsan.tugas10_pam.viewmodel.NoteViewModel
import com.muhammadnurikhsan.tugas10pam.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {

    private lateinit var repository: NoteRepository
    private lateinit var viewModel: NoteViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        NotesDatabase.Schema.create(driver)
        val database = NotesDatabase(driver)
        repository = NoteRepository(database)
        viewModel = NoteViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val freshViewModel = NoteViewModel(repository)
        assertIs<NotesUiState.Loading>(freshViewModel.uiState.value)
    }

    @Test
    fun `addNote then uiState becomes Content`() = runTest {
        repository.insertNote("Test Title", "Test Content")
        val state = viewModel.uiState.first { it is NotesUiState.Content } as NotesUiState.Content
        assertEquals(1, state.notes.size)
    }

    @Test
    fun `deleteNote then uiState becomes Empty`() = runTest {
        repository.insertNote("To Delete", "Content")
        val contentState = viewModel.uiState.first { it is NotesUiState.Content } as NotesUiState.Content
        val noteId = contentState.notes.first().id
        repository.deleteNote(noteId)
        // Tunggu hingga state bukan Loading dan bukan Content
        val afterDelete = viewModel.uiState.first { it is NotesUiState.Empty || it is NotesUiState.Error }
        assertIs<NotesUiState.Empty>(afterDelete)
    }

    @Test
    fun `searchQuery filters notes correctly`() = runTest {
        repository.insertNote("Kotlin Flow", "Coroutines")
        repository.insertNote("Android UI", "Compose")
        viewModel.onSearchQueryChange("Kotlin")
        val state = viewModel.uiState.first { it is NotesUiState.Content } as NotesUiState.Content
        assertEquals(1, state.notes.size)
        assertEquals("Kotlin Flow", state.notes.first().title)
    }

    @Test
    fun `toggleFavorite changes favorite status`() = runTest {
        repository.insertNote("Favorit", "Isi")
        val contentState = viewModel.uiState.first { it is NotesUiState.Content } as NotesUiState.Content
        val noteId = contentState.notes.first().id
        repository.toggleFavorite(noteId)
        val favorites = repository.getFavorites().first()
        assertTrue(favorites.isNotEmpty())
        assertEquals(noteId, favorites.first().id)
    }

    // Flow tests dengan Turbine
    @Test
    fun `uiState flow emits Content after insert`() = runTest {
        viewModel.uiState.test {
            // skip semua state awal sampai dapat non-Loading
            var item = awaitItem()
            while (item is NotesUiState.Loading) { item = awaitItem() }
            // sekarang state = Empty, insert note
            repository.insertNote("Flow Test", "Isi flow")
            val content = awaitItem()
            assertIs<NotesUiState.Content>(content)
            assertEquals("Flow Test", content.notes.first().title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState flow emits Empty after delete all notes`() = runTest {
        repository.insertNote("Note", "Isi")
        viewModel.uiState.test {
            // skip Loading jika ada
            var item = awaitItem()
            while (item is NotesUiState.Loading) { item = awaitItem() }
            // sekarang harus Content
            assertIs<NotesUiState.Content>(item)
            val noteId = item.notes.first().id
            repository.deleteNote(noteId)
            val empty = awaitItem()
            assertIs<NotesUiState.Empty>(empty)
            cancelAndIgnoreRemainingEvents()
        }
    }
}