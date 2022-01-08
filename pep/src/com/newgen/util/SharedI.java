package com.newgen.util;

import com.newgen.iforms.custom.IFormReference;

public interface SharedI {

    void formLoad(IFormReference ifr);
    void sendMail(IFormReference ifr);
    void setDecision(IFormReference ifr);
}
