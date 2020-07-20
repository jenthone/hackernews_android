package io.github.jenthone.hackernews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.jenthone.hackernews.databinding.ActivityMainBinding
import io.github.jenthone.hackernews.domain.entity.StoryType
import io.github.jenthone.hackernews.ui.story.StoryPagerAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: StoryPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        adapter = StoryPagerAdapter(
            supportFragmentManager
        )
        with(binding) {
            vpMain.adapter = adapter
            vpMain.offscreenPageLimit = StoryType.values().size
            tlMain.setupWithViewPager(vpMain)
        }
    }
}
