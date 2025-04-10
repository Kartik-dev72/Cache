package com.jetispot.player

import android.content.Context
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.util.Util
import java.io.File

object OfflineCache {
    private var simpleCache: SimpleCache? = null

    fun getInstance(context: Context): SimpleCache {
        if (simpleCache == null) {
            val cacheSize: Long = 100L * 1024L * 1024L // 100 MB
            val evictor = LeastRecentlyUsedCacheEvictor(cacheSize)
            val databaseProvider = StandaloneDatabaseProvider(context)
            val cacheDir = File(context.cacheDir, "media_cache")
            simpleCache = SimpleCache(cacheDir, evictor, databaseProvider)
        }
        return simpleCache!!
    }
}
