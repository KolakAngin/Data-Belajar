package com.dcfanbase.storyapp

import com.dcfanbase.storyapp.data.api.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        val ranges = 0..10
        for (i in ranges) {
            val stories = ListStoryItem(
                "photoURL + $i",
                "createdAt + $i",
                "Name + $i",
                "description + $i",
                0.0 + i,
                "$i",
                0.0 + i
            )
            items.add(stories)
        }
        return items
    }
}