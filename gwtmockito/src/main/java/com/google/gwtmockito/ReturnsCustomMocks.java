/*
 * Copyright 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwtmockito;

import com.google.gwt.core.client.JavaScriptObject;

import org.mockito.internal.stubbing.defaultanswers.ReturnsMocks;
import org.mockito.invocation.InvocationOnMock;

/**
 * An answer that generally returns mocks, but with a few overrides.
 * 
 * @author ekuefler@google.com (Erik Kuefler)
 */
class ReturnsCustomMocks extends ReturnsMocks {
  @Override
  public Object answer(InvocationOnMock invocation) throws Throwable {
    // Make JavaScriptObject.cast work in most cases by forcing it to return the underlying mock
    // instead of a new mock of type JavaScriptObject. This allows cast to be used in situations
    // that don't violate the Java type system, but not in situations that do (even though
    // javascript would allow them).
    if (invocation.getMock() instanceof JavaScriptObject
        && invocation.getMethod().getName().equals("cast")) {
      return invocation.getMock();
    } else {
      return super.answer(invocation);
    }
  }
}
