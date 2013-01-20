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

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import org.jruby.ext.krypt.provider.Cipher;
import org.jruby.ext.krypt.provider.jce.Algorithms.JavaAlgorithm;


public class JceCipher implements Cipher {
    
    private final javax.crypto.Cipher cp;
    private final JavaAlgorithm algorithm;
       
    public JceCipher(JavaAlgorithm algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
       
        this.algorithm = algorithm;
        this.cp= javax.crypto.Cipher.getInstance(algorithm.getCanonicalJavaName());
    }

  
    @Override
    public byte[] doFinal() throws IllegalBlockSizeException, BadPaddingException{
        return cp.doFinal();
    }

    @Override
    public byte[] doFinal(byte[] input)  throws IllegalBlockSizeException, BadPaddingException{
        return cp.doFinal(input);
    }

    @Override
    public void init(int opmode, Key key) throws InvalidKeyException{
        cp.init(opmode, key);
    }
    
    @Override
    public int update(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws ShortBufferException{
        return cp.update(input, inputOffset, inputLen, output, outputOffset);
    }
    
    @Override
    public String getName() {
        return algorithm.getCanonicalRubyName();
    }
}
