package io.github.jenthone.hackernews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.jenthone.hackernews.R
import io.github.jenthone.hackernews.ui.story.StoryPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: StoryPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        adapter = StoryPagerAdapter(
            supportFragmentManager
        )
        vpMain.adapter = adapter
        tlMain.setupWithViewPager(vpMain)
    }
}
