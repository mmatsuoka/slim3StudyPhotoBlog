package com.mprog.photoBlog.model.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.mprog.photoBlog.model.Comment;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class MemoTest extends AppEngineTestCase {

    private Comment model = new Comment();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
