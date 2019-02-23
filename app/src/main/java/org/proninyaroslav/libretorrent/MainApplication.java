/*
 * Copyright (C) 2016-2019 Yaroslav Pronin <proninyaroslav@mail.ru>
 *
 * This file is part of LibreTorrent.
 *
 * LibreTorrent is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LibreTorrent is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LibreTorrent.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.proninyaroslav.libretorrent;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.greenrobot.eventbus.EventBus;
import org.proninyaroslav.libretorrent.core.TorrentEngine;
import org.proninyaroslav.libretorrent.core.storage.AppDatabase;
import org.proninyaroslav.libretorrent.core.storage.FeedRepository;
import org.proninyaroslav.libretorrent.core.storage.TorrentRepository;
import org.proninyaroslav.libretorrent.core.utils.old.Utils;
import org.proninyaroslav.libretorrent.settings.old.SettingsManager;

@ReportsCrashes(mailTo = "proninyaroslav@mail.ru",
                mode = ReportingInteractionMode.DIALOG,
                reportDialogClass = ErrorReportActivity.class)

public class MainApplication extends Application
{
    @SuppressWarnings("unused")
    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);

        Utils.migrateTray2SharedPreferences(this);
        ACRA.init(this);
        EventBus.builder().logNoSubscriberMessages(false).installDefaultEventBus();

        /* TODO: temporary */
        TorrentEngine.getInstance().setContext(this);
        TorrentEngine.getInstance().start();
    }

    public TorrentRepository getTorrentRepository()
    {
        return TorrentRepository.getInstance(AppDatabase.getInstance(this));
    }

    public FeedRepository getFeedRepository()
    {
        return FeedRepository.getInstance(AppDatabase.getInstance(this));
    }
}