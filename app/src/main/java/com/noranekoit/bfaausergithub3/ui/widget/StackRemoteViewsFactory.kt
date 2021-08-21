package com.noranekoit.bfaausergithub3.ui.widget

import android.content.Context

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable

import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.noranekoit.bfaausergithub3.R
import com.noranekoit.bfaausergithub3.data.model.UserGithub
import java.lang.Exception

class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {
    private var list: List<UserGithub> = listOf()
    private var cursor: Cursor? = null

    override fun onCreate() {}

    override fun onDataSetChanged() {
        cursor?.close()

        val identityToken = Binder.clearCallingIdentity()

        cursor = mContext.contentResolver?.query(DatabaseContract.FavoriteUserColumns.CONTENT_URI,
            null,
            null,
            null,
            null)

        cursor?.let {
            val listConverted = MappingHelper.mapCursorToArrayList(it)
            list = listConverted
        }

        Binder.restoreCallingIdentity(identityToken)
    }


    override fun onDestroy() {
        cursor?.close()
        list = listOf()
    }

    override fun getCount(): Int = list.size

    override fun getViewAt(position: Int): RemoteViews {

        val rViews = RemoteViews(mContext.packageName, R.layout.widget_item)

        if (!list.isNullOrEmpty()) {
            rViews.apply {
                list[position].apply {
                    setImageViewBitmap(
                        R.id.iv_stack_Favorite, avatar_url.toBitmap(mContext)
                    )
                    setTextViewText(
                        R.id.tv_stack_favorite, login
                    )
                }
            }
        }

        return rViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = true

    private fun String.toBitmap(context: Context): Bitmap {
        var bitmap: Bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_load)

        val option = RequestOptions()
            .error(R.drawable.ic_person_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        try {
            Glide.with(context)
                .setDefaultRequestOptions(option)
                .asBitmap()
                .load(this)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        bitmap = resource
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

}