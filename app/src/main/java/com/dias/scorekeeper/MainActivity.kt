package com.dias.scorekeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dias.scorekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var mScore1 = 0
    private var mScore2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.let {
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1)
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2)
            binding.apply {
                score1.text = mScore1.toString()
                score2.text = mScore2.toString()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        menu?.findItem(R.id.night_mode)
            ?.setTitle(if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) R.string.day_mode else R.string.night_mode)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.night_mode -> {
                val nightMode = AppCompatDelegate.getDefaultNightMode()
                AppCompatDelegate.setDefaultNightMode(
                    when (nightMode) {
                        AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
                        else -> AppCompatDelegate.MODE_NIGHT_YES
                    }
                )
                recreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun decreaseScore(view: View) {
        mScore1 = ensureAboveMinus(--mScore1)
        mScore2 = ensureAboveMinus(--mScore2)
        when (view.id) {
            R.id.decreaseTeam1 -> binding.score1.text = mScore1.toString()
            R.id.decreaseTeam2 -> binding.score2.text = mScore2.toString()
        }
    }

    fun increaseScore(view: View) {
        when (view.id) {
            R.id.increaseTeam1 -> binding.score1.text = (++mScore1).toString()
            R.id.increaseTeam2 -> binding.score2.text = (++mScore2).toString()
        }
    }

    private fun ensureAboveMinus(score: Int): Int {
        return if (score > 0) score else 0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SCORE_1, mScore1)
        outState.putInt(STATE_SCORE_2, mScore2)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val STATE_SCORE_1 = "Team 1 Score"
        private const val STATE_SCORE_2 = "Team 2 Score"
    }
}