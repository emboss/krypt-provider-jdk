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
package org.jruby.ext.krypt.provider.jdk.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.jruby.ext.krypt.impl.digest.RIPEMD160;
import org.jruby.ext.krypt.provider.Digest;
import org.jruby.ext.krypt.provider.jdk.Algorithms;
import org.jruby.ext.krypt.provider.jdk.Algorithms.JavaAlgorithm;

/**
 * 
 * @author <a href="mailto:Martin.Bosslet@gmail.com">Martin Bosslet</a>
 */
public class JdkDigest implements Digest {
    
    private static final Map<JavaAlgorithm, Integer> blockLengthMap = new HashMap<JavaAlgorithm, Integer>();
    
    static {
        blockLengthMap.put(Algorithms.SHA1, 64);
        blockLengthMap.put(Algorithms.SHA224, 64);
        blockLengthMap.put(Algorithms.SHA256, 64);
        blockLengthMap.put(Algorithms.SHA384, 128);
        blockLengthMap.put(Algorithms.SHA512, 128);
        blockLengthMap.put(Algorithms.RIPEMD160, 64);
        blockLengthMap.put(Algorithms.MD5, 64);
    }
    
    private final MessageDigest md;
    private final JavaAlgorithm algorithm;
    private final int blockLength;
    
    public JdkDigest(JavaAlgorithm algorithm) throws NoSuchAlgorithmException {
        this.md = createMessageDigest(algorithm);
        this.algorithm = algorithm;
        this.blockLength = blockLengthMap.get(algorithm);
    }
    
    private static MessageDigest createMessageDigest(JavaAlgorithm algorithm) throws NoSuchAlgorithmException {
        if (Algorithms.RIPEMD160.equals(algorithm)) {
            return new RIPEMD160();
        } else {
            return MessageDigest.getInstance(algorithm.getCanonicalJavaName());
        }
    }

    @Override
    public byte[] digest() {
        return md.digest();
    }

    @Override
    public byte[] digest(byte[] data) {
        return md.digest(data);
    }

    @Override
    public void update(byte[] data, int off, int len) {
        md.update(data, off, len);
    }
    
    @Override
    public void reset() {
        md.reset();
    }
    
    @Override
    public String getName() {
        return algorithm.getCanonicalRubyName();
    }
            
    @Override
    public int getDigestLength() {
        return md.getDigestLength();
    }
    
    @Override
    public int getBlockLength() {
        return blockLength;
    }
}
