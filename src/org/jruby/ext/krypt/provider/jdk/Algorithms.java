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
package org.jruby.ext.krypt.provider.jdk;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:Martin.Bosslet@gmail.com">Martin Bosslet</a>
 */
public class Algorithms {
   
    private Algorithms() {}
    
    private static final Map<String, JavaAlgorithm> rubyToJavaMap = new HashMap<String, JavaAlgorithm>();
    private static final Map<String, JavaAlgorithm> oidToJavaMap = new HashMap<String, JavaAlgorithm>();
    
    public static final JavaAlgorithm SHA1 = new JavaAlgorithm("SHA-1", "SHA1");
    public static final JavaAlgorithm SHA224 = new JavaAlgorithm("SHA-224", "SHA224");
    public static final JavaAlgorithm SHA256 = new JavaAlgorithm("SHA-256", "SHA256");
    public static final JavaAlgorithm SHA384 = new JavaAlgorithm("SHA-384", "SHA384");
    public static final JavaAlgorithm SHA512 = new JavaAlgorithm("SHA-512", "SHA512");
    public static final JavaAlgorithm RIPEMD160 = new JavaAlgorithm("RIPEMD-160", "RIPEMD160");
    public static final JavaAlgorithm MD5 = new JavaAlgorithm("MD5", "MD5");
    
    static {
        rubyToJavaMap.put("SHA1", SHA1);
        rubyToJavaMap.put("sha1", SHA1);
        rubyToJavaMap.put("SHA224", SHA224);
        rubyToJavaMap.put("sha224", SHA224);
        rubyToJavaMap.put("SHA256", SHA256);
        rubyToJavaMap.put("sha256", SHA256);
        rubyToJavaMap.put("SHA384", SHA384);
        rubyToJavaMap.put("sha384", SHA384);
        rubyToJavaMap.put("SHA512", SHA512);
        rubyToJavaMap.put("sha512", SHA512);
        rubyToJavaMap.put("RIPEMD160", RIPEMD160);
        rubyToJavaMap.put("ripemd160", RIPEMD160);
        rubyToJavaMap.put("MD5", MD5);
        rubyToJavaMap.put("md5", MD5);
        
        oidToJavaMap.put("1.3.14.3.2.26", SHA1);
        oidToJavaMap.put("2.16.840.1.101.3.4.2.4", SHA224);
        oidToJavaMap.put("2.16.840.1.101.3.4.2.1", SHA256);
        oidToJavaMap.put("2.16.840.1.101.3.4.2.2", SHA384);
        oidToJavaMap.put("2.16.840.1.101.3.4.2.3", SHA512);
        oidToJavaMap.put("1.3.36.3.2.1", RIPEMD160);
        oidToJavaMap.put("1.2.840.113549.2.5", MD5);
        
        /* What TODO with RIPEMD-160 ? */
    }
    
    public static JavaAlgorithm getJavaAlgorithm(String rbAlgorithm) throws NoSuchAlgorithmException {
        JavaAlgorithm algo = rubyToJavaMap.get(rbAlgorithm);
        if (algo == null)
            throw new NoSuchAlgorithmException("Algorithm not supported: " + rbAlgorithm);
        return algo;
    }
    
    public static JavaAlgorithm getJavaAlgorithmForOid(String oid) throws NoSuchAlgorithmException {
        JavaAlgorithm algo = oidToJavaMap.get(oid);
        if (algo == null)
            throw new NoSuchAlgorithmException("Algorithm not supported: " + oid);
        return algo;
    }
    
    public static class JavaAlgorithm {
        private final String javaName;
        private final String rubyName;

        public JavaAlgorithm(String javaName, String rubyName) {
            if (javaName == null) throw new NullPointerException("javaName");
            if (rubyName == null) throw new NullPointerException("rubyName");
            this.javaName = javaName;
            this.rubyName = rubyName;
        }

        public String getCanonicalJavaName() {
            return javaName;
        }

        public String getCanonicalRubyName() {
            return rubyName;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final JavaAlgorithm other = (JavaAlgorithm) obj;
            if (!this.javaName.equals(other.javaName)) {
                return false;
            }
            if (!this.rubyName.equals(other.rubyName)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + this.javaName.hashCode();
            hash = 53 * hash + this.rubyName.hashCode();
            return hash;
        }
    }
}
