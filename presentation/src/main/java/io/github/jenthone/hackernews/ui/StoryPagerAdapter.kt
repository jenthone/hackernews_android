package io.github.jenthone.hackernews.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.jenthone.hackernews.domain.entity.StoryType

class StoryPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): Fragment {
        val type = StoryType.values()[position]
        return StoryFragment.newInstance(type.name)
    }

    override fun getCount(): Int {
        return StoryType.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val type = StoryType.values()[position]
        return type.name
    }
}