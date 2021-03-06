/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2000-2010 Oracle.  All rights reserved.
 *
 */

package bdb.com.sleepycat.collections;

import bdb.com.sleepycat.compat.DbCompat;
import bdb.com.sleepycat.je.Cursor;
import bdb.com.sleepycat.je.CursorConfig;
import bdb.com.sleepycat.je.DatabaseException;
import bdb.com.sleepycat.util.keyrange.KeyRange;
import bdb.com.sleepycat.util.keyrange.RangeCursor;

class MyRangeCursor extends RangeCursor {

    private DataView view;
    private boolean isRecnoOrQueue;
    private boolean writeCursor;

    MyRangeCursor(KeyRange range,
                  CursorConfig config,
                  DataView view,
                  boolean writeAllowed)
        throws DatabaseException {

        super(range, view.dupsRange, view.dupsOrdered,
              openCursor(view, config, writeAllowed));
        this.view = view;
        isRecnoOrQueue = view.recNumAllowed && !view.btreeRecNumDb;
        writeCursor = isWriteCursor(config, writeAllowed);
    }

    /**
     * Returns true if a write cursor is requested by the user via the cursor
     * config, or if this is a writable cursor and the user has not specified a
     * cursor config.  For CDB, a special cursor must be created for writing.
     * See CurrentTransaction.openCursor.
     */
    private static boolean isWriteCursor(CursorConfig config,
                                         boolean writeAllowed) {
        return DbCompat.getWriteCursor(config) ||
               (config == CursorConfig.DEFAULT && writeAllowed);
    }

    private static Cursor openCursor(DataView view,
                                     CursorConfig config,
                                     boolean writeAllowed)
        throws DatabaseException {

        return view.currentTxn.openCursor
            (view.db, config, isWriteCursor(config, writeAllowed),
             view.useTransaction());
    }

    protected Cursor dupCursor(Cursor cursor, boolean samePosition)
        throws DatabaseException {

        return view.currentTxn.dupCursor(cursor, writeCursor, samePosition);
    }

    protected void closeCursor(Cursor cursor)
        throws DatabaseException {

        view.currentTxn.closeCursor(cursor);
    }

    protected boolean checkRecordNumber() {
        return isRecnoOrQueue;
    }
}
