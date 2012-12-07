/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2010 Oracle.  All rights reserved.
 *
 */

package bdb.com.sleepycat.je.utilint;

/**
 * A long JE stat.
 */
public class LSNStat extends LongStat{

    public LSNStat(StatGroup group, StatDefinition definition) {
        super(group, definition);
    }

    public LSNStat(StatGroup group, StatDefinition definition, long counter) {
        super(group, definition);
        this.counter = counter;
    }

    @Override
    String getFormattedValue() {
        return DbLsn.getNoFormatString(counter);
    }
}
