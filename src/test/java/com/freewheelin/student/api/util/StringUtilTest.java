package com.freewheelin.student.api.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StringUtilTest {

    @Test
    public void phoneNumber_하이픈_추가() throws Exception{
        //given
        String phoneNumber = "01012345678";
        String phoneNumber2 = "0101235678";
        //when
        phoneNumber = StringUtil.formatPhoneNumber(phoneNumber);
        phoneNumber2 = StringUtil.formatPhoneNumber(phoneNumber2);
        //then
        assertThat(phoneNumber).isEqualTo("010-1234-5678");
        assertThat(phoneNumber2).isEqualTo("010-123-5678");
    }

}