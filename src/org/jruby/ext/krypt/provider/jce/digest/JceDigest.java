/***** BEGIN LICENSE BLOCK *****
* Version: CPL 1.0/GPL 2.0/LGPL 2.1
*
* The contents of this file are subject to the Common Public
* License Version 1.0 (the "License"); you may not use this file
* except in compliance with the License. You may obtain a copy of
* the License at http://www.eclipse.org/legal/cpl-v10.html
*
* Software distributed under the License is distributed on an "AS
* IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
* implied. See the License for the specific language governing
* rights and limitations under the License.
*
* Copyright (C) 2011 
* Hiroshi Nakamura <nahi@ruby-lang.org>
* Martin Bosslet <Martin.Bosslet@googlemail.com>
*
* Alternatively, the contents of this file may be used under the terms of
* either of the GNU General Public License Version 2 or later (the "GPL"),
* or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
* in which case the provisions of the GPL or the LGPL are applicable instead
* of those above. If you wish to allow use of your version of this file only
* under the terms of either the GPL or the LGPL, and not to allow others to
* use your version of this file under the terms of the CPL, indicate your
* decision by deleting the provisions above and replace them with the notice
* and other provisions required by the GPL or the LGPL. If you do not delete
* the provisions above, a recipient may use your version of this file under
* the terms of any one of the CPL, the GPL or the LGPL.
 */
package org.jruby.ext.krypt.provider.jce.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.jruby.ext.krypt.impl.digest.RIPEMD160Digest;
import org.jruby.ext.krypt.provider.Digest;
import org.jruby.ext.krypt.provider.jce.Algorithms;
import org.jruby.ext.krypt.provider.jce.Algorithms.JavaAlgorithm;

/**
 * 
 * @author <a href="mailto:Martin.Bosslet@googlemail.com">Martin Bosslet</a>
 */
public class JceDigest implements Digest {
    
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
    private RIPEMD160Digest rmd=null;
    
    private boolean ripemd=false;
    public JceDigest(JavaAlgorithm algorithm) throws NoSuchAlgorithmException {
        //Insert here the RIPEMD thing
        if(algorithm.getCanonicalJavaName().equals("RIPEMD-160"))
        {
            this.rmd=new RIPEMD160Digest();
            this.md=null;
            ripemd=true;
        }
        else
        this.md = MessageDigest.getInstance(algorithm.getCanonicalJavaName());
       
        this.algorithm = algorithm;
        this.blockLength = blockLengthMap.get(algorithm);
    }

    @Override
    public byte[] digest() {
        if(ripemd)
            return rmd.digest();
        else
            return md.digest();
    }

    @Override
    public byte[] digest(byte[] data) {
        if(ripemd)
            return rmd.digest(data);
        else
            return md.digest(data);
    }

    @Override
    public void update(byte[] data, int off, int len) {
        if(ripemd)
            rmd.update(data, off, len);
        else
            md.update(data, off, len);
    }
    
    @Override
    public void reset() {
        if(ripemd)
            rmd.reset();
        else
            md.reset();
    }
    
    @Override
    public String getName() {
        return algorithm.getCanonicalRubyName();
    }
            
    @Override
    public int getDigestLength() {
        if(ripemd)
            return rmd.getDigestSize();
        else
            return md.getDigestLength();
    }
    
    @Override
    public int getBlockLength() {
        return blockLength;
    }
}
