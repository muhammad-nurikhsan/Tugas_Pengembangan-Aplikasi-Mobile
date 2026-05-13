package com.muhammadnurikhsan.tugas10_pam

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.runner.AndroidJUnit4
import com.muhammadnurikhsan.tugas10_pam.util.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun emptyStateTag_isDisplayed() {
        composeRule.setContent {
            Box(
                modifier         = Modifier.fillMaxSize().testTag(TestTags.EMPTY_STATE),
                contentAlignment = Alignment.Center
            ) {
                Text("nothing here yet")
            }
        }
        composeRule.onNodeWithTag(TestTags.EMPTY_STATE).assertIsDisplayed()
    }

    @Test
    fun addButtonTag_isDisplayed() {
        composeRule.setContent {
            Box(modifier = Modifier.testTag(TestTags.ADD_BUTTON)) {
                Text("+")
            }
        }
        composeRule.onNodeWithTag(TestTags.ADD_BUTTON).assertIsDisplayed()
    }

    @Test
    fun searchFieldTag_isDisplayed() {
        composeRule.setContent {
            Box(modifier = Modifier.testTag(TestTags.SEARCH_FIELD)) {
                Text("search notes")
            }
        }
        composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).assertIsDisplayed()
    }

    @Test
    fun aiChatButtonTag_isDisplayed() {
        composeRule.setContent {
            Box(modifier = Modifier.testTag(TestTags.AI_CHAT_BUTTON)) {
                Text("AI")
            }
        }
        composeRule.onNodeWithTag(TestTags.AI_CHAT_BUTTON).assertIsDisplayed()
    }
}