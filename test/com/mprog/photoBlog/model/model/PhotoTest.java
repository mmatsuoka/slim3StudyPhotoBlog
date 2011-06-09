package com.mprog.photoBlog.model.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.mprog.photoBlog.model.Head;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PhotoTest extends AppEngineTestCase {

    private Head model = new Head();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
