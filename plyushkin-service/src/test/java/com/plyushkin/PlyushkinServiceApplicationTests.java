package com.plyushkin;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.plyushkin.openapi.client.CreateCategoryRequest;
import org.junit.jupiter.api.Test;

class PlyushkinServiceApplicationTests {

  @Test
  void testSanity() {
    CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
    assertNotNull(createCategoryRequest);
    assertTrue(true);
  }

}
