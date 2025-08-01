/*
 * Copyright (c) 2015, 2025, Oracle and/or its affiliates.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License, version 2.0, as published by
 * the Free Software Foundation.
 *
 * This program is designed to work with certain software that is licensed under separate terms, as designated in a particular file or component or in
 * included license documentation. The authors of MySQL hereby grant you an additional permission to link the program and your derivative works with the
 * separately licensed software that they have either included with the program or referenced in the documentation.
 *
 * Without limiting anything contained in the foregoing, this file, which is part of MySQL Connector/J, is also subject to the Universal FOSS Exception,
 * version 1.0, a copy of which can be found at http://oss.oracle.com/licenses/universal-foss-exception.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License, version 2.0, for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package com.mysql.cj.result;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.mysql.cj.Constants;
import com.mysql.cj.Messages;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.exceptions.NumberOutOfRange;
import com.mysql.cj.util.DataTypeUtil;
import com.mysql.cj.util.StringUtils;

/**
 * A {@link ValueFactory} to create {@link Byte} instances.
 */
public class ByteValueFactory extends DefaultValueFactory<Byte> {

    public ByteValueFactory(PropertySet pset) {
        super(pset);
    }

    @Override
    public Byte createFromYear(long l) {
        return createFromLong(l);
    }

    @Override
    public Byte createFromLong(long l) {
        if (this.jdbcCompliantTruncationForReads && (l < Byte.MIN_VALUE || l > Byte.MAX_VALUE)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), getTargetTypeName() }));
        }
        return (byte) l;
    }

    @Override
    public Byte createFromBigInteger(BigInteger i) {
        if (this.jdbcCompliantTruncationForReads
                && (i.compareTo(Constants.BIG_INTEGER_MIN_BYTE_VALUE) < 0 || i.compareTo(Constants.BIG_INTEGER_MAX_BYTE_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { i, getTargetTypeName() }));
        }
        return (byte) i.intValue();
    }

    @Override
    public Byte createFromDouble(double d) {
        if (this.jdbcCompliantTruncationForReads && (d < Byte.MIN_VALUE || d > Byte.MAX_VALUE)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, getTargetTypeName() }));
        }
        return (byte) d;
    }

    @Override
    public Byte createFromBigDecimal(BigDecimal d) {
        if (this.jdbcCompliantTruncationForReads
                && (d.compareTo(Constants.BIG_DECIMAL_MIN_BYTE_VALUE) < 0 || d.compareTo(Constants.BIG_DECIMAL_MAX_BYTE_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, getTargetTypeName() }));
        }
        return (byte) d.longValue();
    }

    @Override
    public Byte createFromBit(byte[] bytes, int offset, int length) {
        long l = DataTypeUtil.bitToLong(bytes, offset, length);
        if (this.jdbcCompliantTruncationForReads && l >> 8 != 0) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), getTargetTypeName() }));
        }
        return (byte) l;
    }

    @Override
    public Byte createFromBytes(byte[] bytes, int offset, int length, Field f) {
        if (length == 0 && this.pset.getBooleanProperty(PropertyKey.emptyStringsConvertToZero).getValue()) {
            return (byte) 0;
        }
        String s = StringUtils.toString(bytes, offset, length, f.getEncoding());
        byte[] newBytes = s.getBytes();

        if (this.jdbcCompliantTruncationForReads && newBytes.length != 1) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { s, getTargetTypeName() }));
        }
        return newBytes[0];
    }

    @Override
    public String getTargetTypeName() {
        return Byte.class.getName();
    }

}
