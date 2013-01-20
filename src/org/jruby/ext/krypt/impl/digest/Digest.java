/*
 * krypt-provider-jdk - Implementation using the JDK security library
 *
 * Copyright (c) 2011-2013
 * Hiroshi Nakamura <nahi@ruby-lang.org>
 * Martin Bosslet <martin.bosslet@gmail.com>
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.jruby.ext.krypt.impl.digest;

import java.security.MessageDigest;

/**
 * base implementation of MD4 family style digest as outlined in "Handbook of
 * Applied Cryptography", pages 344 - 347.
 * 
 * @author <a href="mailto:vipulnsward@gmail.com">Vipul Amler</a>
 */
public abstract class Digest extends MessageDigest {

    private final byte[] buf;
    private int off;
    private long byteCount;

    protected abstract void processWord(byte[] in, int inOff);
    protected abstract void processLength(long bitLength);
    protected abstract void processBlock();
    
    protected Digest(String algorithm) {
        super(algorithm);
        buf = new byte[4];
        off = 0;
    }
    
    @Override
    protected void engineUpdate(byte input) {
        buf[off++] = input;

        if (off == buf.length) {
            processWord(buf, 0);
            off = 0;
        }

        byteCount++;
    }

    @Override
    protected void engineUpdate(byte[] input, int inOff, int len) {
        // fill the current word
        while ((off != 0) && (len > 0)) {
            update(input[inOff]);

            inOff++;
            len--;
        }

        // process whole words.
        while (len > buf.length) {
            processWord(input, inOff);

            inOff += buf.length;
            len -= buf.length;
            byteCount += buf.length;
        }

        // load in the remainder.
        while (len > 0) {
            update(input[inOff]);
            inOff++;
            len--;
        }
    }

    @Override
    protected void engineReset() {
        byteCount = 0;

        off = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = 0;
        }
    }

    protected void finish() {
        long bitLength = (byteCount << 3);

        // add the pad bytes.
        update((byte) 0x80);

        while (off != 0) {
            update((byte) 0x00);
        }

        processLength(bitLength);
        processBlock();
    }
}