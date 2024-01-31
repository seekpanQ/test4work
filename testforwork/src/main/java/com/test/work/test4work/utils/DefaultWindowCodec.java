package com.test.work.test4work.utils;

import org.owasp.esapi.codecs.WindowsCodec;

public class DefaultWindowCodec extends WindowsCodec {

    private static final char[] IMMUNE_CHAR = new char[]{'*', '-', '.', '_', ':'};

    @Override
    public String encodeCharacter(char[] immune, Character c) {
        return super.encodeCharacter(IMMUNE_CHAR, c);
    }
}
