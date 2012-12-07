/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2010 Oracle.  All rights reserved.
 *
 */

package bdb.com.sleepycat.je.utilint;

/**
 * A boolean JE stat.
 */
public class BooleanStat extends Stat<Boolean> {

    private Boolean value;

    public BooleanStat(StatGroup group, StatDefinition definition) {
        super(group, definition);
    }

    @Override
    public Boolean get() {
        return value;
    }

    @Override
    public void set(Boolean newValue) {
        value = newValue;
    }


    @Override
    public void add(Stat<Boolean> otherStat) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        value = false;
    }

    @Override
    String getFormattedValue() {
        return value.toString();
    }
}
