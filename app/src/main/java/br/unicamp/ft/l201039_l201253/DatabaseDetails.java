package br.unicamp.ft.l201039_l201253;

import java.lang.annotation.Documented;

@Documented
public @interface DatabaseDetails {
    String[] value();
    int[]  version();
}
