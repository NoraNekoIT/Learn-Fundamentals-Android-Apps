package com.noranekoit.bfaausergithub3.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.noranekoit.bfaausergithub3.R
import com.noranekoit.bfaausergithub3.ui.main.MainActivity


class StackFavoriteWidget : AppWidgetProvider() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action == ACTION_UPDATE) {
                val component = context?.let { context ->
                    ComponentName(
                        context,
                        StackFavoriteWidget::class.java
                    )
                }
                AppWidgetManager.getInstance(context).apply {
                    notifyAppWidgetViewDataChanged(
                        getAppWidgetIds(component),
                        R.id.stack_view
                    )
                }
            }
        }
        super.onReceive(context, intent)
    }

    companion object {
        private const val ACTION_UPDATE = "action_update"

        @RequiresApi(Build.VERSION_CODES.M)
        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val refreshUpdateIntent = Intent(context, StackFavoriteWidget::class.java)
            refreshUpdateIntent.action = ACTION_UPDATE
            refreshUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            val refreshUpdatePendingIntent = PendingIntent.getBroadcast(context,
                0,
                refreshUpdateIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            val toMainActivityIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val toMainPendingIntent = PendingIntent.getActivity(context,
                0,
                toMainActivityIntent,
                PendingIntent.FLAG_IMMUTABLE or 0)

            val views = RemoteViews(context.packageName, R.layout.stack_favorite_widget)
            views.apply {
                setRemoteAdapter(R.id.stack_view, intent)
                setEmptyView(R.id.stack_view, R.id.empty_view)
                setOnClickPendingIntent(R.id.ib_refresh_widget, refreshUpdatePendingIntent)
                setOnClickPendingIntent(R.id.banner_text, toMainPendingIntent)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

