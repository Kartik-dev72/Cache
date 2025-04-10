package com.jetispot

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.jetispot.player.OfflineCache

class MainActivity : AppCompatActivity() {
    private lateinit var player: SimpleExoPlayer
    private var isOfflineMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Offline Mode Toggle
        val toggle = findViewById<Switch>(R.id.offlineSwitch)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            isOfflineMode = isChecked
            if (isChecked) {
                initializePlayerWithCache()
            } else {
                initializePlayerWithoutCache()
            }
        }

        initializePlayerWithoutCache()
    }

    private fun initializePlayerWithCache() {
        player = SimpleExoPlayer.Builder(this).build()
        val cache = OfflineCache.getInstance(this)
        val upstreamFactory = DefaultDataSource.Factory(this)
        val cacheDataSource = CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        player.setMediaSource(cacheDataSource.createDataSource().let {
            val item = MediaItem.fromUri("https://p.scdn.co/mp3-preview/sample.mp3")
            player.setMediaItem(item)
            player.prepare()
            player.play()
        })
    }

    private fun initializePlayerWithoutCache() {
        player = SimpleExoPlayer.Builder(this).build()
        val item = MediaItem.fromUri("https://p.scdn.co/mp3-preview/sample.mp3")
        player.setMediaItem(item)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
