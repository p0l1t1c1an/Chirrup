package cs309.sr2.chirrupfrontend.utils;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * this class is from https://git.linux.iastate.edu/cs309/tutorials/blob/android_unit2_1_volley/AndroidVolley/app/src/main/java/com/example/sumon/androidvolley/utils/LruBitmapCache.java
 * and is used as a cache for bitmap images
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache {

    /**
     * get the default LruCacheSize
     *
     * @return default LruCacheSize
     */
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    /**
     * Initialize default LruBitmapCache
     */
    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    /**
     * Initialize the LruBitmapCache with a specific size in kilobytes
     *
     * @param sizeInKiloBytes size of LruBitmapCache in kilobytes
     */
    public LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    /**
     * get the size of the bitmap value
     *
     * @param key unused parameter
     * @param value value to get the size of
     * @return size of the bitmap value
     */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    /**
     * get a bitmap from a url
     *
     * @param url url of bitmap
     * @return bitmap from url
     */
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    /**
     * put a bitmap at a location referenced by url
     *
     * @param url url location to put bitmap
     * @param bitmap bitmap to put
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
